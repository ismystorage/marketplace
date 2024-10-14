//package ru.otus.otuskotlin.marketplace.mappers.v1.item
//
//import ru.otus.otuskotlin.marketplace.api.v1.models.*
//import ru.otus.otuskotlin.marketplace.common.MkplOrderContext
//import ru.otus.otuskotlin.marketplace.common.exceptions.UnknownMkplCommand
//import ru.otus.otuskotlin.marketplace.common.models.*
//import ru.otus.otuskotlin.marketplace.common.models.order.MkplOrder
//import ru.otus.otuskotlin.marketplace.common.models.order.MkplOrderId
//
//fun MkplOrderContext.toTransportAd(): IResponse = when (val cmd = command) {
//    MkplCommand.CREATE -> toTransportCreate()
//    MkplCommand.READ -> toTransportRead()
//    MkplCommand.UPDATE -> toTransportUpdate()
//    MkplCommand.DELETE -> toTransportDelete()
//    MkplCommand.SEARCH -> toTransportSearch()
//    MkplCommand.CALCULATE -> toTransportOffers()
//    MkplCommand.NONE -> throw UnknownMkplCommand(cmd)
//}
//
//fun MkplOrderContext.toTransportCreate() = OrderCreateResponse(
//    result = state.toResult(),
//    errors = errors.toTransportErrors(),
//    order = adResponse.toTransportAd()
//)
//
//fun MkplOrderContext.toTransportRead() = OrderReadResponse(
//    result = state.toResult(),
//    errors = errors.toTransportErrors(),
//    order = adResponse.toTransportAd()
//)
//
//fun MkplOrderContext.toTransportUpdate() = OrderUpdateResponse(
//    result = state.toResult(),
//    errors = errors.toTransportErrors(),
//    order = adResponse.toTransportAd()
//)
//
//fun MkplOrderContext.toTransportDelete() = OrderDeleteResponse(
//    result = state.toResult(),
//    errors = errors.toTransportErrors(),
//    order = adResponse.toTransportAd()
//)
//
//fun MkplOrderContext.toTransportSearch() = OrderSearchResponse(
//    result = state.toResult(),
//    errors = errors.toTransportErrors(),
//    orders = adsResponse.toTransportAd()
//)
//
//fun MkplOrderContext.toTransportOffers() = OrderCalculateResponse(
//    result = state.toResult(),
//    errors = errors.toTransportErrors(),
//    orders = adsResponse.toTransportAd()
//)
//
//fun List<MkplOrder>.toTransportAd(): List<OrderResponseObject>? = this
//    .map { it.toTransportAd() }
//    .toList()
//    .takeIf { it.isNotEmpty() }
//
//private fun MkplOrder.toTransportAd(): OrderResponseObject = OrderResponseObject(
//    id = id.takeIf { it != MkplOrderId.NONE }?.asString(),
//    title = title.takeIf { it.isNotBlank() },
//    description = description.takeIf { it.isNotBlank() },
//    ownerId = ownerId.takeIf { it != MkplUserId.NONE }?.asString(),
//    adType = adType.toTransportAd(),
//    visibility = visibility.toTransportAd(),
//    permissions = permissionsClient.toTransportAd(),
//)
//
//private fun Set<MkplAdPermissionClient>.toTransportAd(): Set<OrderPermissions>? = this
//    .map { it.toTransportAd() }
//    .toSet()
//    .takeIf { it.isNotEmpty() }
//
//private fun MkplAdPermissionClient.toTransportAd() = when (this) {
//    MkplAdPermissionClient.READ -> OrderPermissions.READ
//    MkplAdPermissionClient.UPDATE -> OrderPermissions.UPDATE
//    MkplAdPermissionClient.MAKE_VISIBLE_OWNER -> OrderPermissions.MAKE_VISIBLE_OWN
//    MkplAdPermissionClient.MAKE_VISIBLE_GROUP -> OrderPermissions.MAKE_VISIBLE_GROUP
//    MkplAdPermissionClient.MAKE_VISIBLE_PUBLIC -> OrderPermissions.MAKE_VISIBLE_PUBLIC
//    MkplAdPermissionClient.DELETE -> OrderPermissions.DELETE
//}
//
//private fun MkplVisibility.toTransportAd(): OrderVisibility? = when (this) {
//    MkplVisibility.VISIBLE_PUBLIC -> OrderVisibility.PUBLIC
//    MkplVisibility.VISIBLE_TO_GROUP -> OrderVisibility.REGISTERED_ONLY
//    MkplVisibility.VISIBLE_TO_OWNER -> OrderVisibility.OWNER_ONLY
//    MkplVisibility.NONE -> null
//}
//
//private fun MkplDealSide.toTransportAd(): DealSide? = when (this) {
//    MkplDealSide.DEMAND -> DealSide.DEMAND
//    MkplDealSide.SUPPLY -> DealSide.SUPPLY
//    MkplDealSide.NONE -> null
//}
//
//private fun List<MkplError>.toTransportErrors(): List<Error>? = this
//    .map { it.toTransportAd() }
//    .toList()
//    .takeIf { it.isNotEmpty() }
//
//private fun MkplError.toTransportAd() = Error(
//    code = code.takeIf { it.isNotBlank() },
//    group = group.takeIf { it.isNotBlank() },
//    field = field.takeIf { it.isNotBlank() },
//    message = message.takeIf { it.isNotBlank() },
//)
//
//private fun MkplState.toResult(): ResponseResult? = when (this) {
//    MkplState.RUNNING -> ResponseResult.SUCCESS
//    MkplState.FAILING -> ResponseResult.ERROR
//    MkplState.FINISHING -> ResponseResult.SUCCESS
//    MkplState.NONE -> null
//}
