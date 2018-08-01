package example14MulticastingHot

import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {

    /**
    ====> replay() => De esta manera los observers q se enganchan tarde recuperar todas las emisiones ya emitidas.
     */

    val seconds = Observable.interval(1, TimeUnit.SECONDS)
            .replay()
            .refCount()

    seconds.take(6).subscribe { println("observer1 $it") }

    Thread.sleep(4000)

    seconds.take(7).subscribe { println("observer2 $it") }

    Thread.sleep(4000)

    /**
     * result:
     *
    observer1 0
    observer1 1
    observer1 2
    observer1 3
    observer2 0 => A partir de los 4 segundos vemos q el observer 2 obtiene todas las emisiones hasta los 4 segundos
    observer2 1 => por replay
    observer2 2 => por replay
    observer2 3 => por replay
    observer1 4
    observer2 4
    observer1 5
    observer2 5
    observer2 6

     */

}
