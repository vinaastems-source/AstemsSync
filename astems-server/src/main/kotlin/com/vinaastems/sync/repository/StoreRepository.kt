package com.vinaastems.sync.repository

import com.vinaastems.sync.entity.Store
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StoreRepository : JpaRepository<Store, String> {
    fun findByUserId(userId: String): Store?
}
