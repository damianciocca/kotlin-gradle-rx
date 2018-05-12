package example3operators

import example2.FakeDb

fun main(args: Array<String>) {

    val fakeDb = FakeDb()

    println("----> ENTENDIENDO FLATMAP")

    fakeDb.getlAllUsers()
            .map { fakeDb.getPointsForUser(it.id) }
            .subscribe({ println(it) })

    /**
     * Result:
     * io.reactivex.internal.operators.single.SingleJust@555590
     * io.reactivex.internal.operators.single.SingleJust@6d1e7682
     * io.reactivex.internal.operators.single.SingleJust@424c0bc4
     */


    fakeDb.getlAllUsers()
            .flatMapSingle { fakeDb.getPointsForUser(it.id) }
            .subscribe({ println(it) })

    /**
     * Result:
     * 10
     * 20
     * 30
     */

    fakeDb.getlAllUsers()
            .flatMapSingle { fakeDb.getPointsForUser(it.id) }
            .filter({ it < 21 })
            .subscribe({ println(it) })

    /**
     * Result:
     * 10
     * 20
     */
}

