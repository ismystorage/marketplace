package ru.otus.otuskotlin.marketplace.common.models.order

data class MkplOrder(
    var id: MkplOrderId = MkplOrderId.NONE,
    var createdAt: String = "",
    var updatedAt: String = "",
    var orderNumber: String = "",
    var status: MkplOrderStatus = MkplOrderStatus.NONE,
    var packages: List<MkplPackageType> = emptyList(),
    var ownerId: MkplUserId = MkplUserId.NONE,
    var visibility: MkplVisibility = MkplVisibility.NONE,
    var lock: MkplOrderLock = MkplOrderLock.NONE,
    val permissionsClient: MutableSet<MkplOrderPermissionClient> = mutableSetOf()
) {
    fun isEmpty() = this == NONE

    companion object {
        private val NONE = MkplOrder()
    }
}
