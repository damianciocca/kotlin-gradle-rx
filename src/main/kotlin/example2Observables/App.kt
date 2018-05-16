package example2Observables

import io.reactivex.functions.Consumer

fun main(args: Array<String>) {

    val fakeDb = FakeDb()

    /**
     * OBSERVABLE
     *  When you are dealing with a smaller amount of data (less than 10,000 emissions)
     *  When you are performing strictly synchronous operations or operations with limited concurrency
     *  When you are emitting UI events (while working with Android, JavaFX, or Swing)
     *
     * FLOWABLE
     *  if api return more than one item or error
     *
     *  Flowables and backpressure are meant to help deal with larger amounts of data. So, use flowable if your
     *  source may emit 10,000+ items. Especially when the source is asynchronous so that the consumer chain may ask
     *  the producer to limit/regulate emissions when required.
     *
     *  If you are reading from/parsing a file or database.
     *
     * SINGLE
     *  if api return only one item or error
     *
     * MAYBE
     *  if api return only one item, no item or error
     *
     * COMPLETABLE
     *  if api would not return any item. Ex Save user in DB will return some value
     */

    println("----> mostramos todos los nombres de los usuarios con FLOWABLE")
    fakeDb.getlAllUsers().subscribe(Consumer { t -> println(t) })

    println("----> mostramos todos los nombres de los usuarios con OBSERVABLE")
    fakeDb.getlAllUsers().subscribe(Consumer { t -> println(t) })

    println("----> mostramos un usuario con MAYBE")
    fakeDb.getUserById(1).subscribe(Consumer { t -> println(t) })

    println("----> no mostramos nada con MAYBE")
    fakeDb.getUserById(100).subscribe(Consumer { t -> println(t) })

    println("----> mostramos el usuario 1 con SINGLE")
    fakeDb.getPointsForUser(1).subscribe(Consumer { t -> println(t) })

    println("----> lanzamos error cuando no encontramos el punto para el usuario 1 con SINGLE")
    fakeDb.getPointsForUser(100).subscribe(Consumer { t -> println(t) })

    println("----> agregamos usuarios con COMPLETABLE")
    val user = User(1, "name")
    fakeDb.addUser(user).subscribe({ println("User added $user") })
}

