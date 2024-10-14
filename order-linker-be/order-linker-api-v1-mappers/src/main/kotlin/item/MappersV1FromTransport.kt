package ru.otus.otuskotlin.marketplace.mappers.v1.item

import exceptions.UnknownRequestClass
import ru.otus.otuskotlin.marketplace.api.v1.models.*
import ru.otus.otuskotlin.marketplace.common.MkplItemContext
import ru.otus.otuskotlin.marketplace.common.models.*
import ru.otus.otuskotlin.marketplace.common.models.item.*
import ru.otus.otuskotlin.marketplace.common.models.order.MkplOrderId
import ru.otus.otuskotlin.marketplace.common.stubs.MkplStubs

fun MkplItemContext.fromTransport(request: ItemIRequest) = when (request) {
    is ItemCreateRequest -> fromTransport(request)
    is ItemReadRequest -> fromTransport(request)
    is ItemUpdateRequest -> fromTransport(request)
    is ItemDeleteRequest -> fromTransport(request)
    is ItemSearchRequest -> fromTransport(request)
    is ItemAvailablePackageRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun String?.toItemId() = this?.let { MkplItemId(it) } ?: MkplOrderId.NONE
private fun String?.toItemWithId() = MkplItem(id = this.toItemId() as MkplItemId)
private fun String?.toItemLock() = this?.let { MkplItemLock(it) } ?: MkplItemLock.NONE

private fun ItemDebug?.transportToWorkMode(): MkplWorkMode = when (this?.mode) {
    ItemRequestDebugMode.PROD -> MkplWorkMode.PROD
    ItemRequestDebugMode.TEST -> MkplWorkMode.TEST
    ItemRequestDebugMode.STUB -> MkplWorkMode.STUB
    null -> MkplWorkMode.PROD
}

private fun ItemDebug?.transportToStubCase(): MkplStubs = when (this?.stub) {
    ItemRequestDebugStubs.SUCCESS -> MkplStubs.SUCCESS
    ItemRequestDebugStubs.NOT_FOUND -> MkplStubs.NOT_FOUND
    ItemRequestDebugStubs.BAD_ID -> MkplStubs.BAD_ID
    ItemRequestDebugStubs.BAD_TITLE -> MkplStubs.BAD_TITLE
    ItemRequestDebugStubs.BAD_DESCRIPTION -> MkplStubs.BAD_DESCRIPTION
    ItemRequestDebugStubs.BAD_VISIBILITY -> MkplStubs.BAD_VISIBILITY
    ItemRequestDebugStubs.CANNOT_DELETE -> MkplStubs.CANNOT_DELETE
    ItemRequestDebugStubs.BAD_SEARCH_STRING -> MkplStubs.BAD_SEARCH_STRING
    null -> MkplStubs.NONE
}

fun MkplItemContext.fromTransport(request: ItemCreateRequest) {
    command = MkplCommand.CREATE
    itemRequest = request.item?.toInternal() ?: MkplItem()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun MkplItemContext.fromTransport(request: ItemReadRequest) {
    command = MkplCommand.READ
    itemRequest = request.item.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun ItemReadObject?.toInternal(): MkplItem = if (this != null) {
    MkplItem(id = id.toItemId() as MkplItemId)
} else {
    MkplItem()
}

fun MkplItemContext.fromTransport(request: ItemUpdateRequest) {
    command = MkplCommand.UPDATE
    itemRequest = request.item?.toInternal() ?: MkplItem()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun MkplItemContext.fromTransport(request: ItemDeleteRequest) {
    command = MkplCommand.DELETE
    itemRequest = request.item.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun ItemType?.fromTransport(): MkplItemType = when (this) {
    ItemType.ORDINARY -> MkplItemType.ORDINARY
    ItemType.GLASS -> MkplItemType.GLASS
    ItemType.ELECTRONICS -> MkplItemType.ELECTRONICS
    ItemType.TEMPERATURE -> MkplItemType.TEMPERATURE
    null -> MkplItemType.NONE
}

private fun ItemDeleteObject?.toInternal(): MkplItem = if (this != null) {
    MkplItem(
        id = id.toItemId() as MkplItemId,
        lock = lock.toItemLock(),
    )
} else {
    MkplItem()
}

fun MkplItemContext.fromTransport(request: ItemSearchRequest) {
    command = MkplCommand.SEARCH
    itemFilterRequest = request.itemFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun MkplItemContext.fromTransport(request: ItemAvailablePackageRequest) {
    command = MkplCommand.CALCULATE
    itemRequest = request.item?.id.toItemWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun ItemSearchFilter?.toInternal(): MkplItemFilter = MkplItemFilter(
    searchString = this?.searchString ?: ""
)

private fun ItemCreateObject.toInternal(): MkplItem = MkplItem(
    length = this.length ?: 0,
    width = this.width ?: 0,
    height = this.height ?: 0,
    weight = this.weight ?: 0,
    type = this.type.fromTransport(),
    orderId = this.orderId ?: "",
)

private fun ItemUpdateObject.toInternal(): MkplItem = MkplItem(
    id = this.id.toItemId() as MkplItemId,
    length = this.length ?: 0,
    width = this.width ?: 0,
    height = this.height ?: 0,
    weight = this.weight ?: 0,
    type = this.type.fromTransport(),
    lock = lock.toItemLock(),
)
