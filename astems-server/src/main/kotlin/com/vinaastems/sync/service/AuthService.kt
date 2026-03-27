package com.vinaastems.sync.service

import com.vinaastems.sync.dto.LoginRequest
import com.vinaastems.sync.dto.LoginResponse
import com.vinaastems.sync.repository.StoreRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val storeRepository: StoreRepository
) {
    @Transactional(readOnly = true)
    fun authenticate(request: LoginRequest): LoginResponse {
        val store = storeRepository.findByUserId(request.userId)
            ?: return LoginResponse(false, "존재하지 않는 사용자 계정입니다.")

        // 비밀번호 검증 (실제 운영 시에는 BCrypt 등 암호화 필수)
        if (store.userPw != request.password) {
            return LoginResponse(false, "비밀번호가 일치하지 않습니다.")
        }

        // 매장 코드 검증 (매장별 POS 관리 목적)
        if (request.storeCode != null && store.storeCode != request.storeCode) {
            return LoginResponse(false, "요청한 매장 정보와 계정 정보가 일치하지 않습니다.")
        }

        return LoginResponse(
            success = true,
            message = "인증에 성공하였습니다: ${store.storeName}",
            authToken = "session-${System.currentTimeMillis()}"
        )
    }
}
