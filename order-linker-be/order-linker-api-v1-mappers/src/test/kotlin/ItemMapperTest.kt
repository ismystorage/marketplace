import org.junit.Test
import ru.otus.otuskotlin.marketplace.api.v1.models.*
import ru.otus.otuskotlin.marketplace.common.MkplItemContext
import ru.otus.otuskotlin.marketplace.common.MkplOrderContext
import ru.otus.otuskotlin.marketplace.common.models.MkplCommand
import ru.otus.otuskotlin.marketplace.common.models.MkplError
import ru.otus.otuskotlin.marketplace.common.models.MkplState
import ru.otus.otuskotlin.marketplace.common.models.MkplWorkMode
import ru.otus.otuskotlin.marketplace.common.models.item.MkplItem
import ru.otus.otuskotlin.marketplace.common.models.item.MkplItemType
import ru.otus.otuskotlin.marketplace.common.models.order.*
import ru.otus.otuskotlin.marketplace.common.stubs.MkplStubs
import ru.otus.otuskotlin.marketplace.mappers.v1.item.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v1.item.toTransportItem
import ru.otus.otuskotlin.marketplace.mappers.v1.order.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v1.order.toTransport
import kotlin.test.assertEquals

class ItemMapperTest {
    @Test
    fun fromTransport() {
        val req = ItemCreateRequest(
            debug = ItemDebug(
                mode = ItemRequestDebugMode.STUB,
                stub = ItemRequestDebugStubs.SUCCESS,
            ),
            item = ItemCreateObject(
                length = 1200,
                width = 600,
                height = 700,
                weight = 800,
                type = ItemType.ORDINARY,
                orderId = "123",
            ),
        )

        val context = MkplItemContext()
        context.fromTransport(req)

        assertEquals(MkplStubs.SUCCESS, context.stubCase)
        assertEquals(MkplWorkMode.STUB, context.workMode)
        assertEquals(1200, context.itemRequest.length)
        assertEquals(600, context.itemRequest.width)
        assertEquals(700, context.itemRequest.height)
        assertEquals(800, context.itemRequest.weight)
        assertEquals(MkplItemType.ORDINARY, context.itemRequest.type)
        assertEquals("123", context.itemRequest.orderId)
    }

    @Test
    fun toTransport() {
        val context = MkplItemContext(
            requestId = MkplRequestId("1234"),
            command = MkplCommand.CREATE,
            itemResponse = MkplItem(
                length = 1200,
                width = 600,
                height = 700,
                weight = 800,
                type = MkplItemType.ORDINARY,
                orderId = "123",
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

        val req = context.toTransportItem() as ItemCreateResponse

        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
    }
}
