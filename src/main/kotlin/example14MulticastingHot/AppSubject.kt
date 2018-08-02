package example14MulticastingHot

import io.reactivex.subjects.PublishSubject

fun main(args: Array<String>) {

    /**
    ====> subject “ implements both Observable and Observer. ”
     */

    val subject = PublishSubject.create<String>()

    subject.map(String::length)
            .subscribe { println(it) }

    subject.onNext("Alpha")
    subject.onNext("Beta")
    subject.onNext("Gamma")
    subject.onComplete()


    /**
     * result:
     5
     4
     5
     */

    println("--------------------------")

    // “Since Subjects are hot, executing the onNext() calls before the Observers are set up would result in
    // these emissions being missed with our Subject. If you move the onNext() calls like this, you will not get any
    // output because the Observer will miss these emissions:”
    val subject2 = PublishSubject.create<String>()

    subject2.onNext("Alpha")
    subject2.onNext("Beta")
    subject2.onNext("Gamma")
    subject2.onComplete()

    subject2.map(String::length)
            .subscribe { println(it) } // => Suscribimos a un observer despues de la emision de los datos

    /**
     * result:
     * NO HUBO RESULTADO!
     */

    println("--------------------------")

    // “Since Subjects are hot, executing the onNext() calls before the Observers are set up would result in
    // these emissions being missed with our Subject. If you move the onNext() calls like this, you will not get any
    // output because the Observer will miss these emissions:”
    val subject3 = PublishSubject.create<String>()

    subject3.onNext("Alpha")
    subject3.onNext("Beta")
    subject3.onNext("Gamma")

    subject3.map(String::length)
            .subscribe { println(it) } // => Suscribimos a un observer despues de varios datos emitidos pero todavia no se completo

    subject3.onNext("raulcassini")
    subject3.onComplete() // aca emitimos un dato mas y completamos.. Si completamos antes el observer se pierde
    /**
     * result:
     * 11
     */

}
