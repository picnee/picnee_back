package com.picnee.travel.domain.report.service;

import com.picnee.travel.domain.post.service.PostService;
import com.picnee.travel.domain.postComment.service.PostCommentService;
import com.picnee.travel.domain.report.dto.req.CreateReportReq;
import com.picnee.travel.domain.report.dto.res.FindReportRes;
import com.picnee.travel.domain.report.entity.Report;
import com.picnee.travel.domain.report.exception.NotFoundReportException;
import com.picnee.travel.domain.report.repository.ReportRepository;
import com.picnee.travel.domain.review.service.ReviewService;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.user.exception.NotAdminException;
import com.picnee.travel.domain.user.service.UserService;
import com.picnee.travel.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.picnee.travel.global.exception.ErrorCode.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserService userService;
    private final PostService postService;
    private final PostCommentService postCommentService;
    private final ReviewService reviewService;

    /**
     * 신고 생성
     */
    @Transactional
    public Report create(CreateReportReq dto, AuthenticatedUserReq auth) {
        User user = userService.findByEmail(auth.getEmail());

        return reportRepository.save(dto.toEntity(dto, user));
    }

    /**
     * 신고 삭제
     * 권한 : 어드민
     */
    @Transactional
    public void delete(UUID reportId, AuthenticatedUserReq auth) {
        validateAdmin(auth);

        reportRepository.deleteById(reportId);
    }

    /**
     * 신고 단건 조회
     */
    public FindReportRes find(UUID reportId, AuthenticatedUserReq auth) {
        validateAdmin(auth);
        Report report = reportRepository.findById(reportId).orElseThrow(() -> new IllegalArgumentException("신고 건이 존재하지않습니다."));

        return FindReportRes.from(report);
    }

    /**
     * 신고 전체 조회
     * 권한 : 어드민
     */
    public Page<FindReportRes> findReports(AuthenticatedUserReq auth, String targetId, String reportTargetType, String reportType, String isVisible, String sort, int page) {
        validateAdmin(auth);

        Pageable pageable = PageRequest.of(page, 10);
        Page<Report> reports = reportRepository.findReports(targetId, reportTargetType, reportType, isVisible, sort, pageable);

        return FindReportRes.paging(reports);
    }

    /**
     * 신고 처리
     * 권한 : 어드민
     */
    @Transactional
    public void processReport(UUID reportId, AuthenticatedUserReq auth) {
        validateAdmin(auth);
        Report report = findById(reportId);
        UUID reportTargetId = report.getTargetId();
        User reportedUser = switch (report.getReportTargetType()) {
            case REVIEW -> reviewService.sanction(reportTargetId);
            case POST -> postService.sanction(reportTargetId);
            case COMMENT -> postCommentService.sanction(reportTargetId);
        };

        //리포트 누적 5회 시 유저 계정 정지 BLOCKED 처리
        reportedUser.reportSanctionCountPlus();
        if(reportedUser.isRequiringSanctions()){
            reportedUser.updateBlockedStatus();
        }

        report.softDelete();

    }

    /**
     * 어드민 권한 확인
     */
    private void validateAdmin(AuthenticatedUserReq auth) {
        if (!auth.isAdmin()) {
            throw new NotAdminException(NOT_ADMIN_EXCEPTION);
        }
    }

    private Report findById(UUID reportId) {
        return reportRepository.findById(reportId)
                .orElseThrow(() -> new NotFoundReportException(NOT_FOUND_REPORT_EXCEPTION));
    }

}
