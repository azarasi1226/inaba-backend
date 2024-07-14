package jp.inaba.encrypt.api.domain

data class FindKeysetByIdQuery(val id: KeysetId)

data class FindKeysetByIdResult(val id: String, val serviceId: String)