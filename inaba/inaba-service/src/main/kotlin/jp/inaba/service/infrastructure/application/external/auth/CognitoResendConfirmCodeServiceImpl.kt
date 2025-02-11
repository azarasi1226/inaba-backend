package jp.inaba.service.infrastructure.application.external.auth

import jp.inaba.service.application.external.auth.resendConfirmCode.CognitoResendConfirmCodeService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient
import software.amazon.awssdk.services.cognitoidentityprovider.model.ResendConfirmationCodeRequest

@Service
class CognitoResendConfirmCodeServiceImpl(
    @Value("\${aws.cognito.client-id}")
    private val clientId: String,
    private val cognitoClient: CognitoIdentityProviderClient,
) : CognitoResendConfirmCodeService {
    override fun handle(emailAddress: String) {
        val request =
            ResendConfirmationCodeRequest.builder()
                .clientId(clientId)
                .username(emailAddress)
                .build()

        cognitoClient.resendConfirmationCode(request)
    }
}
