import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class JSONManager {

    companion object{
        fun JSONToMap(json: String): Map<String, Any> {
            val mapType = object : TypeToken<Map<String, Any>>() {}.type
            return Gson().fromJson(json, mapType)
        }
    }
}