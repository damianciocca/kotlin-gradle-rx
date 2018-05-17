package example12Operators

import io.reactivex.Observable

fun main(args: Array<String>) {

    /**
     * Sometimes, you may require to listen only on the onComplete of a producer. The ignoreElements operator helps you to do that
     */
    val observable = Observable.range(1, 10)
    observable
            .ignoreElements()
            .subscribe { println("completed") }// Solo permite recibir la accion de Complete
                        // public final Disposable subscribe(final Action onComplete)
}
