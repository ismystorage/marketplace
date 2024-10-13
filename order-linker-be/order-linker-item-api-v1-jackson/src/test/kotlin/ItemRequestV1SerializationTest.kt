import ru.otus.otuskotlin.marketplace.api.v1.models.*
import ru.otus.otuskotlin.marketplace.apiV1ItemMapper
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ItemRequestV1SerializationTest {
    private val request = ItemCreateRequest(
        debug = ItemDebug(
            mode = ItemRequestDebugMode.STUB,
            stub = ItemRequestDebugStubs.BAD_TITLE
        ),
        item = ItemCreateObject(
            length = 1100,
            width = 800,
            height = 1500,
            weight = 300,
            type = ItemType.ORDINARY
        )
    )

    @Test
    fun serialize() {
        val json = apiV1ItemMapper.writeValueAsString(request)

        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badTitle\""))
        assertContains(json, Regex("\"length\":\\s*1100.*"));
        assertContains(json, Regex("\"width\":\\s*800."))
        assertContains(json, Regex("\"height\":\\s*1500."))
        assertContains(json, Regex("\"weight\":\\s*300."))
        assertContains(json, Regex("\"type\":\\s*\"ORDINARY\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1ItemMapper.writeValueAsString(request)
        val obj = apiV1ItemMapper.readValue(json, ItemIRequest::class.java) as ItemCreateRequest

        assertEquals(request, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"item": null}
        """.trimIndent()
        val obj = apiV1ItemMapper.readValue(jsonString, ItemCreateRequest::class.java)

        assertEquals(null, obj.item)
    }
}
