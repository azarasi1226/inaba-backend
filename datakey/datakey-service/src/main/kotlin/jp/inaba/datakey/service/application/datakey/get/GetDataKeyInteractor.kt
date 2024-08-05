package jp.inaba.datakey.service.jp.inaba.datakey.service.application.datakey.get

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import jp.inaba.datakey.service.jp.inaba.datakey.service.application.datakey.EncryptedDataKeyDecrypter
import jp.inaba.datakey.service.jp.inaba.datakey.service.domain.datakey.Base64PlaneDataKey
import jp.inaba.datakey.service.jp.inaba.datakey.service.domain.datakey.EncryptedDataKey
import jp.inaba.datakey.service.jp.inaba.datakey.service.domain.datakey.GetDataKeyError
import jp.inaba.datakey.service.jp.inaba.datakey.service.infrastructure.jpa.datakey.DataKeyJpaRepository
import org.springframework.stereotype.Service

@Service
class GetDataKeyInteractor(
    private val encryptedDataKeyDecrypter: EncryptedDataKeyDecrypter,
    private val dataKeyJpaRepository: DataKeyJpaRepository,
) {
    fun hundle(input: GetDataKeyInput): Result<GetDataKeyOutput, GetDataKeyError> {
        val entity = dataKeyJpaRepository.getReferenceById(input.relationId.toString())

        val planDataKey = encryptedDataKeyDecrypter.handle(EncryptedDataKey(entity.encryptedDataKey))

        val base64PlaneDataKey = Base64PlaneDataKey.create(planDataKey)
        return Ok(GetDataKeyOutput(base64PlaneDataKey))
    }

}