package vertx.apirest

import io.vertx.core.Future
import io.vertx.core.json.Json
import io.vertx.reactivex.core.AbstractVerticle
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.RoutingContext
import java.util.*


/**
 * https://vertx.io/blog/my-first-vert-x-3-application/
 *
 * In the Vert.x world, a verticle is a component. By extending AbstractVerticle, our class gets access to the vertx field.
 */
class ApiHttpServer : AbstractVerticle() { // IMPORTANTE extender de io.vertx.reactivex.core.AbstractVerticle


    private val products = LinkedHashMap<Int, Whisky>()

    override fun start(startFuture: Future<Void>) {

        createSomeData()

        val router = Router.router(vertx)

        router.get("/api/whiskies").handler(this::getAll);

        vertx.createHttpServer()
                .requestHandler { req -> router.accept(req) }
                .listen(8098) { result ->
                    if (result.succeeded()) {
                        startFuture.complete()
                    } else {
                        startFuture.fail(result.cause())
                    }
                }

    }

    private fun createSomeData() {
        val bowmore = Whisky("Bowmore 15 Years Laimrig", "Scotland, Islay")
        products[bowmore.id] = bowmore

        val talisker = Whisky("Talisker 57° North", "Scotland, Island")
        products[talisker.id] = talisker
    }

    private fun getAll(routingContext: RoutingContext) {
        // Write the HTTP response
        // The response is in JSON using the utf-8 encoding
        // We returns the list of bottles
        routingContext.response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(products.values))
    }

}