import ru.otus.otuskotlin.marketplace.api.v1.models.*
import ru.otus.otuskotlin.marketplace.apiV1OrderMapper
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class OrderResponseV1SerializationTest {
    private val response = OrderCreateResponse(
        order = OrderResponseObject(
            orderNumber = "R01-002",
            status = OrderStatus.COMPLETED,
            packages = listOf(PackageType.LARGE, PackageType.SMALL),
            visibility = OrderVisibility.PUBLIC,
        )
    )

    @Test
    fun serialize() {
        val json = apiV1OrderMapper.writeValueAsString(response)
        assertContains(json, Regex("\"orderNumber\":\\s*\"R01-002\""))
        assertContains(json, Regex("\"status\":\\s*\"COMPLETED\""))
        assertContains(json, Regex("\"packages\":\\s*\\[\"LARGE\",\"SMALL\"]"))
    }

    @Test
    fun deserialize() {
        val json = apiV1OrderMapper.writeValueAsString(response)
        val obj = apiV1OrderMapper.readValue(json, IResponse::class.java) as OrderCreateResponse

        assertEquals(response, obj)
    }
}
