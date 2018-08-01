package example14MulticastingHot

import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {

    /**
    ====> refCount() and share()
     */

    val seconds = Observable.interval(1, TimeUnit.SECONDS)
            .publish()
            .refCount() // se puede usar en lugar de publish().refCount() el share()

    //val seconds = Observable.interval(1, TimeUnit.SECONDS)
    //        .share()

    seconds.take(5).subscribe { println("observer1 $it") }

    Thread.sleep(3000)

    seconds.take(2).subscribe { println("observer2 $it") }

    Thread.sleep(3000)

    //there should be no more Observers at this point
    // “Note how Observer 3 has started over with a fresh set of intervals starting at 0! ”
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
    observer3 0 => volvio a empezar
    observer3 1
     */

}
