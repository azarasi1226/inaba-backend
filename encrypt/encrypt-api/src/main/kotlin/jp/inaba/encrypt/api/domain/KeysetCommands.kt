package jp.inaba.encrypt.api.domain

/**
 * AxonFramework向けだから使わないかも
 */
sealed interface KeysetAggregateCommand {
    val id: KeysetId
}

data class CreateKeysetCommand(
    override val id: KeysetId,
    val serviceId: KeysetServiceId,
) : KeysetAggregateCommand

data class UpdateKeysetCommand(
    override val id: KeysetId,
    val serviceId: KeysetServiceId,
) : KeysetAggregateCommand

data class DeleteKeysetCommand(
    override val id: KeysetId
) : KeysetAggregateCommand