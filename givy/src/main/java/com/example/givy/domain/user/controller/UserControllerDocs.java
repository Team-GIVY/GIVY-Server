package com.example.givy.domain.user.controller;

import com.example.givy.domain.user.dto.req.UserReqDTO;
import com.example.givy.domain.user.dto.res.UserResDTO;
import com.example.givy.global.apiPayLoad.ApiResponse;
import com.example.givy.global.security.CustomPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "01 User", description = "01번대 사용자 API")
public interface UserControllerDocs {

    /* 01-01 회원가입 */
    @Operation(
            summary = "01-01 회원가입",
            operationId = "01-01",
            description = "email, password, name을 입력하여 사용자 회원가입을 진행합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "회원가입 성공 (USR201_1)",
                    content = @Content(schema = @Schema(implementation = UserResDTO.UserInfoDTO.class))
            )
    })
    @PostMapping("/signup")
    ApiResponse<UserResDTO.UserInfoDTO> SignUp(
            @Valid @RequestBody UserReqDTO.UserSignupDTO request
    );


    /* 01-02 로그인 */
    @Operation(
            summary = "01-02 로그인",
            operationId = "01-02",
            description = "이메일과 비밀번호를 입력하면 JWT Access Token을 발급합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공 (USR200_1)",
                    content = @Content(schema = @Schema(implementation = UserResDTO.UserLoginResDTO.class))
            )
    })
    @PostMapping("/login")
    ApiResponse<UserResDTO.UserLoginResDTO> Login(
            @Valid @RequestBody UserReqDTO.UserLoginDTO request
    );


    /* 01-03 내 정보 조회 */
    @Operation(
            summary = "01-03 내 정보 조회",
            operationId = "01-03",
            description = "JWT 토큰 기반으로 로그인한 사용자의 상세 정보를 조회합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "내 정보 조회 성공 (USR200_2)",
                    content = @Content(schema = @Schema(implementation = UserResDTO.UserDetailDTO.class))
            )
    })
    @GetMapping("/me")
    ApiResponse<UserResDTO.UserDetailDTO> getUserInfo(
            @AuthenticationPrincipal CustomPrincipal principal
    );
}
