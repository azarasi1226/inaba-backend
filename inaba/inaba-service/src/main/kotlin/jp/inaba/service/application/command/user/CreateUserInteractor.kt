package jp.inaba.service.application.command.user

import jp.inaba.core.domain.common.UseCaseException
import jp.inaba.core.domain.user.CreateUserError
import jp.inaba.message.user.command.CreateUserCommand
import jp.inaba.service.domain.user.CanCreateUserVerifier
import jp.inaba.service.domain.user.InternalCreateUserCommand
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Component

@Component
class CreateUserInteractor(
    private val canCreateUserVerifier: CanCreateUserVerifier,
    private val commandGateway: CommandGateway,
) {
    @CommandHandler
    fun handle(command: CreateUserCommand) {
        if (canCreateUserVerifier.isUserExists(command.id)) {
            throw UseCaseException(CreateUserError.USER_ALREADY_EXISTS)
        }
        if (canCreateUserVerifier.isLinkedSubject(command.subject)) {
            throw UseCaseException(CreateUserError.USER_ALREADY_LINKED_TO_SUBJECT)
        }

        // Subject(認証ユーザー)がIDProviderに問い合わせ確認模したほうがいいような気もしたんやけど...
        // * AccessTokenだとCognitoのLambdaハンドラーから問い合わせできない。
        // * Subjectでのユーザー問い合わせはCognitoでは対応していない。
        // 上記２点を踏まえてやめている。万が一存在しないSubjectが渡されたとしても、ゴミデータが作られるだけで、システムの整合性的には問題ない認識。

        val internalCommand =
            InternalCreateUserCommand(
                id = command.id,
                subject = command.subject,
            )

        commandGateway.sendAndWait<Any>(internalCommand)
    }
}
