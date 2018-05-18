package vertx.apirest

import io.vertx.core.Vertx
import io.vertx.ext.unit.TestContext
import io.vertx.ext.unit.junit.VertxUnitRunner
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(VertxUnitRunner::class)
class ApiRestServerRunner {

    private var vertx: Vertx? = null

    @Before
    fun setUp(context: TestContext) { // @Before method, it receives a TestContext. This object lets us control the asynchronous aspect of our test
        vertx = Vertx.vertx()
        // the not-null assertion operator (!!) converts any value to a non-null type and throws an exception if the value is null
        vertx!!.deployVerticle(ApiHttpServer::class.java.name, context.asyncAssertSuccess())
    }

    @After
    fun tearDown(context: TestContext) {
        vertx!!.close(context.asyncAssertSuccess())
    }

    @Test
    fun runHttpServer(context: TestContext) {
        val async = context.async()
    }
}