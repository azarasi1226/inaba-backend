package jp.inaba.service.domain.user

import jp.inaba.core.domain.user.UserId

interface CanCreateUserVerifier {
    fun isUserExists(userId: UserId): Boolean

    fun isLinkedSubject(subject: String): Boolean
}
