package jp.inaba.basket.service.application.command.basket.setbasketitem

import jp.inaba.basket.api.domain.basket.BasketCommands
import jp.inaba.basket.service.domain.basket.CanSetBasketItemVerifier
import jp.inaba.basket.service.domain.basket.InternalBasketCommands
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Component

@Component
class SetBasketItemInteractor(
    private val canSetBasketItemVerifier: CanSetBasketItemVerifier,
    private val commandGateway: CommandGateway
) {
    @CommandHandler
    fun handle(command: BasketCommands.SetBasketItem) {
        if(!canSetBasketItemVerifier.existProduct(command.productId)) {
            throw ProductNotFoundException(command.productId)
        }

        val internalCommand = InternalBasketCommands.SetBasketItem(
            id = command.id,
            productId = command.productId,
            basketItemQuantity = command.basketItemQuantity
        )

        commandGateway.sendAndWait<Any>(internalCommand)
    }
}