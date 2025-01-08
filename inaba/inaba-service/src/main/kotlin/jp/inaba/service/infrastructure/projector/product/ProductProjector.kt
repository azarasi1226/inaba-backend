package jp.inaba.service.infrastructure.projector.product

import jp.inaba.message.product.event.ProductCreatedEvent
import jp.inaba.message.product.event.ProductUpdatedEvent
import jp.inaba.service.infrastructure.jpa.product.ProductJpaEntity
import jp.inaba.service.infrastructure.jpa.product.ProductJpaRepository
import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
@ProcessingGroup(ProductProjectorEventProcessor.PROCESSOR_NAME)
class ProductProjector(
    private val productJpaRepository: ProductJpaRepository,
) {
    @EventHandler
    fun on(event: ProductCreatedEvent) {
        val entity =
            ProductJpaEntity(
                id = event.id,
                name = event.name,
                description = event.description,
                imageUrl = event.imageUrl,
                price = event.price,
                quantity = event.quantity,
            )

        productJpaRepository.save(entity)
    }

    @EventHandler
    fun on(event: ProductUpdatedEvent) {
        val maybeEntity = productJpaRepository.findById(event.id)

        if (maybeEntity.isPresent) {
            val entity = maybeEntity.get()
            val updatedEntity =
                entity.copy(
                    name = event.name,
                    description = event.description,
                    imageUrl = event.imageUrl,
                    price = event.price,
                )

            productJpaRepository.save(updatedEntity)
        }
    }
}

