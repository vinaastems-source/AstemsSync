package com.vinaastems.sync.controller

import com.vinaastems.sync.dto.LoginRequest
import com.vinaastems.sync.dto.LoginResponse
import com.vinaastems.sync.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
        val result = authService.authenticate(request)
        
        return if (result.success) {
            ResponseEntity.ok(result)
        } else {
            ResponseEntity.status(401).body(result)
        }
    }
}
