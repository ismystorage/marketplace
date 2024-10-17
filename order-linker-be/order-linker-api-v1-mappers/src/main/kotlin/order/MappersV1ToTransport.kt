package ru.otus.otuskotlin.marketplace.mappers.v1.order

import ru.otus.otuskotlin.marketplace.api.v1.models.*
import ru.otus.otuskotlin.marketplace.common.MkplOrderContext
import ru.otus.otuskotlin.marketplace.common.exceptions.UnknownMkplCommand
import ru.otus.otuskotlin.marketplace.common.models.MkplCommand
import ru.otus.otuskotlin.marketplace.common.models.MkplError
import ru.otus.otuskotlin.marketplace.common.models.MkplState
import ru.otus.otuskotlin.marketplace.common.models.order.*

fun MkplOrderContext.toTransport(): IResponse = when (val cmd = command) {
    MkplCommand.CREATE -> toTransportCreate()
    MkplCommand.READ -> toTransportRead()
    MkplCommand.UPDATE -> toTransportUpdate()
    MkplCommand.DELETE -> toTransportDelete()
    MkplCommand.SEARCH -> toTransportSearch()
    MkplCommand.CALCULATE -> toTransportOffers()
    MkplCommand.NONE -> throw UnknownMkplCommand(cmd)
}

fun MkplOrderContext.toTransportCreate() = OrderCreateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    order = orderResponse.toTransportOrder()
)

fun MkplOrderContext.toTransportRead() = OrderReadResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    order = orderResponse.toTransportOrder()
)

fun MkplOrderContext.toTransportUpdate() = OrderUpdateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    order = orderResponse.toTransportOrder()
)

fun MkplOrderContext.toTransportDelete() = OrderDeleteResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    order = orderResponse.toTransportOrder()
)

fun MkplOrderContext.toTransportSearch() = OrderSearchResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    orders = ordersResponse.toTransport()
)

fun MkplOrderContext.toTransportOffers() = OrderCalculateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    orders = ordersResponse.toTransport()
)

fun List<MkplOrder>.toTransport(): List<OrderResponseObject>? = this
    .map { it.toTransportOrder() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun MkplOrder.toTransportOrder(): OrderResponseObject = OrderResponseObject(
    id = id.takeIf { it != MkplOrderId.NONE }?.asString(),
    createdAt = createdAt.takeIf { it.isNotBlank() },
    updatedAt = updatedAt.takeIf { it.isNotBlank() },
    orderNumber = orderNumber.takeIf { it.isNotBlank() },
    status = status.toTransport(),
    packages = packages.toTransportPackages(),
    ownerId = ownerId.takeIf { it != MkplUserId.NONE }?.asString(),
    visibility = visibility.toTransport(),
    permissions = permissionsClient.toTransport(),
)

private fun List<MkplPackageType>.toTransportPackages(): List<PackageType>? = this
    .map { it.toTransportOrder() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun Set<MkplOrderPermissionClient>.toTransport(): Set<OrderPermissions>? = this
    .map { it.toTransport() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun MkplOrderStatus.toTransport(): OrderStatus? = when (this) {
    MkplOrderStatus.CREATED -> OrderStatus.CREATED
    MkplOrderStatus.IN_PROGRESS -> OrderStatus.IN_PROGRESS
    MkplOrderStatus.COMPLETED -> OrderStatus.COMPLETED
    MkplOrderStatus.CANCELLED -> OrderStatus.CANCELLED
    MkplOrderStatus.NONE -> null
}

private fun MkplPackageType.toTransportOrder() = when (this) {
            MkplPackageType.SMALL -> PackageType.SMALL
            MkplPackageType.MEDIUM -> PackageType.MEDIUM
            MkplPackageType.LARGE -> PackageType.LARGE
}

private fun MkplOrderPermissionClient.toTransport() = when (this) {
    MkplOrderPermissionClient.READ -> OrderPermissions.READ
    MkplOrderPermissionClient.UPDATE -> OrderPermissions.UPDATE
    MkplOrderPermissionClient.MAKE_VISIBLE_OWNER -> OrderPermissions.MAKE_VISIBLE_OWN
    MkplOrderPermissionClient.MAKE_VISIBLE_GROUP -> OrderPermissions.MAKE_VISIBLE_GROUP
    MkplOrderPermissionClient.MAKE_VISIBLE_PUBLIC -> OrderPermissions.MAKE_VISIBLE_PUBLIC
    MkplOrderPermissionClient.DELETE -> OrderPermissions.DELETE
}

private fun MkplVisibility.toTransport(): OrderVisibility? = when (this) {
    MkplVisibility.VISIBLE_PUBLIC -> OrderVisibility.PUBLIC
    MkplVisibility.VISIBLE_TO_GROUP -> OrderVisibility.REGISTERED_ONLY
    MkplVisibility.VISIBLE_TO_OWNER -> OrderVisibility.OWNER_ONLY
    MkplVisibility.NONE -> null
}

private fun List<MkplError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransport() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun MkplError.toTransport() = Error(
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
