package example6errors

import example2.FakeDb
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toFlowable

fun main(args: Array<String>) {

    val fakeDb = FakeDb()

    /*
       catch expcetion and continue with the next item on the stream
     */
    println("----> Error handling with streams")

    listOf("Aleberto", "Damian", "Rodri", "Ger").stream()
            .forEach {
                try {
                    if (it == "Rodri") {
                        throw IllegalArgumentException("invalid")
                    } else {
                        println(it)
                    }
                } catch (e: IllegalArgumentException) {
                    println("Error!!!!!!!!!!!!!!!. But dont stop")
                }
            }
    /**
     * Result
     * Aleberto
     * Damian
     * Error!!!!!!!!!!!!!!!. But dont stop
     * Ger
     */

    println("----> Error handling with RX case 1")

    listOf("Aleberto", "Damian", "Rodri", "Ger").toFlowable()
            .doOnNext {
                if (it == "Rodri") {
                    throw IllegalArgumentException("invalid")
                }
            }
            .subscribeBy(
                    onNext = { println(it) },
                    onError = { println("error!!.. Stop") },
                    onComplete = { println("completed!") })

    /**
     * Result:
     *
     * Aleberto
     * Damian
     * error!!.. Stop
     */

    println("----> RX onComplete without errors")

    listOf("Aleberto", "Damian", "Rodri", "Ger").toFlowable()
            .subscribeBy(
                    onNext = { println(it) },
                    onError = { println("error!!.. Stop") },
                    onComplete = { println("completed!") })
    /**
     * Result:
     *
     * Aleberto
     * Damian
     * Rodri
     * Ger
     * completed!
     */

}

