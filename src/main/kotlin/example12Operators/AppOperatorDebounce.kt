package example12Operators

import io.reactivex.Observable
import java.util.concurrent.TimeUnit


fun main(args: Array<String>) {

    /**
     * When developing an application UI/UX, we often come to such a situation. For example, you have created a text input
     * and are willing to perform some operation when the user types something, but you don't want to perform this
     * operation on each keystroke. You would like to wait a little bit for the user to stop typing (so you've got a
     * good query matching what the user actually wants) and then send it to the downstream operator. The debounce operator
     * serves that exact purpose.
     */

    println("EJEMPLO con una espera de 50 milisegundos para imprimir el resultado")
    createObservable()//(1)
            .debounce(50, TimeUnit.MILLISECONDS)//(2)
            .subscribe {
                println(it)//(3)
            }

    /**
    Result:

    R
    Reac
    Reactiv
    Reactive
    Reactive P
    Reactive Pro
    Reactive Progra
    Reactive Programming
    Reactive Programming in
    Reactive Programming in Ko
    Reactive Programming in Kotlin
     */

    println("EJEMPLO con una espera de 200 milisegundos para imprimir el resultado")
    createObservable()//(1)
            .debounce(200, TimeUnit.MILLISECONDS)//(2)
            .subscribe {
                println(it)//(3)
            }

    /**
     * Result: Observer receives only three emits, after which the Observable took at least 200 milliseconds before emitting the next one.

    Reactive
    Reactive Programming
    Reactive Programming in Kotlin
     */

}

/**
 * we tried to simulate user typing behavior by emitting a series of incremental Strings with intervals, until it
 * reached the final version (Reactive Programming in Kotlin). We provided bigger intervals after completing each
 * word depicting an ideal user behavior.
 */
inline fun createObservable(): Observable<String> =
        Observable.create<String> {
            it.onNext("R")//(4)
            run { Thread.sleep(100) }
            it.onNext("Re")
            it.onNext("Reac")
            run { Thread.sleep(130) }
            it.onNext("Reactiv")
            run { Thread.sleep(140) }
            it.onNext("Reactive")
            Thread.sleep(250)
            run { Thread.sleep(250) }
            it.onNext("Reactive P")
            run { Thread.sleep(130) }
            it.onNext("Reactive Pro")
            run { Thread.sleep(100) }
            it.onNext("Reactive Progra")
            run { Thread.sleep(100) }
            it.onNext("Reactive Programming")
            run { Thread.sleep(300) }
            it.onNext("Reactive Programming in")
            run { Thread.sleep(100) }
            it.onNext("Reactive Programming in Ko")
            run { Thread.sleep(150) }
            it.onNext("Reactive Programming in Kotlin")
            run { Thread.sleep(250) }
            it.onComplete()
        }
