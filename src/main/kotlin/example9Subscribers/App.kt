package example9Subscribers

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

fun main(args: Array<String>) {


    /**
     * In RxKotlin 1.x, the Subscriber operator essentially became an Observer type in RxKotlin 2.x.
     *
     * There is an Observer type in RxKotlin 1.x, but the Subscriber value is what you pass to the subscribe() method,
     * and it implements Observer.
     *
     * In RxJava 2.x, a Subscriber operator only exists when talking about Flowables.
     *
     * an Observer type is an interface with four methods in it, namely
     * onNext(item:T),
     * onError(error:Throwable),
     * onComplete(),
     * onSubscribe(d:Disposable).
     *
     * As stated earlier, when we connect Observable to Observer, it looks for these four methods in the Observer type and calls them
     *
     * The subscribe operator serves the purpose of the media by connecting an Observable interface to Observer.
     */

    println("======== >EJEMPLO 1")

    val observable = Observable.range(1, 5)//1

    observable.subscribe({
        //onNext method
        println("Next-> $it")
    }, {
        //onError Method
        println("Error=> ${it.message}")
    }, {
        //onComplete Method
        println("Done")
    })



    println("======== >EJEMPLO 2")

    val observer: Observer<Int> = object : Observer<Int> {
        //3
        override fun onComplete() {
            println("custom All Completed")
        }

        override fun onNext(item: Int) {
            println("custom Next-> $item")
        }

        override fun onError(e: Throwable) {
            println("custom Error Occurred=> ${e.message}")
        }

        override fun onSubscribe(d: Disposable) {
            println("custom New Subscription ")
        }
    }

    observable.subscribe(observer)

}
