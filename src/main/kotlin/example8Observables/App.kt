package example8Observables

import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe


fun main(args: Array<String>) {

    println("---- Observable JUST ex 1")

    Observable.just(listOf(1, 2, 3)).subscribe { println(it) }

    println("---- Observable JUST ex 2")

    Observable.just(listOf(1, 2, 3), listOf(10, 20, 30), listOf(100, 200, 300), listOf(10000)).subscribe({ println(it) })

    println("---- Observable CREATE ex 1")

    Observable.create<String>(
            {
                it.onNext("Emitted 1")
                it.onNext("Emitted 2")
                it.onNext("Emitted 3")
                it.onNext("Emitted 4")
                it.onNext("Emitted 5")
                it.onNext("Emitted 6")
                it.onComplete()
            }
    ).subscribe({ t -> println(t) },
            { println("error!!..") },
            { println("completed") })

    println("---- Observable CREATE ex 2")

    Observable.create<String>(
            {
                it.onNext("Emitted 1")
                it.onNext("Emitted 2")
                it.onError(Exception("error!!!!!!"))
            }
    ).subscribe({ t -> println(t) },
            { println("error!!..") },
            { println("completed") })


}

