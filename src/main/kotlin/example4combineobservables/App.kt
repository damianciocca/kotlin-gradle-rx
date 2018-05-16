package example4combineobservables

import example2Observables.FakeDb
import example2Observables.User
import io.reactivex.Single
import io.reactivex.functions.BiFunction

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


}
