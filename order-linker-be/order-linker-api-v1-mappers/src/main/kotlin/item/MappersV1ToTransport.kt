package ru.otus.otuskotlin.marketplace.mappers.v1.item

import ru.otus.otuskotlin.marketplace.api.v1.models.*
import ru.otus.otuskotlin.marketplace.common.MkplItemContext
import ru.otus.otuskotlin.marketplace.common.exceptions.UnknownMkplCommand
import ru.otus.otuskotlin.marketplace.common.models.MkplCommand
import ru.otus.otuskotlin.marketplace.common.models.MkplError
import ru.otus.otuskotlin.marketplace.common.models.MkplState
import ru.otus.otuskotlin.marketplace.common.models.item.MkplItem
import ru.otus.otuskotlin.marketplace.common.models.item.MkplItemId
import ru.otus.otuskotlin.marketplace.common.models.item.MkplItemType

fun MkplItemContext.toTransportItem(): IResponse = when (val cmd = command) {
    MkplCommand.CREATE -> toTransportCreate()
    MkplCommand.READ -> toTransportRead()
    MkplCommand.UPDATE -> toTransportUpdate()
    MkplCommand.DELETE -> toTransportDelete()
    MkplCommand.SEARCH -> toTransportSearch()
    MkplCommand.CALCULATE -> toTransportItemAvailable()
    MkplCommand.NONE -> throw UnknownMkplCommand(cmd)
}

fun MkplItemContext.toTransportCreate() = ItemCreateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    item = itemResponse.toTransportAd()
)

fun MkplItemContext.toTransportRead() = ItemReadResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    item = itemResponse.toTransportAd()
)

fun MkplItemContext.toTransportUpdate() = ItemUpdateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    item = itemResponse.toTransportAd()
)

fun MkplItemContext.toTransportDelete() = ItemDeleteResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    item = itemResponse.toTransportAd()
)

fun MkplItemContext.toTransportSearch() = ItemSearchResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    items = itemsResponse.toTransportAd()
)

fun MkplItemContext.toTransportItemAvailable() = ItemAvailablePackageResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    items = itemsResponse.toTransportAd()
)

fun List<MkplItem>.toTransportAd(): List<ItemResponseObject>? = this
    .map { it.toTransportAd() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun MkplItem.toTransportAd(): ItemResponseObject = ItemResponseObject(
    id = id.takeIf { it != MkplItemId.NONE }?.asString(),
    length = length.takeIf { it > 0 },
    width = width.takeIf { it > 0 },
    height = height.takeIf { it > 0 },
    weight = weight.takeIf { it > 0 },
    type = type.toTransport(),
    orderId = orderId.takeIf { it.isNotBlank() },
)

private fun MkplItemType.toTransport(): ItemType? = when (this) {
    MkplItemType.ORDINARY -> ItemType.ORDINARY
    MkplItemType.GLASS -> ItemType.GLASS
    MkplItemType.ELECTRONICS -> ItemType.ELECTRONICS
    MkplItemType.TEMPERATURE -> ItemType.TEMPERATURE
    MkplItemType.NONE -> null
}

private fun List<MkplError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportAd() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun MkplError.toTransportAd() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)

private fun MkplState.toResult(): ResponseResult? = when (this) {
    MkplState.RUNNING -> ResponseResult.SUCCESS
    MkplState.FAILING -> ResponseResult.ERROR
    MkplState.FINISHING -> ResponseResult.SUCCESS
    MkplState.NONE -> null
}
