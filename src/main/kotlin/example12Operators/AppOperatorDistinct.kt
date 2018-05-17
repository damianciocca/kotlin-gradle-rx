package example12Operators

import io.reactivex.rxkotlin.toObservable

fun main(args: Array<String>) {


    /**
     * This operator is quite simple; it helps you filter duplicate emissions from the upstream
     */
    println("Operator distinct")
    listOf(1, 2, 2, 3, 4, 5, 5, 5, 6, 7, 8, 9, 3, 10)//(1)
            .toObservable()//(2)
            .distinct()//(3)
            .subscribe { println("Received $it") }//(4)

    /**
     * Result
     *
    Received 1
    Received 2
    Received 3
    Received 4
    Received 5
    Received 6
    Received 7
    Received 8
    Received 9
    Received 10
     */

    /**
     * The distinctUntilChange operator is slightly different. Instead of discarding all duplicate emissions, it discards only consecutive duplicate emissions,
     */
    println("Operator distinctUntilChanged")
    listOf(1, 2, 2, 3, 4, 5, 5, 5, 6, 7, 8, 9, 3, 10)//(1)
            .toObservable()//(2)
            .distinctUntilChanged()//(3)
            .subscribe { println("Received $it") }//(4)

    /**
     * Result
     *
    Received 1
    Received 2
    Received 3
    Received 4
    Received 5
    Received 6
    Received 7
    Received 8
    Received 9
    Received 3 ( vemos q el 3 no lo descarto porque no la repeticion no es consecutiva )
    Received 10
     */
}
