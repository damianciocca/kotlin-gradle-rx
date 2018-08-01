package example14MulticastingHot

import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {

    /**
     * SIMPERE ES MULTICAST... les va a llegar a todos los sobservers.. pero la diferencia es que nos va a limitar
     * a la hora arrancar a escuchar las emisiones.. autoConnect = 1 => arranca de una.. autoConnect = 2 => arranca
     * cuando haya dos observers
     *
    ====> autoConnect()
     */

    val seconds = Observable.interval(1, TimeUnit.SECONDS)
            .publish()
            .autoConnect()

    seconds.take(5).subscribe { println("observer1 $it") }

    Thread.sleep(3000)

    seconds.take(2).subscribe { println("observer2 $it") }

    Thread.sleep(3000)

    seconds.take(2).subscribe { println("observer3 $it") }

    Thread.sleep(3000)

    /**
     * result:
    observer1 0
    observer1 1
    observer1 2
    observer1 3
    observer2 3
    observer1 4
    observer2 4
    observer3 6 => NO VOLVIO A EMPEZAR!
    observer3 7
     */

    println("---------------------")
    println("---------------------")

    /**
    ====> autoConnect(2)
     */

    val otherSeconds = Observable.interval(1, TimeUnit.SECONDS)
            .publish()
            .autoConnect(2)

    otherSeconds.take(5).subscribe { println("observer1 $it") }

    Thread.sleep(3000)

    otherSeconds.take(2).subscribe { println("observer2 $it") }
    // Recien aca deberia empezar a emitir ya que hay dos observers que esta escuchando (autoConnect = 2)

    Thread.sleep(3000)

    otherSeconds.take(2).subscribe { println("observer3 $it") }

    Thread.sleep(3000)

    /**
     * result: (aca vemos q el observer 1 y 2 arrancan al mismo tiempo
    observer1 0
    observer2 0
    observer1 1
    observer2 1
    observer1 2
    observer1 3
    observer3 3 => El observer 3 arranco con el stream ya empezado
    observer1 4
    observer3 4
     */
}
