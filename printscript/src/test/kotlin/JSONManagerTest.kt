import org.example.utils.JSONManager
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class JSONManagerTest {
    @Test
    fun test001getAJsonToMap() {
        val json =
            "{" +
                "\"key1\": \"value1\"," +
                "\"key2\": \"value2\"," +
                "\"key3\": \"value3\"" +
                "}"
        val map = JSONManager.jsonToMap<String>(json)
        assertEquals("value1", map["key1"])
        assertEquals("value2", map["key2"])
        assertEquals("value3", map["key3"])
    }
}
