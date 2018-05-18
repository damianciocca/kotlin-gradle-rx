package vertx.apirest

import io.vertx.core.DeploymentOptions
import io.vertx.core.Vertx
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.ext.unit.TestContext
import io.vertx.ext.unit.junit.VertxUnitRunner
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.net.ServerSocket


@RunWith(VertxUnitRunner::class)
class ApiRestServerIntegrationTest {

    private var vertx: Vertx? = null
    private var port: Int = 1

    @Before
    fun setUp(context: TestContext) { // @Before method, it receives a TestContext. This object lets us control the asynchronous aspect of our test
        vertx = Vertx.vertx()

        // nos da un puerto distinto dinamicamente
        val socket = ServerSocket(0)
        port = socket.localPort
        socket.close()

        // otra forma de poder pasar parametros de configuracion por json
        val options = DeploymentOptions().setConfig(JsonObject().put("http.port", port))
        // the not-null assertion operator (!!) converts any value to a non-null type and throws an exception if the value is null
        vertx!!.deployVerticle(ApiHttpServer::class.java.name, options, context.asyncAssertSuccess())
    }

    @After
    fun tearDown(context: TestContext) {
        vertx!!.close(context.asyncAssertSuccess())
    }

    @Test
    fun checkThatWeCanAdd(context: TestContext) {
        val async = context.async()
        val json = Json.encodePrettily(Whisky(100, "Jameson", "Ireland"))
        val length = Integer.toString(json.length)
        vertx!!.createHttpClient().post(port, "localhost", "/api/whiskies")
                .putHeader("content-type", "application/json")
                .putHeader("content-length", length)
                .handler { response ->
                    context.assertEquals(response.statusCode(), 201)
                    context.assertTrue(response.headers().get("content-type").contains("application/json"))
                    response.bodyHandler { body ->
                        val (id, name, origin) = Json.decodeValue(body.toString(), Whisky::class.java)
                        context.assertEquals(name, "Jameson")
                        context.assertEquals(origin, "Ireland")
                        context.assertNotNull(id)
                        context.assertEquals(id, 100)
                        async.complete()
                    }
                }
                .write(json)
                .end()
    }
}