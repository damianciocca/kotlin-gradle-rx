package example12Operators

import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable

fun main(args: Array<String>) {

    /**
     * Where the map operator takes each emission and transforms them, the flatMap operator creates a new producer,
     * applying the function you passed to each emission of the source producer.
     */

    println("----------------- EJEMPLO 1 -------------------------")
    val observable = listOf(10, 9, 8, 7, 6, 5, 4, 3, 2, 1).toObservable()
    observable.flatMap { number ->
        Observable.just("Transforming Int to String $number") // devuelve un Observable dado un item
    }.subscribe { item ->
        println("Received $item")
    }

    /**
     * Result

    Received Transforming Int to String 10
    Received Transforming Int to String 9
    Received Transforming Int to String 8
    Received Transforming Int to String 7
    Received Transforming Int to String 6
    Received Transforming Int to String 5
    Received Transforming Int to String 4
    Received Transforming Int to String 3
    Received Transforming Int to String 2
    Received Transforming Int to String 1

     */

    println("----------------- EJEMPLO 2 -------------------------")
    val observableNumbers = listOf(10, 9, 8, 7, 6, 5, 4, 3, 2, 1).toObservable()
    observableNumbers.flatMap { number ->
        Observable.create<String> {
            // retorna un Observable como Just pero recibe como parametro un ObservableOnSubscribe<T> q necesita la
            // interface ObservableEmitter q extiende de Emitter
            /*
            public interface Emitter<T> {
                 void onNext(T value);
                 void onError(Throwable error);
                 void onComplete();
            }
            */
            it.onNext("The Number $number")
            it.onNext("number/2 ${number / 2}")
            it.onNext("number%2 ${number % 2}")
            it.onComplete()//(2)
        }
    }.subscribeBy(
            onNext = { item ->
                println("Received $item")
            },
            onComplete = {
                println("Complete")
            }
    )

    println("----------------- EJEMPLO 3 -------------------------")
    val source = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
    source
            .flatMap { s -> Observable.fromArray(s.split("")) }
            .subscribe(System.out::println);

    println("----------------- EJEMPLO 4 -------------------------")
    // Our earlier flatMap() examples would be better suited for concatMap() if we explicitly cared about emission order. Although our
    // example here has the same output as the flatMap() example, we should use concatMap() when we explicitly care about maintaining
    // ordering and want to process each mapped Observable sequentially
    val source2 = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
    source2
            .concatMap{ s -> Observable.fromArray(s.split("")) }
            .subscribe(System.out::println);

}