import ru.otus.otuskotlin.marketplace.api.v1.models.*
import ru.otus.otuskotlin.marketplace.apiV1ItemMapper
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ItemResponseV1SerializationTest {
    private val response = ItemCreateResponse(
        item = ItemResponseObject(
            length = 1100,
            width = 800,
            height = 1500,
            weight = 300,
            type = ItemType.ORDINARY
        )
    )

    @Test
    fun serialize() {
        val json = apiV1ItemMapper.writeValueAsString(response)
        assertContains(json, Regex("\"length\":\\s*1100.*"));
        assertContains(json, Regex("\"width\":\\s*800."))
        assertContains(json, Regex("\"height\":\\s*1500."))
        assertContains(json, Regex("\"weight\":\\s*300."))
        assertContains(json, Regex("\"type\":\\s*\"ORDINARY\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1ItemMapper.writeValueAsString(response)
        val obj = apiV1ItemMapper.readValue(json, IResponse::class.java) as ItemCreateResponse

        assertEquals(response, obj)
    }
}
