package ru.otus.otuskotlin.marketplace.common.models.item

import ru.otus.otuskotlin.marketplace.common.models.order.MkplUserId

data class MkplItemFilter(
    var searchString: String = "",
    var ownerId: MkplUserId = MkplUserId.NONE,
)
