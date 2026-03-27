package com.vinaastems.sync.entity

import jakarta.persistence.*

@Entity
@Table(name = "TB_SYNC_STORE") // Linux/Oracle 표준 테이블 네이밍 고려
class Store(
    @Id
    @Column(name = "STORE_CODE", length = 20)
    var storeCode: String,

    @Column(name = "STORE_NAME", nullable = false, length = 100)
    var storeName: String,

    @Column(name = "USER_ID", nullable = false, length = 50)
    var userId: String,

    @Column(name = "USER_PW", nullable = false, length = 100)
    var userPw: String,

    @Column(name = "POS_NO", length = 10)
    var posNo: String? = "1",

    @Column(name = "CURRENT_VERSION", length = 20)
    var currentVersion: String? = "1.0.0"
)
