package com.picnee.travel.domain.report.service;

import com.picnee.travel.domain.report.dto.req.CreateReportReq;
import com.picnee.travel.domain.report.dto.res.FindReportRes;
import com.picnee.travel.domain.report.entity.Report;
import com.picnee.travel.domain.report.repository.ReportRepository;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserService userService;

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
    public Page<FindReportRes> findReports(AuthenticatedUserReq auth, int page) {
        validateAdmin(auth);

        Pageable pageable = PageRequest.of(page, 10);
        Page<Report> reports = reportRepository.findReports(pageable);

        return FindReportRes.paging(reports);
    }

    /**
     * 어드민 권한 확인
     */
    private void validateAdmin(AuthenticatedUserReq auth) {
        if (!auth.isAdmin()) {
            throw new IllegalArgumentException("어드민 권한이 아닙니다");
        }
    }

}
