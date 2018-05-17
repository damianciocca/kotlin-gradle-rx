package example12Operators

import io.reactivex.Observable

fun main(args: Array<String>) {

    Observable.range(1, 20)//(1)
            .filter {
                it % 2 == 0
            }
            .subscribe {
                println("Received $it")
            }

    /**
     * Result
    Received 2
    Received 4
    Received 6
    Received 8
    Received 10
    Received 12
    Received 14
    Received 16
    Received 18
    Received 20
     */
}