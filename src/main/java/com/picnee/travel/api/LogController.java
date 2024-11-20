package com.picnee.travel.api;

import com.picnee.travel.api.in.LogAPI;
import com.picnee.travel.domain.log.LogService;
import com.picnee.travel.domain.log.dto.res.LogRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping("/log")
@RequiredArgsConstructor
public class LogController implements LogAPI {

    private final LogService logService;

    @GetMapping
    public ResponseEntity<Resource> getLogFile() {

        HttpHeaders headers = new HttpHeaders();

        LogRes logRes = logService.getLog();

        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("attachment", logRes.getFileName());

        return ResponseEntity.ok()
                .headers(headers)
                .body(logRes.getResource());
    }

}