package jp.inaba.core.domain.user

import jp.inaba.core.domain.common.DomainError

enum class FindUserByIdError(
    override val errorCode: String,
    override val errorMessage: String,
) : DomainError {
    USER_NOT_FOUND("1", "ユーザーが存在しません"),
}
