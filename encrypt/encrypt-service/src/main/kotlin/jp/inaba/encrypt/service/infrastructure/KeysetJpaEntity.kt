package jp.inaba.encrypt.service.infrastructure

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "keyset")
data class KeysetJpaEntity(
    @Id
    val id: String = "",
    val serviceId: String = "",
)
