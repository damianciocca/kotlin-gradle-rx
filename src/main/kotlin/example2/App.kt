package example2

import io.reactivex.functions.Consumer

fun main(args: Array<String>) {

    val fakeDb = FakeDb()

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

