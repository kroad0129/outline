package com.outline.outline.file;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/files")
@Tag(name = "File", description = "파일 업로드 API")
public class FileController {

    @PostMapping("/upload")
    @Operation(summary = "이미지 업로드", description = "이미지를 업로드하고 접근 가능한 URL을 반환합니다.")
    public String upload(@RequestParam("file") MultipartFile file) {
        // 프로젝트 루트 기준 경로
        String uploadDir = System.getProperty("user.dir") + "/uploads";
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

        try {
            // 디렉토리 없으면 생성
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // 실제 파일 저장
            File saveFile = new File(uploadDir, filename);
            file.transferTo(saveFile);

            // 접근 경로 반환
            return "/uploads/" + filename;

        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 실패", e);
        }
    }
}
