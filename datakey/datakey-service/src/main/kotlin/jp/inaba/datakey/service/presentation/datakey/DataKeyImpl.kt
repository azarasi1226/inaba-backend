package jp.inaba.datakey.service.jp.inaba.datakey.service.presentation.datakey

import io.grpc.stub.StreamObserver
import jp.inaba.datakey.service.DataKeyGrpc
import jp.inaba.datakey.service.GetDataKeyReply
import jp.inaba.datakey.service.GetDataKeyRequest
import jp.inaba.datakey.service.jp.inaba.datakey.service.application.datakey.get.GetDataKeyInput
import jp.inaba.datakey.service.jp.inaba.datakey.service.application.datakey.get.GetDataKeyInteractor
import jp.inaba.datakey.service.jp.inaba.datakey.service.domain.datakey.RelationId
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class DataKeyImpl(
    private val getDataKeyInteractor: GetDataKeyInteractor,
): DataKeyGrpc.DataKeyImplBase() {
    @Override
    override fun getDataKey(request: GetDataKeyRequest, responseObserver: StreamObserver<GetDataKeyReply>) {
        // リクエストパラメータからRelationIdを受け取る
        val relationId = RelationId(request.relationId)

        // リクエストパラメータをまとめる
        val input = GetDataKeyInput(relationId)

        // メイン処理部
        val result = getDataKeyInteractor.hundle(input)

        // レスポンスを送信する
        responseObserver.onNext(GetDataKeyReply.newBuilder().setBase64PlaneDataKey(result.value.base64PlaneDataKey.value).build())

        // 送信完了
        responseObserver.onCompleted()
    }
}