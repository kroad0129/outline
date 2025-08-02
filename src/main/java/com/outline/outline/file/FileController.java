package com.outline.outline.file;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/files")
@Tag(name = "File", description = "파일 업로드 API")
public class FileController {

    @PostMapping("/upload")
    @Operation(summary = "이미지 업로드", description = "이미지를 업로드하고 접근 가능한 URL 목록을 반환합니다.")
    public List<String> upload(@RequestParam("files") List<MultipartFile> files) {
        String uploadDir = System.getProperty("user.dir") + "/uploads";
        List<String> resultUrls = new ArrayList<>();

        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        for (MultipartFile file : files) {
            String ext = getExtension(file.getOriginalFilename());
            String filename = UUID.randomUUID().toString() + ext;
            File saveFile = new File(uploadDir, filename);
            try {
                file.transferTo(saveFile);
                resultUrls.add("/uploads/" + filename); // 상대 경로
            } catch (IOException e) {
                throw new RuntimeException("파일 업로드 실패: " + filename, e);
            }
        }

        return resultUrls;
    }

    private String getExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex != -1) ? filename.substring(dotIndex) : "";
    }
}
