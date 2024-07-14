package jp.inaba.encrypt.service.infrastructure

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface KeysetJpaRepository : JpaRepository<KeysetJpaEntity, String>
