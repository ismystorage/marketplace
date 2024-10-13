import ru.otus.otuskotlin.marketplace.api.v1.models.*
import ru.otus.otuskotlin.marketplace.apiV1OrderMapper
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class OrderRequestV1SerializationTest {
    private val request = OrderCreateRequest(
        debug = OrderDebug(
            mode = OrderRequestDebugMode.STUB,
            stub = OrderRequestDebugStubs.BAD_TITLE
        ),
        order = OrderCreateObject(
            orderNumber = "R01-002",
            status = OrderStatus.IN_PROGRESS,
            packages = listOf(PackageType.LARGE, PackageType.SMALL),
            visibility = OrderVisibility.PUBLIC,
        )
    )

    @Test
    fun serialize() {
        val json = apiV1OrderMapper.writeValueAsString(request)

        assertContains(json, Regex("\"orderNumber\":\\s*\"R01-002\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badTitle\""))
        assertContains(json, Regex("\"status\":\\s*\"IN_PROGRESS\""))
        assertContains(json, Regex("\"packages\":\\s*\\[\"LARGE\",\"SMALL\"]"))
    }

    @Test
    fun deserialize() {
        val json = apiV1OrderMapper.writeValueAsString(request)
        val obj = apiV1OrderMapper.readValue(json, IRequest::class.java) as OrderCreateRequest

        assertEquals(request, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"order": null}
        """.trimIndent()
        val obj = apiV1OrderMapper.readValue(jsonString, OrderCreateRequest::class.java)

        assertEquals(null, obj.order)
    }
}
