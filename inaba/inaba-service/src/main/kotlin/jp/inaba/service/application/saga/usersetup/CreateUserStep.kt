package jp.inaba.service.application.saga.usersetup

import io.github.oshai.kotlinlogging.KotlinLogging
import jp.inaba.message.user.command.CreateUserCommand
import org.axonframework.commandhandling.gateway.CommandGateway

private val logger = KotlinLogging.logger {}

class CreateUserStep(
    private val commandGateway: CommandGateway,
) {
    fun handle(
        command: CreateUserCommand,
        onFail: () -> Unit,
    ) {
        try {
            commandGateway.sendAndWait<Any>(command)
        } catch (e: Exception) {
            logger.error { "ユーザー作成に失敗しました exception:[$e]" }
            onFail.invoke()
        }
    }
}
