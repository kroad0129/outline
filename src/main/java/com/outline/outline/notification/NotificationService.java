package com.outline.outline.notification;

import com.outline.outline.like.LikeRepository;
import com.outline.outline.post.Post;
import com.outline.outline.post.PostRepository;
import com.outline.outline.user.User;
import com.outline.outline.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;


    public List<Notification> getUserNotifications(Long userId) {
        if (!userRepository.existsById(userId)) {
            return List.of();
        }
        User user = userRepository.findById(userId).get(); // 안전
        return notificationRepository.findByUser(user);
    }

    public void notifyUsersForPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));

        String message = "\"" + post.getTitle() + "\" 해당 제보 요청이 완료되었습니다.";

        List<User> users = likeRepository.findUsersByPost(post);

        List<Notification> notifications = users.stream()
                .map(user -> Notification.builder()
                        .user(user)
                        .message(message)
                        .build())
                .toList();

        notificationRepository.saveAll(notifications);
    }

    public void notifySolvedUsers(Post post, List<User> users) {
        String message = "\"" + post.getTitle() + "\" 관련 정책이 반영되었습니다.";

        List<Notification> notifications = users.stream()
                .map(user -> Notification.builder()
                        .user(user)
                        .message(message)
                        .build())
                .toList();

        notificationRepository.saveAll(notifications);
    }

}
