package jp.inaba.identity.service.application.command.external.cognito.signup

import jp.inaba.identity.api.domain.external.cognito.CognitoCommands
import jp.inaba.identity.api.domain.external.cognito.CognitoEvents
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventhandling.gateway.EventGateway
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient
import software.amazon.awssdk.services.cognitoidentityprovider.model.SignUpRequest

@Service
class SignupCognitoInteractor(
    @Value("aws.cognito.client_id")
    private val clientId: String,
    private val cognitoClient: CognitoIdentityProviderClient,
    private val eventGateway: EventGateway
) {
    @CommandHandler
    fun handle(command: CognitoCommands.Signup) {
        val request = SignUpRequest.builder()
            .clientId(clientId)
            .username(command.emailAddress)
            .password(command.password)
            .build()

        val response = cognitoClient.signUp(request)

        val event = CognitoEvents.Signedup(
            sub = response.userSub(),
            emailAddress = command.emailAddress,
            password = command.password
        )

        eventGateway.publish(event)
    }
}