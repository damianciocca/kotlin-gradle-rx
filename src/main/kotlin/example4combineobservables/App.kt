package example4combineobservables

import example2Observables.FakeDb
import example2Observables.User
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {

    val fakeDb = FakeDb()

    println("----> COMBINANDO - MERGEANDO OBSERVABLES")
    /**
     * HAY MUCHAS OPCIONES
     * merge
     * join
     * zip
     * startWith
     */

    fakeDb.getlAllUsers()
            .flatMapSingle {
                Single.zip(
                        Single.just(it),
                        fakeDb.getPointsForUser(it.id),
                        BiFunction { user: User, points: Int -> "user $user has $points points" })
            }.subscribe { println(it) }


    println("Ejemplo ZIP.. permite combinar como merge o concat pero dos observables de diferentes tipos")

    val source1 = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
    val source2 = Observable.range(1, 3)

    Observable.zip(source1, source2, BiFunction { a: String, b: Int -> Bla(b, a) })
            .subscribe { it -> println(it) }

    println("Ejemplo COMBINE LAST.. cuando una fuente dispara, se combina con las últimas emisiones de la otra. Combina las emisiones de una y otra" +
            " fuente manteniendo el ultimo valor de la fuente q no emitio")

    val source10 = Observable.interval(300, TimeUnit.MILLISECONDS)
    val source11 = Observable.interval(1, TimeUnit.SECONDS)

    Observable.combineLatest(source10, source11, BiFunction { a: Long, b: Long -> "SOURCE 1 $a SOURCE 2 $b "})
            .subscribe{ it -> println(it)}

    Thread.sleep(4000)

    /**
    SOURCE 1 2 SOURCE 2 0
    SOURCE 1 3 SOURCE 2 0
    SOURCE 1 4 SOURCE 2 0
    SOURCE 1 5 SOURCE 2 0
    SOURCE 1 5 SOURCE 2 1  => aca se cumple el 1er segundo... emite la segunda y se combina con la ultima emision de la primera
    SOURCE 1 6 SOURCE 2 1
    SOURCE 1 7 SOURCE 2 1
    SOURCE 1 8 SOURCE 2 1
    SOURCE 1 9 SOURCE 2 1
    SOURCE 1 9 SOURCE 2 2
    SOURCE 1 10 SOURCE 2 2
    SOURCE 1 11 SOURCE 2 2
    SOURCE 1 12 SOURCE 2 2
    SOURCE 1 12 SOURCE 2 3
     */

    println("Ejemplo WITH LAST FROM.. idema anterior pero elimina las que no son parte de su emision, en este caso las del source 20")

    // Comentar el ejemplo anterior sino se ensicia este ej

    val source20 = Observable.interval(300, TimeUnit.MILLISECONDS)
    val source21 = Observable.interval(1, TimeUnit.SECONDS)

    source21.withLatestFrom(source20, BiFunction { a: Long, b: Long -> "SOURCE 2   $a SOURCE 1   $b "})
            .subscribe{ it -> println(it)}

    // “As you can see here, source21 emits every one second while source20 emits every 300 milliseconds.
    // When you call withLatestFrom() on source21 and pass it source20, it will combine with the latest emission
    // from source20 but it does not care about any previous or subsequent emissions.

    Thread.sleep(4000)

    /**
    SOURCE 2   0 SOURCE 1   2
    SOURCE 2   1 SOURCE 1   5
    SOURCE 2   2 SOURCE 1   8
    SOURCE 2   3 SOURCE 1   12
     */


}


data class Bla(val id: Int, val name: String)
