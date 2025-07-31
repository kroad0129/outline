package com.outline.outline.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${AWS_REGION}")
    private String region;

    @Value("${SES_FROM_EMAIL}")
    private String fromEmail;

    @Value("${SES_TO_EMAIL}")
    private String toEmail;

    public void sendLikeNotificationEmail(String postTitle) {
        try (SesClient sesClient = SesClient.builder()
                .region(Region.of(region))
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .build()) {

            String subject = "[알림] 공감수 100개 달성 제보 요청";
            String bodyText = "제보 \"" + postTitle + "\" 이(가) 공감수 100개를 달성하여 관련 기관에 제보 요청되었습니다.";

            SendEmailRequest request = SendEmailRequest.builder()
                    .destination(Destination.builder()
                            .toAddresses(toEmail)
                            .build())
                    .message(Message.builder()
                            .subject(Content.builder()
                                    .data(subject)
                                    .charset("UTF-8")
                                    .build())
                            .body(Body.builder()
                                    .text(Content.builder()
                                            .data(bodyText)
                                            .charset("UTF-8")
                                            .build())
                                    .build())
                            .build())
                    .source(fromEmail)
                    .build();

            sesClient.sendEmail(request);
            System.out.println("✅ SES 이메일 전송 성공");

        } catch (SesException e) {
            System.err.println("❌ SES 이메일 전송 실패: " + e.awsErrorDetails().errorMessage());
        }
    }
}
