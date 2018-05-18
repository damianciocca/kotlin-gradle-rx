package vertx.apirest

import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.vertx.core.Future
import io.vertx.core.json.Json
import io.vertx.reactivex.core.AbstractVerticle
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.RoutingContext
import io.vertx.reactivex.ext.web.handler.BodyHandler
import java.util.*


/**
 * https://vertx.io/blog/my-first-vert-x-3-application/
 *
 * In the Vert.x world, a verticle is a component. By extending AbstractVerticle, our class gets access to the vertx field.
 */
class ApiHttpServer : AbstractVerticle() { // IMPORTANTE extender de io.vertx.reactivex.core.AbstractVerticle


    private val products = LinkedHashMap<Int, Whisky>()

    override fun start(startFuture: Future<Void>) {

        Json.mapper.registerModule(KotlinModule()) // IMPORTANTE para poder serializar de JSON a Kotlin Obj

        createSomeData()

        val router = Router.router(vertx)

        /**
         * GET /api/whiskies => get all bottles (getAll)
         * GET /api/whiskies/:id => get the bottle with the corresponding id (getOne)
         * POST /api/whiskies => add a new bottle (addOne)
         * PUT /api/whiskies/:id => update a bottle (updateOne)
         * DELETE /api/whiskies/id => delete a bottle (deleteOne)
         */
        router.get("/api/whiskies").handler(this::getAll);
        // enables the reading of the request body for all routes under “/api/whiskies”. We could have enabled it
        // globally with router.route().handler(BodyHandler.create()).
        router.route("/api/whiskies*").handler(BodyHandler.create());
        router.post("/api/whiskies").handler(this::addOne);
        // In the URL, we define a path parameter :id. So, when handling a matching request, Vert.x extracts the path segment corresponding to the
        // parameter and let us access it in the handler method. For instance, /api/whiskies/0 maps id to 0.
        router.delete("/api/whiskies/:id").handler(this::deleteOne);

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
        val bowmore = Whisky(1, "Bowmore 15 Years Laimrig", "Scotland, Islay")
        products[bowmore.id] = bowmore

        val talisker = Whisky(2, "Talisker 57° North", "Scotland, Island")
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

    private fun addOne(routingContext: RoutingContext) {
        val bodyAsJson = routingContext.bodyAsString
        println(bodyAsJson)
        val whisky = Json.decodeValue(bodyAsJson, Whisky::class.java)
        products[whisky.id] = whisky
        routingContext.response()
                .setStatusCode(201)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(whisky))
    }

    private fun deleteOne(routingContext: RoutingContext) {
        val id = routingContext.request().getParam("id")
        if (id == null) {
            routingContext.response().setStatusCode(400).end()
        } else {
            val idAsInteger = Integer.valueOf(id)
            products.remove(idAsInteger)
        }
        routingContext.response().setStatusCode(204).end()
    }


}