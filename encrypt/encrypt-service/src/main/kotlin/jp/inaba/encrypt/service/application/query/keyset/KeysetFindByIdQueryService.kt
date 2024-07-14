package jp.inaba.encrypt.service.application.query.keyset

import jp.inaba.encrypt.api.domain.FindKeysetByIdQuery
import jp.inaba.encrypt.api.domain.FindKeysetByIdResult
import jp.inaba.encrypt.service.infrastructure.KeysetJpaRepository
import org.springframework.stereotype.Component
import java.util.Optional

@Component
class KeysetFindByIdQueryService(
    private val KeysetJpaRepository: KeysetJpaRepository,
) {
    fun handle(query: FindKeysetByIdQuery): Optional<FindKeysetByIdResult> {
        val maybeEntity = KeysetJpaRepository.findById(query.id.value)

        return if (maybeEntity.isEmpty) {
            Optional.empty()
        } else {
            val entity = maybeEntity.get()
            Optional.of(
                FindKeysetByIdResult(
                    id = entity.id,
                    serviceId = entity.serviceId,
                ),
            )
        }
    }
}
