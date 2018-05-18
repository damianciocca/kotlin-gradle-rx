package vertx

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServerOptions


fun main(args: Array<String>) {

    val vertx = Vertx.vertx()
    vertx.deployVerticle(MainVerticle())

    // val options = HttpClientOptions().setLogActivity(true).setDefaultPort(8080)
    // val client = vertx.createHttpClient(options)
    // client.getNow("/some-uri") { response -> println("Received response with status code $response.statusCode()") }

}

class MainVerticle : AbstractVerticle() {


    override fun start(startFuture: Future<Void>) {

        val options = HttpServerOptions().setLogActivity(true)
        val server = vertx.createHttpServer(options)

        // To be notified when a request arrives you need to set a requestHandler
        // When a request arrives, the request handler is called passing in an instance of HttpServerRequest. This object represents the server
        // side HTTP request.
        // server.requestHandler { request -> request.response().write("Hello world").end()}

        //server.requestHandler { println("incoming request!") }

        server.requestHandler({ request ->
            println("incoming request!")
            println(request.uri())
            println(request.path())
            println(request.getParam("p1"))
        })

        //To tell the server to listen for incoming requests you use one of the listen
        server.listen(8080, "localhost", { res ->
            if (res.succeeded()) {
                println("Server is now listening!")
                startFuture.complete()
            } else {
                println("Failed to bind!")
                startFuture.fail(res.cause())
            }
        })
    }
}