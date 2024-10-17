package ru.otus.otuskotlin.marketplace.mappers.v1.item

import ru.otus.otuskotlin.marketplace.api.v1.models.*
import ru.otus.otuskotlin.marketplace.common.MkplItemContext
import ru.otus.otuskotlin.marketplace.common.exceptions.UnknownMkplCommand
import ru.otus.otuskotlin.marketplace.common.models.*
import ru.otus.otuskotlin.marketplace.common.models.item.MkplItem
import ru.otus.otuskotlin.marketplace.common.models.item.MkplItemId
import ru.otus.otuskotlin.marketplace.common.models.order.MkplOrderPermissionClient
import ru.otus.otuskotlin.marketplace.mappers.v1.order.toTransport


fun MkplItemContext.toTransportAd(): IResponse = when (val cmd = command) {
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

private fun Set<MkplItemPermissionClient>.toTransport(): Set<ItemPermissions>? = this
    .map { it.toTransport() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun MkplItem.toTransportAd(): ItemResponseObject = ItemResponseObject(
    id = id.takeIf { it != MkplItemId.NONE }?.asString(),
    length = length.takeIf { true },
    width = width.takeIf { true },
    height = height.takeIf { true },
    weight = weight.takeIf { true },
    type = ,
    orderId = ,
    permissions = permissionsClient.toTransportAd(),
)

private fun MkplItemPermissionClient.toTransport() = when (this) {
    MkplOrderPermissionClient.READ -> OrderPermissions.READ
    MkplOrderPermissionClient.UPDATE -> OrderPermissions.UPDATE
    MkplOrderPermissionClient.MAKE_VISIBLE_OWNER -> OrderPermissions.MAKE_VISIBLE_OWN
    MkplOrderPermissionClient.MAKE_VISIBLE_GROUP -> OrderPermissions.MAKE_VISIBLE_GROUP
    MkplOrderPermissionClient.MAKE_VISIBLE_PUBLIC -> OrderPermissions.MAKE_VISIBLE_PUBLIC
    MkplOrderPermissionClient.DELETE -> OrderPermissions.DELETE
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
