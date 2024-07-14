package jp.inaba.encrypt.api.domain

sealed interface KeysetEvent {
    val id: String
}

data class KeysetCreateEvent(
    override val id: String,
    val serviceId: String
) : KeysetEvent

data class KeysetUpdateEvent(
    override val id: String,
    val serviceId: String
) : KeysetEvent

data class KeysetDeleteEvent(
    override val id: String,
    val serviceId: String
) : KeysetEvent