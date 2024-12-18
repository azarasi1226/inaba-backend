package jp.inaba.identity.api.domain.user

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import jp.inaba.identity.api.domain.user.command.CreateUserCommand
import jp.inaba.identity.api.domain.user.command.DeleteUserCommand
import jp.inaba.identity.api.domain.user.query.FindUserByIdQuery
import jp.inaba.identity.api.domain.user.query.FindUserByIdResult
import jp.inaba.identity.share.domain.user.FindUserByIdError
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway

fun CommandGateway.createUser(command: CreateUserCommand) {
    this.sendAndWait<Any>(command)
}

fun CommandGateway.deleteUser(command: DeleteUserCommand) {
    this.sendAndWait<Any>(command)
}

fun QueryGateway.findUserById(query: FindUserByIdQuery): Result<FindUserByIdResult, FindUserByIdError> {
    val result =
        this.query(query, ResponseTypes.optionalInstanceOf(FindUserByIdResult::class.java))
            .get()

    return if (result.isPresent) {
        Ok(result.get())
    } else {
        Err(FindUserByIdError.USER_NOT_FOUND)
    }
}
