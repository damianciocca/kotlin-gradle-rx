package example1

fun main(args: Array<String>) {

    val fakeDb = FakeDb()

    println("----> mostramos todos los nombres de los usuarios")

    fakeDb.getUsers()
            .map { user -> user.value }
            // alternativa 1
            //.subscribe({ name -> println(name)})
            // alternativa 2
            .subscribe({ println(it) }) // como tenemos solo una lambda podemos usar it

    println("----> mostramos los nombres de los usuarios con los puntos")

    val usersObservable = fakeDb.getUsers()
    val usersWithPointsObservable = usersObservable.flatMap { user -> fakeDb.getPointOfUsers(user.key) }
    usersWithPointsObservable.subscribe( { println(it)} )


}
