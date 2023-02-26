import org.json.JSONObject

data class SearchParameters(val query: String) {
    fun toJsonObject() : JSONObject {
        return JSONObject(
            mapOf(
                "query" to query
            )
        )
    }
}