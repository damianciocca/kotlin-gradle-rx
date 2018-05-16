package example8Observables

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit


fun main(args: Array<String>) {

    println("---- Observable Interval with Subjects + Disposables --------------------------------")

    // Subjects:
    // Basically, it is a combination of Observable and Observer, as it has many common behaviors to both Observables
    // and Observers. Like Hot Observables, it maintains an internal Observer list and relays a single push
    // to every Observer subscribed to it at the time of emission
    val subject = PublishSubject.create<Long>()
    val observable = Observable.interval(1, TimeUnit.SECONDS)
    observable.subscribe(subject)

    val disposable = subject.subscribe({ println("Received $it") })

    Thread.sleep(2000) // en este lapso de 2 segundos comienza a emitir...
    //disposable.dispose() // luego se hace unsubscribe
    Thread.sleep(4000) // en este laspo vemos q ya no emite mas

    /**
     * Result
     *
     * Received 0
     * Received 1
     */

    // En caso que comentemos esta linea disposable.dispose() va a continuar recibiendo.. hasta el numero 5
    // Received 0
    // Received 1
    // Received 2
    // Received 3
    // Received 4
    // Received 5

}

