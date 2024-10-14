package ru.otus.otuskotlin.marketplace.mappers.v1.order

import exceptions.UnknownRequestClass
import kotlinx.datetime.Clock
import ru.otus.otuskotlin.marketplace.api.v1.models.*
import ru.otus.otuskotlin.marketplace.common.MkplOrderContext
import ru.otus.otuskotlin.marketplace.common.models.MkplCommand
import ru.otus.otuskotlin.marketplace.common.models.MkplWorkMode
import ru.otus.otuskotlin.marketplace.common.models.order.*
import ru.otus.otuskotlin.marketplace.common.stubs.MkplStubs

fun MkplOrderContext.fromTransport(request: IRequest) = when (request) {
    is OrderCreateRequest -> fromTransport(request)
    is OrderReadRequest -> fromTransport(request)
    is OrderUpdateRequest -> fromTransport(request)
    is OrderDeleteRequest -> fromTransport(request)
    is OrderSearchRequest -> fromTransport(request)
    is OrderCalculateRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun String?.toOrderId() = this?.let { MkplOrderId(it) } ?: MkplOrderId.NONE
private fun String?.toAdWithId() = MkplOrder(id = this.toOrderId())
private fun String?.toAdLock() = this?.let { MkplOrderLock(it) } ?: MkplOrderLock.NONE

private fun OrderDebug?.transportToWorkMode(): MkplWorkMode = when (this?.mode) {
    OrderRequestDebugMode.PROD -> MkplWorkMode.PROD
    OrderRequestDebugMode.TEST -> MkplWorkMode.TEST
    OrderRequestDebugMode.STUB -> MkplWorkMode.STUB
    null -> MkplWorkMode.PROD
}

private fun OrderDebug?.transportToStubCase(): MkplStubs = when (this?.stub) {
    OrderRequestDebugStubs.SUCCESS -> MkplStubs.SUCCESS
    OrderRequestDebugStubs.NOT_FOUND -> MkplStubs.NOT_FOUND
    OrderRequestDebugStubs.BAD_ID -> MkplStubs.BAD_ID
    OrderRequestDebugStubs.BAD_TITLE -> MkplStubs.BAD_TITLE
    OrderRequestDebugStubs.BAD_DESCRIPTION -> MkplStubs.BAD_DESCRIPTION
    OrderRequestDebugStubs.BAD_VISIBILITY -> MkplStubs.BAD_VISIBILITY
    OrderRequestDebugStubs.CANNOT_DELETE -> MkplStubs.CANNOT_DELETE
    OrderRequestDebugStubs.BAD_SEARCH_STRING -> MkplStubs.BAD_SEARCH_STRING
    null -> MkplStubs.NONE
}

fun MkplOrderContext.fromTransport(request: OrderCreateRequest) {
    command = MkplCommand.CREATE
    orderRequest = request.order?.toInternal() ?: MkplOrder()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun MkplOrderContext.fromTransport(request: OrderReadRequest) {
    command = MkplCommand.READ
    orderRequest = request.order.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun OrderReadObject?.toInternal(): MkplOrder = if (this != null) {
    MkplOrder(id = id.toOrderId())
} else {
    MkplOrder()
}

fun MkplOrderContext.fromTransport(request: OrderUpdateRequest) {
    command = MkplCommand.UPDATE
    orderRequest = request.order?.toInternal() ?: MkplOrder()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun MkplOrderContext.fromTransport(request: OrderDeleteRequest) {
    command = MkplCommand.DELETE
    orderRequest = request.order.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun OrderDeleteObject?.toInternal(): MkplOrder = if (this != null) {
    MkplOrder(
        id = id.toOrderId(),
        lock = lock.toAdLock(),
    )
} else {
    MkplOrder()
}

fun MkplOrderContext.fromTransport(request: OrderSearchRequest) {
    command = MkplCommand.SEARCH
    orderFilterRequest = request.orderFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun MkplOrderContext.fromTransport(request: OrderCalculateRequest) {
    command = MkplCommand.CALCULATE
    orderRequest = request.order?.id.toAdWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun OrderSearchFilter?.toInternal(): MkplOrderFilter = MkplOrderFilter(
    searchString = this?.searchString ?: ""
)

private fun OrderCreateObject.toInternal(): MkplOrder = MkplOrder(
    createdAt = this.createdAt ?: Clock.System.now().toString(),
    updatedAt = this.updatedAt ?: Clock.System.now().toString(),
    orderNumber = this.orderNumber ?: "",
    status = this.status.fromTransport(),
    packages = this.packages.fromTransport(),
    visibility = this.visibility.fromTransport(),
)

private fun OrderUpdateObject.toInternal(): MkplOrder = MkplOrder(
    id = this.id.toOrderId(),
    createdAt = this.createdAt ?: Clock.System.now().toString(),
    updatedAt = this.updatedAt ?: Clock.System.now().toString(),
    orderNumber = this.orderNumber ?: "",
    status = this.status.fromTransport(),
    packages = this.packages.fromTransport(),
    visibility = this.visibility.fromTransport(),
    lock = lock.toAdLock(),
)

private fun OrderStatus?.fromTransport(): MkplOrderStatus = when (this) {
    OrderStatus.CREATED -> MkplOrderStatus.CREATED
    OrderStatus.IN_PROGRESS -> MkplOrderStatus.IN_PROGRESS
    OrderStatus.COMPLETED -> MkplOrderStatus.COMPLETED
    OrderStatus.CANCELLED -> MkplOrderStatus.CANCELLED
    OrderStatus.NULL -> MkplOrderStatus.NONE
    null -> MkplOrderStatus.NONE
}

private fun List<PackageType>?.fromTransport(): List<MkplPackageType> {
    return this?.map {
        when (it) {
            PackageType.SMALL -> MkplPackageType.SMALL
            PackageType.MEDIUM -> MkplPackageType.MEDIUM
            PackageType.LARGE -> MkplPackageType.LARGE
        }
    } ?: emptyList()
}

private fun OrderVisibility?.fromTransport(): MkplVisibility = when (this) {
    OrderVisibility.PUBLIC -> MkplVisibility.VISIBLE_PUBLIC
    OrderVisibility.OWNER_ONLY -> MkplVisibility.VISIBLE_TO_OWNER
    OrderVisibility.REGISTERED_ONLY -> MkplVisibility.VISIBLE_TO_GROUP
    null -> MkplVisibility.NONE
}
