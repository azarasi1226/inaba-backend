package jp.inaba.service.infrastructure.domain.basket

import jp.inaba.core.domain.basket.BasketItemQuantity
import jp.inaba.core.domain.product.ProductId
import jp.inaba.service.domain.basket.CanSetBasketItemVerifier
import jp.inaba.service.infrastructure.jpa.lookupproduct.LookupProductJpaRepository
import jp.inaba.service.infrastructure.jpa.product.ProductJpaRepository
import org.springframework.stereotype.Service

@Service
class CanSetBasketItemVerifierImpl(
    private val lookupProductJpaRepository: LookupProductJpaRepository,
    private val productJpaRepository: ProductJpaRepository,
) : CanSetBasketItemVerifier {
    override fun isProductNotFound(productId: ProductId): Boolean {
        return !lookupProductJpaRepository.existsById(productId.value)
    }

    override fun isOutOfStock(
        productId: ProductId,
        basketItemQuantity: BasketItemQuantity,
    ): Boolean {
        val entity = productJpaRepository.findById(productId.value).orElseThrow()

        return entity.quantity < basketItemQuantity.value
    }
}
