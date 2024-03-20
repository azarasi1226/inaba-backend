package jp.inaba.basket.service.presentation.model.basket

import jp.inaba.basket.api.domain.basket.BasketQueries
import jp.inaba.common.domain.shared.Page

data class GetBasketResponse(
    val basketId: String,
    val page: Page<BasketQueries.ItemDataModel>
)