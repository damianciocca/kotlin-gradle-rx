package example1

import io.reactivex.Observable


fun main(args: Array<String>) {

    val fakeDb = FakeDb()

    /**
     * Muy buena documentacion:
     *
     *  http://reactivex.io/intro.html
     *  http://reactivex.io/documentation/observable.html
     */

    println("----> mostramos todos los nombres de los usuarios. EJEMPLO 1")

    fakeDb.getUsers()
            .map { user -> user.value }
            // alternativa 1
            //.subscribe({ name -> println(name)})
            // alternativa 2
            .subscribe({ println(it) }) // como tenemos solo una lambda podemos usar it

    println("----> mostramos los los puntos")

    val usersObservable = fakeDb.getUsers()
    val pointsObservable = usersObservable.flatMap { user -> fakeDb.getPointOfUsers(user.key) }
    pointsObservable.subscribe({ println(it) })

    println("----> mostramos los nombres de los usuarios y los puntos")

    val letters = Observable.just("A", "B", "C", "D", "E", "F")
    val numbers = Observable.just(1, 2, 3, 4, 5)

    Observable.merge(letters, numbers).subscribe({ t -> println(t) })

    // todo ver ZIP & ZIPWITH para unir los dos observable uno a uno.. en cambio el merge te pone uno y despues el otro

}
