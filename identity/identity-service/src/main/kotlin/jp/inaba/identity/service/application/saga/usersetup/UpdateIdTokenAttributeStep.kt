package jp.inaba.identity.service.application.saga.usersetup

import io.github.oshai.kotlinlogging.KotlinLogging
import jp.inaba.identity.api.domain.external.auth.AuthCommands
import jp.inaba.identity.api.domain.external.auth.updateIdTokenAttribute
import org.axonframework.commandhandling.gateway.CommandGateway

class UpdateIdTokenAttributeStep(
    private val commandGateway: CommandGateway
) {
    private val logger = KotlinLogging.logger {}

    fun handle(
        command: AuthCommands.UpdateIdTokenAttribute,
        onFail: (() -> Unit)? = null
    ) {
        try {
            commandGateway.updateIdTokenAttribute(command)
        }
        catch(e: Exception) {
            logger.error { "IdTokenの属性変更に失敗しました exception:[${e}]" }
            onFail?.invoke()
        }
    }
}