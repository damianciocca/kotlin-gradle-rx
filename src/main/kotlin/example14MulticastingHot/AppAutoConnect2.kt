package example14MulticastingHot

import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {

    /**
     * SIMPERE ES MULTICAST... Otro ejemplo
     *
    ====> autoConnect()
     */

    val seconds = Observable.range(1, 10)
            .publish()
            .autoConnect() // es igual a 1

    seconds.take(5).subscribe { println("observer1 $it") }

    seconds.take(2).subscribe { println("observer2 $it") }

    seconds.take(2).subscribe { println("observer3 $it") }

    /**
     * result:
    observer1 1
    observer1 2
    observer1 3
    observer1 4
    observer1 5
     => El observer 2 y 3 quedan descartados ya que con q se conecte uno alcanza y ademas es COLD (finito) por lo cual.. una vez q termina de
    emitir se hace el dispose
     */

    println("---------------------")
    println("---------------------")

    val otherSeconds = Observable.range(1, 10)
            .publish()
            .autoConnect(2)

    otherSeconds.take(5).subscribe { println("observer1 $it") }

    otherSeconds.take(2).subscribe { println("observer2 $it") }

    otherSeconds.take(2).subscribe { println("observer3 $it") }

    /**
     * result:
    observer1 1
    observer2 1
    observer1 2
    observer2 2
    observer1 3
    observer1 4
    observer1 5
    => El observer 3 quedan descartado ya que con q se conecte el uno y dos alcanza
     */


    println("----------------------")
    println("----------------------")
    println("----------------------")
    println("----------------------")

    val stream = Observable.range(1, 10)

    stream.doOnEach{ println(it)}

    stream.publish().connect()




}
