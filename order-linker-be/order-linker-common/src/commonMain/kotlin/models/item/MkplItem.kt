package ru.otus.otuskotlin.marketplace.common.models.item

import ru.otus.otuskotlin.marketplace.common.models.order.MkplOrderId

data class MkplItem(
    var id: MkplItemId = MkplItemId.NONE,
    var length: Int = 0,
    var width: Int = 0,
    var height: Int = 0,
    var weight: Int = 0,
    var type: MkplItemType = MkplItemType.NONE,
    var orderId: MkplOrderId = MkplOrderId.NONE,
    var lock: MkplItemLock = MkplItemLock.NONE,
) {
    fun isEmpty() = this == NONE

    companion object {
        private val NONE = MkplItem()
    }
}
