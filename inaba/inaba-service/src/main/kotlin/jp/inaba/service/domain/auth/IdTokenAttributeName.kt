package jp.inaba.service.domain.auth

enum class IdTokenAttributeName(val value: String) {
    UserId("custom:user_id"),
    BasketId("custom:basket_id"),
}
