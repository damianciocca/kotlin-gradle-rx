package example5rxasync

import example2.FakeDb
import io.reactivex.schedulers.Schedulers

fun main(args: Array<String>) {

    val fakeDb = FakeDb()

    /*

    POr default javaRx viene sincronico.. para hacer Asincronico tenemos q usar los metodos
        * SubscribeOn => Emite los items de manera asincronica diciendole en q hilo
        * ObserveOn => Escucha en los nuevos items para aplicarle algun operacion de manera sincronica en un hilo aparte

     */
    println("----> SubscribeOn Case 1 with Schedulers.newThread()")

    fakeDb.getlAllUsers()
            .subscribeOn(Schedulers.newThread())
            .subscribe({ println(it) })

    // Es necesario para ver este ejemplo agregar el thread sleep. Los items se van a ejecutar en un hilo aparte, mientras que la subscripcion
    // se hace en el hilo principal
    Thread.sleep(100) // para evitar esto se tiene q bloquear el subscribe con blockingSubscribe

    println("----> SubscribeOn Case 2")
    fakeDb.getlAllUsers()
            .doOnNext({ t -> println("User => " + t + " " + Thread.currentThread().name) })
            .subscribeOn(Schedulers.newThread()) // Scheduler NEW THREAD type
            .blockingSubscribe({ println("User => " + it + " " + Thread.currentThread().name) })

    println("----> SubscribeOn Case 3 with Schedulers.single()")
    fakeDb.getlAllUsers()
            .doOnNext({ t -> println("User => " + t + " " + Thread.currentThread().name) })
            .subscribeOn(Schedulers.single()) // Scheduler SINGLE type
            .blockingSubscribe({ println("User => " + it + " " + Thread.currentThread().name) })

    /**
     * Result:
     * User => User(id=1, name=Peter) RxNewThreadScheduler-2
     * User => User(id=2, name=Laura) RxNewThreadScheduler-2
     * User => User(id=1, name=Peter) main
     * User => User(id=2, name=Laura) main
     * User => User(id=3, name=Damian) RxNewThreadScheduler-2
     * User => User(id=3, name=Damian) main
     */

    println("----> ObserveOn Case 1")
    fakeDb.getlAllUsers()
            .doOnNext({ t -> println("User => " + t + " ThreadName: " + Thread.currentThread().name) })
            .observeOn(Schedulers.newThread()) // la operacion de map se ejecuta en un thread aparte
            .map { t -> t.name }
            .doOnNext({ t -> println("User mapped => " + t + " ThreadName: " + Thread.currentThread().name) })
            .subscribeOn(Schedulers.newThread()) // los items se crean en un tread aparte
            .blockingSubscribe({ println("User result => " + it + " ThreadName: " + Thread.currentThread().name) })

    /**
     * Result
     *
     * User => User(id=1, name=Peter) ThreadName: RxNewThreadScheduler-3
     * User => User(id=2, name=Laura) ThreadName: RxNewThreadScheduler-3
     * User => User(id=3, name=Damian) ThreadName: RxNewThreadScheduler-3
     * User mapped => Peter ThreadName: RxNewThreadScheduler-4
     * User mapped => Laura ThreadName: RxNewThreadScheduler-4
     * User mapped => Damian ThreadName: RxNewThreadScheduler-4
     * User result => Peter ThreadName: main
     * User result => Laura ThreadName: main
     * User result => Damian ThreadName: main
     */

}

