package ru.otus.otuskotlin.marketplace.common.models.order

data class MkplOrderFilter(
    var searchString: String = "",
    var ownerId: MkplUserId = MkplUserId.NONE,
)
