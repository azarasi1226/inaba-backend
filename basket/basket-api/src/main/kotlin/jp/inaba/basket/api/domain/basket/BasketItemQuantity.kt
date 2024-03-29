package jp.inaba.basket.api.domain.basket

data class BasketItemQuantity(val value: Int) {
    companion object {
        private const val MIN = 1
        private const val MAX = 99
    }

    init {
        if(value !in MIN..MAX) {
            throw IllegalArgumentException("買い物かごアイテムの最大個数は[${MIN} ~ ${MAX}]の間です。現在の個数[${value}]")
        }
    }
}