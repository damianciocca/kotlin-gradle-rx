package swithifempty

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Predicate

fun main(args: Array<String>) {


    Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
            .filter(Predicate { s -> s.startsWith("Z") })
            .switchIfEmpty(bla().toObservable())
            .subscribe { println(it) }


}

private fun bla(): Single<String> {
    println("entro....")
    return Single.fromCallable {
        "Zeta"
    }
}