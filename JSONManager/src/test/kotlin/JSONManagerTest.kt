import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class JSONManagerTest {
    @Test
    fun jsonToMapTest() {
        val json =
            """
            {
                "name": "John",
                "age": 30,
                "cars": {
                    "car1": "Ford",
                    "car2": "BMW",
                    "car3": "Fiat"
                }
            }
            """.trimIndent()
        val map: Map<String, Any> = JSONManager.jsonToMap(json)
        val hashMap: HashMap<String, Any> = HashMap(map)
        assertEquals("John", hashMap["name"])
        assertEquals(30.0, hashMap["age"])
        assertEquals("Ford", (hashMap["cars"] as Map<*, *>)["car1"])
        assertEquals("BMW", (hashMap["cars"] as Map<*, *>)["car2"])
        assertEquals("Fiat", (hashMap["cars"] as Map<*, *>)["car3"])
    }
}
