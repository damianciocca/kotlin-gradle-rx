package vertx.clientservertest

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future

/**
 * https://vertx.io/blog/my-first-vert-x-3-application/
 *
 * In the Vert.x world, a verticle is a component. By extending AbstractVerticle, our class gets access to the vertx field.
 */
class MyFirstVertxServer : AbstractVerticle() {

    /**
     * The start method is called when the verticle is deployed
     * One of the particularity of Vert.x is its asynchronous / non-blocking aspect. When our verticle is going to
     * be deployed it won’t wait until the start method has been completed. So, the Future parameter is important to notify
     * of the completion.
     */
    override fun start(startFuture: Future<Void>) {

        vertx.createHttpServer()
                // requestHandler is called every time the server receives a request
                .requestHandler { r -> r.response().end("<h1>Hello from my first " + "Vert.x 3 application</h1>") }
                .listen(8099) { result ->
                    if (result.succeeded()) {
                        startFuture.complete()
                    } else {
                        startFuture.fail(result.cause())
                    }
                }
    }
}