import io.socket.client.IO
import org.json.JSONObject
import kotlin.system.exitProcess

class SearchApplication(private val uri : String) {

    private val socket = IO.socket(uri)

    private companion object {
        const val CONNECT = "connect"
        const val DISCONNECT = "disconnect"
        const val ERROR = "error"
        const val CONNECT_ERROR = "connect_error"
        const val SEARCH = "search"
    }

    init {
        socket.on(CONNECT) {
            println("Connected to search server")
            prompt()
        }

        // Note: Socket.io will allow for re-connections, so it's not strictly necessary to exit. If we did want to
        // retry we should log something sensible
        socket.on(DISCONNECT) {
            exit("The connection to the server was disconnected")
        }

        socket.on(CONNECT_ERROR) {
            exit("Could not connect to server $uri")
        }

        socket.on(ERROR) {
            exit("There was an error with the server")
        }

        socket.on(SEARCH) {

            val result = it.singleOrNull()
            if (result != null) {

                try {
                    val searchResult = SearchResult.fromJSONObject(result as JSONObject)
                    receiveResults(searchResult)
                } catch ( e: ClassCastException ) {
                    exit("Error: exiting because result object is not a valid JSON Object (${e.message})")
                }

            } else {
                println("Error: There was a problem with the result set, expected 1 result, got: ${it.size}")
            }
        }

        socket.connect()
    }

    private fun prompt() {

        println("\nWhat Star Wars character would you like to search for?")

        val searchQuery = readln()

        sendSearch(searchQuery)
    }

    private fun sendSearch(searchQuery : String) {
        socket.emit(SEARCH, SearchParameters(searchQuery).toJsonObject())
    }

    private fun receiveResults(searchResult: SearchResult) {

        prettyPrintSearchResult(searchResult)

        if (searchResult.isLastResult()) {
            prompt()
        }
    }

    private fun prettyPrintSearchResult (searchResult : SearchResult) {
        with(searchResult) {
            if (isError()) {
                println("Error: ${error}")
            } else {
                println("($page/$resultCount) $name - [$films]")
            }
        }
    }

    private fun exit(exitMessage : String) {

        println(exitMessage)

        if (socket.connected()) {
            socket.close()
        }

        exitProcess(-1)
    }
}