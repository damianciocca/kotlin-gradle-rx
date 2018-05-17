package example12Operators

import io.reactivex.rxkotlin.toObservable

fun main(args: Array<String>) {

    /**
     * With imperative programming, we have the ability to access the nth element of any array/list, which is quite a common
     * requirement. The elementAt operator is really helpful in this regard; it pulls the nth element
     * from the producer and emits it as its own sole emission.
     */
    val observable = listOf(10, 1, 2, 5, 8, 6, 9)
            .toObservable()

    observable.elementAt(5)//(1)
            .subscribe { println("Received $it") }

    observable.elementAt(50)//(2)
            .subscribe { println("Received $it") }

    /**
     * Result

    Received 6
     */
}