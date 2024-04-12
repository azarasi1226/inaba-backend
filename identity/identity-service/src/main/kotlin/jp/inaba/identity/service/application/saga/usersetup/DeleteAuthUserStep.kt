package jp.inaba.identity.service.application.saga.usersetup

import jp.inaba.identity.api.domain.external.auth.AuthCommands
import jp.inaba.identity.api.domain.external.auth.deleteAuthUser
import org.axonframework.commandhandling.gateway.CommandGateway

class DeleteAuthUserStep(
    private val commandGateway: CommandGateway
) {
    private var onSuccess: (() -> Unit)? = null
    private var onFail: ((Exception) -> Unit)? = null

    fun onSuccess(onSuccess: () -> Unit): DeleteAuthUserStep {
        this.onSuccess = onSuccess
        return this
    }

    fun onFail(onFail: (Exception) -> Unit): DeleteAuthUserStep {
        this.onFail = onFail
        return this
    }

    fun execute(command: AuthCommands.DeleteAuthUser) {
        try {
            commandGateway.deleteAuthUser(command)
            onSuccess?.invoke()
        }
        catch(e: Exception) {
            onFail?.invoke(e)
        }
    }
}