import org.junit.Test
import ru.otus.otuskotlin.marketplace.api.v1.models.*
import ru.otus.otuskotlin.marketplace.common.MkplOrderContext
import ru.otus.otuskotlin.marketplace.common.models.MkplCommand
import ru.otus.otuskotlin.marketplace.common.models.MkplError
import ru.otus.otuskotlin.marketplace.common.models.MkplState
import ru.otus.otuskotlin.marketplace.common.models.MkplWorkMode
import ru.otus.otuskotlin.marketplace.common.models.order.*
import ru.otus.otuskotlin.marketplace.common.stubs.MkplStubs
import ru.otus.otuskotlin.marketplace.mappers.v1.order.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v1.order.toTransport
import kotlin.test.assertEquals

class OrderMapperTest {
    @Test
    fun fromTransport() {
        val req = OrderCreateRequest(
            debug = OrderDebug(
                mode = OrderRequestDebugMode.STUB,
                stub = OrderRequestDebugStubs.SUCCESS,
            ),
            order = OrderCreateObject(
                createdAt = "2024-10-19T07:37:45.186396300Z",
                updatedAt = "2024-10-19T08:37:45.186396300Z",
                orderNumber = "123",
                status = OrderStatus.CREATED,
                packages = listOf(PackageType.SMALL, PackageType.SMALL),
                visibility = OrderVisibility.PUBLIC,
            ),
        )

        val context = MkplOrderContext()
        context.fromTransport(req)

        assertEquals(MkplStubs.SUCCESS, context.stubCase)
        assertEquals(MkplWorkMode.STUB, context.workMode)
        assertEquals("2024-10-19T07:37:45.186396300Z", context.orderRequest.createdAt)
        assertEquals("2024-10-19T08:37:45.186396300Z", context.orderRequest.updatedAt)
        assertEquals("123", context.orderRequest.orderNumber)
        assertEquals(MkplOrderStatus.CREATED, context.orderRequest.status)
        assertEquals(listOf(MkplPackageType.SMALL, MkplPackageType.SMALL), context.orderRequest.packages)
        assertEquals(MkplVisibility.VISIBLE_PUBLIC, context.orderRequest.visibility)
    }

    @Test
    fun toTransport() {
        val context = MkplOrderContext(
            requestId = MkplRequestId("1234"),
            command = MkplCommand.CREATE,
            orderResponse = MkplOrder(
                createdAt = "2024-10-19T07:37:45.186396300Z",
                updatedAt = "2024-10-19T08:37:45.186396300Z",
                orderNumber = "123",
                status = MkplOrderStatus.CREATED,
                packages = listOf(MkplPackageType.SMALL, MkplPackageType.SMALL),
                visibility = MkplVisibility.VISIBLE_PUBLIC,
            ),
            errors = mutableListOf(
                MkplError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = MkplState.RUNNING,
        )

        val req = context.toTransport() as OrderCreateResponse

        assertEquals(OrderVisibility.PUBLIC, req.order?.visibility)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
    }
}
