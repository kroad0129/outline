package com.outline.outline.notification;

import com.outline.outline.post.Post;
import com.outline.outline.post.PostRepository;
import com.outline.outline.user.User;
import com.outline.outline.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // 알림 리스트 반환
    public List<Notification> getUserNotifications(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
        return notificationRepository.findByUser(user);
    }

    // 알림 생성 (공감수 100 이상)
    public void notifyAllUsersForPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));

        String message = "\"" + post.getTitle() + "\" 해당 제보 요청이 완료되었습니다.";

        List<User> users = userRepository.findAll();
        List<Notification> notifications = users.stream()
                .map(user -> Notification.builder()
                        .user(user)
                        .message(message)
                        .build())
                .toList();

        notificationRepository.saveAll(notifications);
    }
}
