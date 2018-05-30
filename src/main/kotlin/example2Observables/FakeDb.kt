package example2Observables

import io.reactivex.*

class FakeDb : Db {

    val users = mutableMapOf(1 to User(1, "Peter"), 2 to User(2, "Laura"), 3 to User(3, "Damian"))
    val points = mutableMapOf<Int, Int>(1 to 10, 2 to 20, 3 to 30)

    override fun getlAllUsersV2(): Observable<User> = Observable.fromIterable(users.values)

    override fun getlAllUsers(): Flowable<User> = Flowable.fromIterable(users.values)

    override fun getUserById(id: Int): Maybe<User> {
        return getlAllUsers().filter { it.id == id }.firstElement()
    }

    fun getUserById2(id: Int): Maybe<User> {
        return Maybe.create({ emitter ->
            if(id == 10) {
                val user = User(10, "name")
                emitter.onSuccess(user)
            } else {
                emitter.onComplete()
            }
        })
    }


    override fun getPointsForUser(id: Int): Single<Int> {
        return if (userIsNotFound(id))
            Single.error { UserNotFound("id $id not found") }
        else
            Single.just(points[id])
    }

    override fun addUser(user: User): Completable = Completable.fromAction { users[user.id] == user }

    private fun userIsNotFound(id: Int) = !users.containsKey(id)

}

interface Db {

    // Rx Types...5 => Flowable, Observable, Maybe, Single, Completable

    fun getlAllUsersV2(): Observable<User>
    fun getlAllUsers(): Flowable<User>
    fun getUserById(id: Int): Maybe<User>
    fun getPointsForUser(id: Int): Single<Int>
    fun addUser(user: User): Completable
}

data class User(val id: Int, val name: String)

class UserNotFound(message: String) : Exception(message)