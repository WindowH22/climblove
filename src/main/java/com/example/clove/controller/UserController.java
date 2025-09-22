package com.example.clove.controller;

import com.example.clove.domain.User;
import com.example.clove.dto.LoginRequest;
import com.example.clove.dto.LoginResponse;
import com.example.clove.dto.UserRegistrationRequest;
import com.example.clove.service.JwtService;
import com.example.clove.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "사용자 관리", description = "사용자 등록, 로그인, 프로필 관리 API")
public class UserController {
    
    private final UserService userService;
    private final JwtService jwtService;
    
    @Operation(summary = "사용자 회원가입", description = "새로운 사용자를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 이메일 또는 닉네임")
    })
    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserRegistrationRequest request) {
        User user = userService.createUser(request.getEmail(), request.getPassword(), request.getNickname());
        return ResponseEntity.ok(user);
    }
    
    @Operation(summary = "사용자 로그인", description = "이메일과 비밀번호로 로그인하여 JWT 토큰을 발급받습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        User user = userService.authenticateUser(request.getEmail(), request.getPassword());
        
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        
        LoginResponse response = LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(86400000L) // 24시간
                .userInfo(LoginResponse.UserInfo.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .nickname(user.getNickname())
                        .profileImage(user.getProfileImage())
                        .role(user.getRole().name())
                        .build())
                .build();
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "내 프로필 조회", description = "현재 로그인한 사용자의 프로필 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername());
        return ResponseEntity.ok(user);
    }
    
    @Operation(summary = "사용자 정보 조회", description = "사용자 ID로 특정 사용자의 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(
            @Parameter(description = "사용자 ID", required = true) @PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }
}
