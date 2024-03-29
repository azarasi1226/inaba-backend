package jp.inaba.basket.service.application.query.basket

import de.huxhorn.sulky.ulid.ULID
import jp.inaba.basket.api.domain.basket.BasketEvents
import jp.inaba.basket.service.infrastructure.jpa.basket.BasketJpaEntity
import jp.inaba.basket.service.infrastructure.jpa.basket.BasketJpaRepository
import jp.inaba.basket.service.infrastructure.jpa.basketitem.BasketItemId
import jp.inaba.basket.service.infrastructure.jpa.basketitem.BasketItemJpaEntity
import jp.inaba.basket.service.infrastructure.jpa.basketitem.BasketItemJpaRepository
import jp.inaba.basket.service.infrastructure.jpa.product.ProductJpaRepository
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
class BasketProjector(
    private val basketJpaRepository: BasketJpaRepository,
    private val productJpaRepository: ProductJpaRepository,
    private val basketItemJpaRepository: BasketItemJpaRepository
) {
    @EventHandler
    fun on(event: BasketEvents.Created) {
        val basket = BasketJpaEntity(
            id = event.id,
            userId = event.userId
        )

        basketJpaRepository.save(basket)
    }

    @EventHandler
    fun on(event: BasketEvents.BasketItemSet) {
        val basketJpaEntity = basketJpaRepository.findById(event.id)
            .orElseThrow { Exception("Basketが存在しません") }

        val productJpaEntity = productJpaRepository.findById(event.productId)
            .orElseThrow { Exception("Productが存在しません") }

        val id = BasketItemId(
            basketId = basketJpaEntity.id,
            productId = productJpaEntity.id
        )

        val basketItemJpaEntity = BasketItemJpaEntity(
            basketItemId = id,
            basket = basketJpaEntity,
            product = productJpaEntity,
            itemQuantity = event.basketItemQuantity
        )

        basketItemJpaRepository.save(basketItemJpaEntity)
    }

    @EventHandler
    fun on(event: BasketEvents.BasketItemDeleted) {
        basketItemJpaRepository.deleteByBasketIdAndProductId(
            basketId = event.id,
            productId = event.productId
        )
    }

    @EventHandler
    fun on(event: BasketEvents.Cleared) {
        basketItemJpaRepository.deleteByBasketId(event.id)
    }
}