package example8Observables

import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit


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
            .subscribe { println(it) }


    println("---- Iteraables + kotlin extension functions--------------------------------")
    /**
     * Thanks to the extension functions of Kotlin, you can turn any Iterable instance, such as list, to Observable
     *
     * So, aren't you curious to look into the toObservable method? Let's do it. You can find this method inside the
     * observable.kt file provided with the RxKotlin package.
     *
     * So, it uses the Observable.from method internally, thanks again to the extension functions of Kotlin.
     */
    listOf(1, 2, 3).toObservable().subscribe { println(it) }



    println("---- Cold Observables  --------------------------------")

    /**
     * Observables, emitting items from the beginning for each subscription, are called Cold Observable.
     * To be more specific, Cold Observables start running upon subscriptions and Cold Observable starts pushing
     * items after subscribe gets called, and pushes the same sequence of items on each subscription.
     */

    val observable: Observable<String> = listOf("String 1", "String 2", "String 3", "String 4").toObservable()

    observable.subscribe({
        println("Received $it")
    }, {
        println("Error ${it.message}")
    }, {
        println("Done")
    })

    observable.subscribe({
        //3
        println("Received $it")
    }, {
        println("Error ${it.message}")
    }, {
        println("Done")
    })

    /**
     * Result:
     *
    Received String 1
    Received String 2
    Received String 3
    Received String 4
    Done
    Received String 1
    Received String 2
    Received String 3
    Received String 4
    Done
     */


    println("---- HOT Observables  --------------------------------")

    val connectableObservable = listOf("String 1", "String 2", "String 3", "String 4", "String5").toObservable()
            .publish()
    // which is a variety of ObservableSource that waits until its method is called before it begins emitting
    // items to those that have subscribed to it.

    connectableObservable.subscribe({ println("Subscription 1 $it") })
    connectableObservable.subscribe({ println("Subscription 2 $it") })
    connectableObservable.connect() // we called connect method, and emissions got started to both Observers.

    connectableObservable.subscribe({ println("Subscription 3: $it") }) //6 //Will not receive emissions

    // si movemos el connect debajo de la 3er subscription, la 3era va a recibir items tmb

    /**
     * Result
     *
     *
    Subscription 1: String 1
    Subscription 2 String 1
    Subscription 1: String 2
    Subscription 2 String 2
    Subscription 1: String 3
    Subscription 2 String 3
    Subscription 1: String 4
    Subscription 2 String 4
    Subscription 1: String5
    Subscription 2 String5
     */

    // note that Subscription 3 will not emit anything


    println("---- Observable Interval with Subjects  --------------------------------")

    // There are many types of Subject available. PublishSubject is one of them.
    // PublishSubject emits to an observer only those items that are emitted by the Observable sources subsequent
    // to the time of the subscription
    val subject = PublishSubject.create<Long>()//2
    Observable.interval(1, TimeUnit.SECONDS).subscribe(subject)// emits a sequential number every specified interval of time.
    subject.subscribe({ println("Received $it") })
    Thread.sleep(4000) //

    // Es lo mismo que el ejemplo de arriba pero sin usar el Subject...
    //Observable.interval(1, TimeUnit.SECONDS).subscribe{println(it)}// emits a sequential number every specified interval of time.
    //Thread.sleep(4000) //

}

