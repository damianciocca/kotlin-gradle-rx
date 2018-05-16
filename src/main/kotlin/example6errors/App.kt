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

    println("----> RX doOnError is called before exception was thrown.. Ideal to perform any action")

    listOf("Aleberto", "Damian", "Rodri", "Ger").toFlowable()
            .doOnNext {
                if (it == "Rodri") {
                    throw IllegalArgumentException("invalid")
                }
            }
            .doOnError { println("OnError was called before exception thrown") }
            .subscribeBy(
                    onNext = { println(it) },
                    onError = { println("error!!.. Stop") },
                    onComplete = { println("completed!") })

    /**
     * Result:
     * Aleberto
     * Damian
     * OnError was called before exception thrown
     * error!!.. Stop
     */

    println("----> RX onErrorReturn is called before exception was thrown an allow complete it with ONE default value")

    listOf("Aleberto", "Damian", "Rodri", "Ger").toFlowable()
            .doOnNext {
                if (it == "Rodri") {
                    throw IllegalArgumentException("invalid")
                }
            }
            .onErrorReturn { "default value" }
            .subscribeBy(
                    onNext = { println(it) },
                    onError = { println("error!!.. Stop") },
                    onComplete = { println("completed!") })

    /**
     * Result:
     * Aleberto
     * Damian
     * default value
     * completed!
     */

    println("----> RX onErrorResumeNext is called before exception was thrown an allow complete it with a LIST of default values")

    listOf("Aleberto", "Damian", "Rodri", "Ger").toFlowable()
            .doOnNext {
                if (it == "Rodri") {
                    throw IllegalArgumentException("invalid")
                }
            }
            .onErrorResumeNext(listOf("valu1, value2, value3").toFlowable())
            .subscribeBy(
                    onNext = { println(it) },
                    onError = { println("error!!.. Stop") },
                    onComplete = { println("completed!") })

    /**
     * Result:
     *
     * Aleberto
     * Damian
     * valu1, value2, value3
     * completed!
     */
}

