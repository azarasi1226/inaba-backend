package jp.inaba.service.application.external.auth.resendConfirmCode

import jp.inaba.message.auth.command.ResendConfirmCodeCommand
import jp.inaba.message.auth.event.ConfirmCodeResentEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventhandling.gateway.EventGateway
import org.springframework.stereotype.Component

@Component
class ResendConfirmCodeInteractor(
    private val cognitoResendConfirmCodeService: CognitoResendConfirmCodeService,
    private val eventGateway: EventGateway,
) {
    @CommandHandler
    fun handle(command: ResendConfirmCodeCommand) {
        cognitoResendConfirmCodeService.handle(command.emailAddress)

        val event = ConfirmCodeResentEvent(command.emailAddress)

        eventGateway.publish(event)
    }
}
