package com.outline.outline.solve;

import com.outline.outline.notification.NotificationService;
import com.outline.outline.post.Post;
import com.outline.outline.post.PostRepository;
import com.outline.outline.solve.dto.SolveRequest;
import com.outline.outline.user.User;
import com.outline.outline.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SolveService {

    private final SolveRepository solveRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final NotificationService notificationService;

    @Transactional
    public void addSolve(SolveRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저가 없습니다."));
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));

        if (solveRepository.existsByUserAndPost(user, post)) {
            throw new IllegalStateException("이미 해결표시 했습니다.");
        }

        solveRepository.save(new Solve(user, post));
        post.setSolveCount(post.getSolveCount() + 1);

        if (post.getSolveCount() == 5) {
            post.setStatus(2);

            List<User> solvers = solveRepository.findUsersByPost(post);
            notificationService.notifySolvedUsers(post, solvers);
        }
    }


    @Transactional
    public void removeSolve(SolveRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저가 없습니다."));
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));

        Solve solve = solveRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new IllegalStateException("해결표시 하지 않은 게시글입니다."));

        solveRepository.delete(solve);
        post.setSolveCount(post.getSolveCount() - 1);
    }

    public boolean hasSolved(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저가 없습니다."));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));

        return solveRepository.existsByUserAndPost(user, post);
    }
}

