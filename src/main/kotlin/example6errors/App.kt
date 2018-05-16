package example6errors

import example2.FakeDb
import io.reactivex.Flowable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toFlowable
import java.util.concurrent.TimeUnit

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

    println("----> RX map carl with an an empty list (in other words, drop the value carl) and complete")

    listOf("Alberto", "Damian", "Rodri", "Ger").toFlowable()
            .flatMap {
                if (it == "Rodri") {
                    Flowable.empty()
                } else {
                    Flowable.just(it)
                }
            }
            .onErrorResumeNext(listOf("value1, value2, value3").toFlowable())
            .subscribeBy(
                    onNext = { println(it) },
                    onError = { println("error!!.. Stop") },
                    onComplete = { println("completed!") })

    /**
     * Result
     *
     * Alberto
     * Damian
     * Ger
     * completed!
     */

    println("----> RX throw exception when items to be emitted has a long delay => The delay is > to timeout")

    listOf("Alberto", "Damian", "Rodri", "Ger").toFlowable()
            .delay(10, TimeUnit.SECONDS)
            .flatMap {
                if (it == "Rodri") {
                    Flowable.empty()
                } else {
                    Flowable.just(it)
                }
            }
            .timeout(1, TimeUnit.SECONDS)
            .blockingSubscribe(
                    { t -> println(t) },
                    { println("error!!.. Stop.. The delay to emits items is to long") },
                    { println("completed") })

    /**
     * Result
     *
     * error!!.. Stop.. The delay to emits items is to long
     */

    println("----> RX items to be emitted has a delay and timeout => The delay is < to timeout")

    listOf("Alberto", "Damian", "Rodri", "Ger").toFlowable()
            .delay(2, TimeUnit.SECONDS)
            .flatMap {
                if (it == "Rodri") {
                    Flowable.empty()
                } else {
                    Flowable.just(it)
                }
            }
            .timeout(4, TimeUnit.SECONDS)
            .blockingSubscribe(
                    { t -> println(t) },
                    { println("error!!.. Stop.. The delay to emits items is to long") },
                    { println("completed") })

    /**
     * Result
     *
     * Alberto
     * Damian
     * Ger
     * completed
     */

}

