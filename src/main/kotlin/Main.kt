fun main(args: Array<String>) {

    val uri = if (args.isNotEmpty()) {
        args[0]
    } else {
        "http://localhost:3000"
    }

    // start the app
    SearchApplication(uri)
}