package jp.inaba.service.application.saga.usersetup

import io.github.oshai.kotlinlogging.KotlinLogging
import jp.inaba.message.basket.command.DeleteBasketCommand
import jp.inaba.message.basket.deleteBasket
import org.axonframework.commandhandling.gateway.CommandGateway

private val logger = KotlinLogging.logger {}

class DeleteBasketStep(
    private val commandGateway: CommandGateway,
) {
    fun handle(
        command: DeleteBasketCommand,
        onFail: () -> Unit,
    ) {
        try {
            commandGateway.deleteBasket(command)
        } catch (e: Exception) {
            logger.warn { "買い物かごの削除に失敗しました exception:[$e]" }
            onFail.invoke()
        }
    }
}
