import org.json.JSONException
import org.json.JSONObject

/**
 * An exception that we can through if we cannot parse a JSON object into a SearchResult
 */
class SearchResultException(message:String): Exception(message)

data class SearchResult(
    val page : Int,
    val resultCount: Int,
    val name : String?,
    val films : String?,
    val error: String?
) {

    companion object {
        private const val PAGE = "page"
        private const val RESULT_COUNT = "resultCount"
        private const val NAME = "name"
        private const val FILMS = "films"
        private const val ERROR = "error"

        fun fromJSONObject(obj : JSONObject) : SearchResult {

            return try {
                val page = obj.getInt(PAGE)
                val resultCount = obj.getInt(RESULT_COUNT)

                val isError = (page == -1 && resultCount == -1)

                val name = if(!isError) obj.getString(NAME) else null
                val films = if(!isError) obj.getString(FILMS) else null
                val error = if(isError) obj.getString(ERROR) else null

                SearchResult(
                    page,
                    resultCount,
                    name,
                    films,
                    error
                )
            } catch (e: JSONException) {
                throw SearchResultException(e.message ?: "Could not parse result")
            }
        }
    }

    fun isError() = (page == -1 && resultCount == -1)

    fun isLastResult() = (page == resultCount)
}