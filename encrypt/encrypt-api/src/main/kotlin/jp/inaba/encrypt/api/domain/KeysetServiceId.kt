package jp.inaba.encrypt.api.domain

import jp.inaba.common.domain.shared.DomainException

class KeysetServiceId(val serviceId: String) {
    companion object {
        private const val MIN_LENGTH = 1
        private const val MAX_LENGTH = 32
    }

    init {
        if (serviceId.length !in MIN_LENGTH..MAX_LENGTH) {
            throw DomainException("サービスIDの長さは[${MIN_LENGTH}~${MAX_LENGTH}]の間です。現在の長さは[${serviceId.length}]")
        }
    }
}