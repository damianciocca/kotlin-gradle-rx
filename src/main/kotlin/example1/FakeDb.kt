package example1

import io.reactivex.Observable

class FakeDb {

    fun getUsers(): Observable<Map.Entry<Int, String>> {
        val users = mapOf<Int, String>(1 to "Peter", 2 to "Laura", 3 to "Damian").entries
        // from() operator is used to convert an Iterable, a Future, or an Array into an Observable.
        return Observable.fromIterable(users)
    }

    fun getPointOfUsers(userId: Int): Observable<Int> {
        val points = mapOf<Int, Int>(1 to 10, 2 to 20, 3 to 30)
        return Observable.just(points[userId])
    }
}