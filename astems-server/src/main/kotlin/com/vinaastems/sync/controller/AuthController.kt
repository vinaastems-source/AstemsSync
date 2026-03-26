package com.vinaastems.sync.controller

import com.vinaastems.sync.dto.LoginRequest
import com.vinaastems.sync.dto.LoginResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController {

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
        // 실제 운영 환경에서는 DB 인증 거쳐야 함 (현재는 시뮬레이션 코드)
        println("로그인 수신: ${request.userId} @ ${request.storeCode}")
        
        return if (request.password == "1234") {
            ResponseEntity.ok(LoginResponse(
                success = true, 
                message = "인증 성공: 환영합니다, ${request.userId}님!",
                authToken = "mock-jwt-token-abcd"
            ))
        } else {
            ResponseEntity.status(401).body(LoginResponse(
                success = false, 
                message = "ID 또는 비밀번호가 올바르지 않습니다."
            ))
        }
    }
}
