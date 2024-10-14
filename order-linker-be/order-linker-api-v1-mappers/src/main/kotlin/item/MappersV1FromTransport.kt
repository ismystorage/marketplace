//package ru.otus.otuskotlin.marketplace.mappers.v1.item
//
//import exceptions.UnknownRequestClass
//import ru.otus.otuskotlin.marketplace.api.v1.models.*
//import ru.otus.otuskotlin.marketplace.common.MkplOrderContext
//import ru.otus.otuskotlin.marketplace.common.models.*
//import ru.otus.otuskotlin.marketplace.common.models.order.MkplOrder
//import ru.otus.otuskotlin.marketplace.common.models.order.MkplOrderId
//import ru.otus.otuskotlin.marketplace.common.stubs.MkplStubs
//
//fun MkplOrderContext.fromTransport(request: IRequest) = when (request) {
//    is OrderCreateRequest -> fromTransport(request)
//    is OrderReadRequest -> fromTransport(request)
//    is OrderUpdateRequest -> fromTransport(request)
//    is OrderDeleteRequest -> fromTransport(request)
//    is OrderSearchRequest -> fromTransport(request)
//    is OrderCalculateRequest -> fromTransport(request)
//    else -> throw UnknownRequestClass(request.javaClass)
//}
//
//private fun String?.toAdId() = this?.let { MkplOrderId(it) } ?: MkplOrderId.NONE
//private fun String?.toAdWithId() = MkplOrder(id = this.toAdId())
//private fun String?.toAdLock() = this?.let { MkplAdLock(it) } ?: MkplAdLock.NONE
//
//private fun OrderDebug?.transportToWorkMode(): MkplWorkMode = when (this?.mode) {
//    OrderRequestDebugMode.PROD -> MkplWorkMode.PROD
//    OrderRequestDebugMode.TEST -> MkplWorkMode.TEST
//    OrderRequestDebugMode.STUB -> MkplWorkMode.STUB
//    null -> MkplWorkMode.PROD
//}
//
//private fun OrderDebug?.transportToStubCase(): MkplStubs = when (this?.stub) {
//    OrderRequestDebugStubs.SUCCESS -> MkplStubs.SUCCESS
//    OrderRequestDebugStubs.NOT_FOUND -> MkplStubs.NOT_FOUND
//    OrderRequestDebugStubs.BAD_ID -> MkplStubs.BAD_ID
//    OrderRequestDebugStubs.BAD_TITLE -> MkplStubs.BAD_TITLE
//    OrderRequestDebugStubs.BAD_DESCRIPTION -> MkplStubs.BAD_DESCRIPTION
//    OrderRequestDebugStubs.BAD_VISIBILITY -> MkplStubs.BAD_VISIBILITY
//    OrderRequestDebugStubs.CANNOT_DELETE -> MkplStubs.CANNOT_DELETE
//    OrderRequestDebugStubs.BAD_SEARCH_STRING -> MkplStubs.BAD_SEARCH_STRING
//    null -> MkplStubs.NONE
//}
//
//fun MkplOrderContext.fromTransport(request: OrderCreateRequest) {
//    command = MkplCommand.CREATE
//    adRequest = request.order?.toInternal() ?: MkplOrder()
//    workMode = request.debug.transportToWorkMode()
//    stubCase = request.debug.transportToStubCase()
//}
//
//fun MkplOrderContext.fromTransport(request: OrderReadRequest) {
//    command = MkplCommand.READ
//    adRequest = request.order.toInternal()
//    workMode = request.debug.transportToWorkMode()
//    stubCase = request.debug.transportToStubCase()
//}
//
//private fun OrderReadObject?.toInternal(): MkplOrder = if (this != null) {
//    MkplOrder(id = id.toAdId())
//} else {
//    MkplOrder()
//}
//
//
//fun MkplOrderContext.fromTransport(request: OrderUpdateRequest) {
//    command = MkplCommand.UPDATE
//    adRequest = request.order?.toInternal() ?: MkplOrder()
//    workMode = request.debug.transportToWorkMode()
//    stubCase = request.debug.transportToStubCase()
//}
//
//fun MkplOrderContext.fromTransport(request: OrderDeleteRequest) {
//    command = MkplCommand.DELETE
//    adRequest = request.order.toInternal()
//    workMode = request.debug.transportToWorkMode()
//    stubCase = request.debug.transportToStubCase()
//}
//
//private fun OrderDeleteObject?.toInternal(): MkplOrder = if (this != null) {
//    MkplOrder(
//        id = id.toAdId(),
//        lock = lock.toAdLock(),
//    )
//} else {
//    MkplOrder()
//}
//
//fun MkplOrderContext.fromTransport(request: OrderSearchRequest) {
//    command = MkplCommand.SEARCH
//    adFilterRequest = request.orderFilter.toInternal()
//    workMode = request.debug.transportToWorkMode()
//    stubCase = request.debug.transportToStubCase()
//}
//
//fun MkplOrderContext.fromTransport(request: OrderCalculateRequest) {
//    command = MkplCommand.CALCULATE
//    adRequest = request.order?.id.toAdWithId()
//    workMode = request.debug.transportToWorkMode()
//    stubCase = request.debug.transportToStubCase()
//}
//
//private fun OrderSearchFilter?.toInternal(): MkplAdFilter = MkplAdFilter(
//    searchString = this?.searchString ?: ""
//)
//
//private fun OrderCreateObject.toInternal(): MkplOrder = MkplOrder(
//    title = this.title ?: "",
//    description = this.description ?: "",
//    adType = this.adType.fromTransport(),
//    visibility = this.visibility.fromTransport(),
//)
//
//private fun OrderUpdateObject.toInternal(): MkplOrder = MkplOrder(
//    id = this.id.toAdId(),
//    title = this.title ?: "",
//    description = this.description ?: "",
//    adType = this.adType.fromTransport(),
//    visibility = this.visibility.fromTransport(),
//    lock = lock.toAdLock(),
//)
//
//private fun OrderVisibility?.fromTransport(): MkplVisibility = when (this) {
//    OrderVisibility.PUBLIC -> MkplVisibility.VISIBLE_PUBLIC
//    OrderVisibility.OWNER_ONLY -> MkplVisibility.VISIBLE_TO_OWNER
//    OrderVisibility.REGISTERED_ONLY -> MkplVisibility.VISIBLE_TO_GROUP
//    null -> MkplVisibility.NONE
//}
//
//private fun DealSide?.fromTransport(): MkplDealSide = when (this) {
//    DealSide.DEMAND -> MkplDealSide.DEMAND
//    DealSide.SUPPLY -> MkplDealSide.SUPPLY
//    null -> MkplDealSide.NONE
//}
//
