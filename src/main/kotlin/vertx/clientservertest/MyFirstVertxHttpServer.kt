package vertx.clientservertest

import io.vertx.core.Future
import io.vertx.reactivex.core.AbstractVerticle
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.handler.StaticHandler


/**
 * https://vertx.io/blog/my-first-vert-x-3-application/
 *
 * In the Vert.x world, a verticle is a component. By extending AbstractVerticle, our class gets access to the vertx field.
 */
class MyFirstVertxHttpServer : AbstractVerticle() { // IMPORTANTE extender de io.vertx.reactivex.core.AbstractVerticle

    /**
     * The start method is called when the verticle is deployed
     * One of the particularity of Vert.x is its asynchronous / non-blocking aspect. When our verticle is going to
     * be deployed it won’t wait until the start method has been completed. So, the Future parameter is important to notify
     * of the completion.
     */
    override fun start(startFuture: Future<Void>) {

        val port = EnvironmentConfig.getInt("port")

        // This object is responsible for dispatching the HTTP requests to the right handler.
        val router = Router.router(vertx)

        // 1. ROUTE /
        router.route("/").handler({ routingContext ->
            val response = routingContext.response()
            response
                    .putHeader("content-type", "text/html")
                    .end("<h1>Hello from my first Vert.x 3 application</h1>")
        })

        // 2. ROUTE assets/*
        // Serve static resources from the /assets directory
        // It routes requests on “/assets/*” to resources stored in the “assets”
        // ex http://localhost:8098/assets/index.html
        router.route("/assets/*").handler(StaticHandler.create("assets"))

        vertx.createHttpServer()
                // requestHandler is called every time the server receives a request and is dispatch by a router
                .requestHandler { req -> router.accept(req) }
                .listen(port) { result ->
                    if (result.succeeded()) {
                        startFuture.complete()
                    } else {
                        startFuture.fail(result.cause())
                    }
                }
    }
}