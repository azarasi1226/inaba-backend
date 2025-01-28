package jp.inaba.service.application.saga.registerproduct

import io.github.oshai.kotlinlogging.KotlinLogging
import jp.inaba.message.stock.command.CreateStockCommand
import jp.inaba.message.stock.createStock
import org.axonframework.commandhandling.gateway.CommandGateway

private val logger = KotlinLogging.logger {}

class CreateStockStep(
    private val commandGateway: CommandGateway,
) {
    fun handle(
        command: CreateStockCommand,
        onFail: () -> Unit,
    ) {
        try {
            val result = commandGateway.createStock(command)

            if (result.isErr) {
                logger.warn { "Stockの作成に失敗しました error:[${result.error}]" }
                onFail.invoke()
            }
        } catch (e: Exception) {
            logger.warn { "Stockの作成に失敗しました exception:[$e]" }
            onFail.invoke()
        }
    }
}
