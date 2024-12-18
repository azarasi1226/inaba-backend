package jp.inaba.identity.share.domain.user

import de.huxhorn.sulky.ulid.ULID
import jp.inaba.common.domain.shared.DomainException

data class UserId(val value: String) {
    constructor() : this(ULID().nextULID())

    init {
        try {
            ULID.parseULID(value)
        } catch (ex: Exception) {
            throw DomainException("UserIdはULIDの形式である必要があります。value:[$value]")
        }
    }

    override fun toString(): String {
        return value
    }
}
