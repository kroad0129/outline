package com.outline.outline.file;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/files")
@Tag(name = "File", description = "파일 업로드 API")
public class FileController {

    @PostMapping("/upload")
    @Operation(summary = "이미지 다중 업로드", description = "여러 이미지를 업로드하고 접근 가능한 URL 목록을 반환합니다.")
    public List<String> upload(@RequestParam("files") MultipartFile[] files) {
        String uploadDir = System.getProperty("user.dir") + "/uploads";
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        List<String> uploadedUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
                File saveFile = new File(uploadDir, filename);
                try {
                    file.transferTo(saveFile);
                    uploadedUrls.add("/uploads/" + filename);
                } catch (IOException e) {
                    throw new RuntimeException("파일 업로드 실패: " + file.getOriginalFilename(), e);
                }
            }
        }

        return uploadedUrls;
    }
}
