package com.vinaastems.sync.config

import com.vinaastems.sync.entity.Store
import com.vinaastems.sync.repository.StoreRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("local") // 로컬 개발 환경에서만 테스트 데이터 생성
class DataInitializer(
    private val storeRepository: StoreRepository
) {
    @Bean
    fun initData() = CommandLineRunner {
        if (storeRepository.count() == 0L) {
            println("=== 로컬 테스트용 매장 데이터 생성 중... ===")
            val testStore = Store(
                storeCode = "S0001",
                storeName = "AstemsSync 테스트 매장",
                userId = "testuser",
                userPw = "1234",
                posNo = "1",
                currentVersion = "1.0.0"
            )
            storeRepository.save(testStore)
            println("=== 테스트 매장 생성 완료 (ID: testuser, PW: 1234) ===")
        }
    }
}
