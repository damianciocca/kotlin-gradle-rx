package vertx.apirest

import java.util.concurrent.atomic.AtomicInteger

class Whisky(val name: String, val origin: String) {

    private val COUNTER = AtomicInteger()

    var id: Int = 0

    init {
        id = COUNTER.incrementAndGet()
    }

}