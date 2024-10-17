import kotlinx.datetime.Clock
import org.junit.Test
import ru.otus.otuskotlin.marketplace.api.v1.models.*
import ru.otus.otuskotlin.marketplace.common.MkplOrderContext
import ru.otus.otuskotlin.marketplace.common.models.*
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
                createdAt = Clock.System.now().toString(),
                updatedAt = "",
                orderNumber = "",
                status = OrderStatus.CREATED,
                packages = listOf(PackageType.SMALL, PackageType.SMALL),
                visibility = OrderVisibility.PUBLIC,
            ),
        )
println(req)
        val context = MkplOrderContext()
        context.fromTransport(req)

        assertEquals(MkplStubs.SUCCESS, context.stubCase)
        assertEquals(MkplWorkMode.STUB, context.workMode)


        assertEquals(MkplVisibility.VISIBLE_PUBLIC, context.orderRequest.visibility)

    }

    @Test
    fun toTransport() {
        val context = MkplOrderContext(
            requestId = MkplRequestId("1234"),
            command = MkplCommand.CREATE,
            orderResponse = MkplOrder(
                createdAt = Clock.System.now().toString(),
                updatedAt = "",
                orderNumber = "",
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
