package com.picnee.travel.api;

import com.picnee.travel.api.in.LogAPI;
import com.picnee.travel.api.in.PostCommentApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@Slf4j
@RestController
@RequestMapping("/log")
@RequiredArgsConstructor
public class LogController implements LogAPI {

    @GetMapping
    public ResponseEntity<Resource> getLogFile() {

        //리눅스 로그파일 위치
        String filePath = "/picnee/picnee_back/build/libs/nohup.out";
        System.out.printf(filePath);
        File file = new File(filePath);

        // 파일이 존재하지 않는 경우
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // 파일 리소스 생성
        Resource resource = new FileSystemResource(file);

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("attachment", file.getName());

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

}