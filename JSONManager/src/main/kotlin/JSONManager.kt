import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * This class is responsible for converting JSON strings to Map objects in the application.
 * It provides a static method jsonToMap which takes a JSON string as input and returns a Map object.
 *
 * The jsonToMap function uses the Gson library to parse the JSON string. It creates a TypeToken object to specify the type of the Map,
 * and then calls the fromJson function of the Gson object to convert the JSON string to a Map.
 *
 * The returned Map has String keys and Any values. This means that the JSON string can contain any valid JSON values, including numbers, strings, booleans, null, arrays, and nested objects.
 *
 * @throws com.google.gson.JsonSyntaxException If the JSON string is not a valid JSON object.
 */
class JSONManager {
    companion object {
        fun jsonToMap(json: String): Map<String, Any> {
            val mapType = object : TypeToken<Map<String, Any>>() {}.type
            return Gson().fromJson(json, mapType)
        }
    }
}
