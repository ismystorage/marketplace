package ru.otus.otuskotlin.marketplace.common

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.marketplace.common.models.MkplCommand
import ru.otus.otuskotlin.marketplace.common.models.MkplError
import ru.otus.otuskotlin.marketplace.common.models.MkplState
import ru.otus.otuskotlin.marketplace.common.models.MkplWorkMode
import ru.otus.otuskotlin.marketplace.common.models.item.MkplItem
import ru.otus.otuskotlin.marketplace.common.models.item.MkplItemFilter
import ru.otus.otuskotlin.marketplace.common.models.order.MkplRequestId
import ru.otus.otuskotlin.marketplace.common.stubs.MkplStubs

data class MkplItemContext(
    var command: MkplCommand = MkplCommand.NONE,
    var state: MkplState = MkplState.NONE,
    val errors: MutableList<MkplError> = mutableListOf(),

    var workMode: MkplWorkMode = MkplWorkMode.PROD,
    var stubCase: MkplStubs = MkplStubs.NONE,

    var requestId: MkplRequestId = MkplRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var itemRequest: MkplItem = MkplItem(),
    var itemFilterRequest: MkplItemFilter = MkplItemFilter(),

    var itemResponse: MkplItem = MkplItem(),
    var itemsResponse: MutableList<MkplItem> = mutableListOf(),
)
