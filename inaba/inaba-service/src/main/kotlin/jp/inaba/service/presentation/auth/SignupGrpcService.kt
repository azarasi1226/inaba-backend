package jp.inaba.service.presentation.auth

import com.google.protobuf.Empty
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import jp.inaba.grpc.auth.SignupGrpc
import jp.inaba.grpc.auth.SignupRequest
import jp.inaba.message.auth.command.SignupCommand
import jp.inaba.message.auth.signup
import org.axonframework.commandhandling.gateway.CommandGateway

@GrpcService
class SignupGrpcService(
    private val commandGateway: CommandGateway
) : SignupGrpc.SignupImplBase() {
    override fun handle(request: SignupRequest, responseObserver: StreamObserver<Empty>) {
        val command = SignupCommand(
            emailAddress = request.emailAddress,
            password = request.password
        )

        commandGateway.signup(command)

        responseObserver.onNext(Empty.getDefaultInstance())
        responseObserver.onCompleted()
    }
}