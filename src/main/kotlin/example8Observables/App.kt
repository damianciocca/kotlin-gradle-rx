package example8Observables

import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable


fun main(args: Array<String>) {

    println("---- Observable JUST ex 1--------------------------------")

    Observable.just(listOf(1, 2, 3)).subscribe { println(it) }

    println("---- Observable JUST ex 2--------------------------------")

    Observable.just(listOf(1, 2, 3), listOf(10, 20, 30), listOf(100, 200, 300), listOf(10000)).subscribe({ println(it) })



    println("---- Observable CREATE ex 1--------------------------------")

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

    println("---- Observable CREATE ex 2--------------------------------")

    Observable.create<String>(
            {
                it.onNext("Emitted 1")
                it.onNext("Emitted 2")
                it.onError(Exception("error!!!!!!"))
            }
    ).subscribe({ t -> println(t) },
            { println("error!!..") },
            { println("completed") })



    println("---- Observable FROM / FROMITERABLE ex 1 --------------------------------")
    /**
     * In RxKotlin 1, you will have Observale.from as a method; however, from RxKotlin 2.0 (as with RxJava2.0),
     * operator overloads have been renamed with a postfix, such as fromArray, fromIterable, and fromFuture.
     */

    // we used the Observable.fromIterable method to create Observable from an Iterable instance (here, list).
    Observable.fromIterable(listOf(1, 2, 3, 4, 5))
            .subscribe{ println(it)}


    println("---- Iteraables + kotlin extension functions--------------------------------")
    /**
     * Thanks to the extension functions of Kotlin, you can turn any Iterable instance, such as list, to Observable
     *
     * So, aren't you curious to look into the toObservable method? Let's do it. You can find this method inside the
     * observable.kt file provided with the RxKotlin package.
     *
     * So, it uses the Observable.from method internally, thanks again to the extension functions of Kotlin.
     */
    listOf(1,2,3).toObservable().subscribe{println(it)}

}

