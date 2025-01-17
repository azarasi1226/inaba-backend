package jp.inaba.service.application.saga.usersetup

import io.github.oshai.kotlinlogging.KotlinLogging
import jp.inaba.message.user.command.CreateUserCommand
import jp.inaba.message.user.createUser
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
            commandGateway.createUser(command)
        } catch (e: Exception) {
            logger.warn { "ユーザー作成に失敗しました exception:[$e]" }
            onFail.invoke()
        }
    }
}
