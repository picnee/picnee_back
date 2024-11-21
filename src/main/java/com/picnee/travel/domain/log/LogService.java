package com.picnee.travel.domain.log;

import com.picnee.travel.domain.log.dto.res.LogRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogService {

    //로그파일 다운로드
    public LogRes getLog() {

        //리눅스 로그파일 위치
        String filePath = "/picnee/picnee_back/build/libs/nohup.out";
        File file = new File(filePath);

        if (!file.exists()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        String fileName = "execute_log_" +
                LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".txt";

        Resource resource = new FileSystemResource(file);

        return LogRes.builder()
                .resource(resource)
                .fileName(fileName)
                .build();
    }
}
