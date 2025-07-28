package com.outline.outline.auth;

import com.outline.outline.auth.dto.LoginRequest;
import com.outline.outline.auth.dto.LoginResponse;
import com.outline.outline.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "로그인 관련 API")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(
            summary = "로그인 또는 자동 회원가입",
            description = "아이디를 입력하면 기존 회원이면 로그인 처리하고, 없으면 자동으로 회원가입 후 로그인합니다."
    )
    public LoginResponse login(@RequestBody LoginRequest request) {
        User user = authService.login(request.getUsername());
        return new LoginResponse(user.getId(), user.getUsername());
    }
}
