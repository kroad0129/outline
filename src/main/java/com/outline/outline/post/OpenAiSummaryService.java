package com.outline.outline.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.outline.outline.post.dto.SummaryResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpenAiSummaryService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.openai.com/v1/chat/completions")
            .defaultHeader("Content-Type", "application/json")
            .build();

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.model}")
    private String model;

    public SummaryResult summarize(String title, String content) {
        String prompt = buildPrompt(title, content);

        Map<String, Object> requestBody = Map.of(
                "model", model,
                "messages", List.of(
                        Map.of("role", "system", "content", "너는 게시글을 요약하는 도우미야."),
                        Map.of("role", "user", "content", prompt)
                ),
                "temperature", 0.7
        );

        String response = webClient.post()
                .header("Authorization", "Bearer " + apiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        try {
            String contentText = objectMapper.readTree(response)
                    .path("choices").get(0)
                    .path("message")
                    .path("content")
                    .asText();

            return objectMapper.readValue(contentText, SummaryResult.class);
        } catch (Exception e) {
            log.error("OpenAI 요약 실패", e);
            return SummaryResult.builder()
                    .summarizedTitle("[요약 실패]")
                    .summarizedContent("[요약 실패]")
                    .build();
        }
    }

    private String buildPrompt(String title, String content) {
        return """
            아래는 사용자가 작성한 게시글 제목과 본문입니다.

            1. 지역명이 있다면 ' 도' '시' 포함으로 자연스럽게 12자 이내
            2. 본문은 100자 이내로 요약해줘

            반드시 JSON 형식으로 아래와 같이 출력해 주세요.

            {
              "summarizedTitle": "...",
              "summarizedContent": "..."
            }

            [제목]: %s

            [본문]: %s
            """.formatted(title, content);
    }
}
