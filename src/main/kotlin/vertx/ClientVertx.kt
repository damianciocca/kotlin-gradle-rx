package vertx

import io.vertx.core.Vertx
import io.vertx.core.http.HttpClientOptions
import io.vertx.core.http.HttpMethod


fun main(args: Array<String>) {

    val vertx = Vertx.vertx()

      val options = HttpClientOptions().setLogActivity(true).setDefaultHost("localhost").setDefaultPort(8080)
    val client = vertx.createHttpClient(options)
   // client.getNow("/some-uri", { response -> println("Received response with status code $response.statusCode()") })

    client.request(HttpMethod.GET, "some-uri") { response -> System.out.println("Received response with status code " + response.statusCode()) }.end()

}