package example12Operators

import io.reactivex.Observable
import io.reactivex.functions.Consumer

fun main(args: Array<String>) {

    val observable = Observable.range(1, 10)

    observable
            .first(2)
            .subscribe(Consumer { t -> println(t) })

    observable
            .last(2)
            .subscribe(Consumer { t -> println(t) })

    Observable.empty<Int>()
            .first(2)
            .subscribe(Consumer { t -> println(t) })

    /**
     * Result

    1   => obtiene el primero
    10  => obtiene el ultimo
    2   => obtiene el valor por default
     */


}

