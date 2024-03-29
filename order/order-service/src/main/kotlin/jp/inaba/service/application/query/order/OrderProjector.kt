package jp.inaba.service.application.query.order

import io.github.oshai.kotlinlogging.KotlinLogging
import jp.inaba.order.api.domain.order.OrderCompletedEvent
import jp.inaba.order.api.domain.order.OrderIssuedEvent
import jp.inaba.service.domain.order.OrderStatus
import jp.inaba.service.infrastructure.jpa.order.OrderEntity
import jp.inaba.service.infrastructure.jpa.order.OrderJpaRepository
import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
@ProcessingGroup(OrderProjector.PROCESSOR_NAME)
class OrderProjector(
    private val orderJpaRepository: OrderJpaRepository
) {
    private val logger = KotlinLogging.logger {}

    companion object {
        const val PROCESSOR_NAME = "OrderProjection"
    }

    @EventHandler
    fun on(event: OrderIssuedEvent) {
        logger.info { "handle OrderIssuedEvent {${event}}" }
        val entity = OrderEntity(
            id = event.id.value,
            status = OrderStatus.Issued,
            userId = event.userId
        )

        orderJpaRepository.save(entity)
    }

    @EventHandler
    fun on(event: OrderCompletedEvent) {
        logger.info { "handle OrderCompletedEvent {${event}}" }
        val entity = orderJpaRepository.findById(event.id.value).orElseThrow()

        entity.status = OrderStatus.Completed

        orderJpaRepository.save(entity)
    }
}