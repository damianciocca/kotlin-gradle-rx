import io.reactivex.rxkotlin.toObservable
import org.slf4j.LoggerFactory

class RxExample {

}

fun main(args: Array<String>) {

    val logger = LoggerFactory.getLogger(RxExample::class.java)

    val listOfStrings = listOf("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
    val listOfNumbers = listOf(1, 2, 3, 4, 5)

    listOfNumbers.map { it * 5 }.filter { it < 25 }.forEach { println(it) }
    /*
    5
    10
    15
    20
     */

    println("-----------------")

    listOfStrings.toObservable()
            .filter { it.length >= 5 }
// alternativa 1
//            .subscribeBy(  // named arguments for lambda Subscribers
//                    onNext = { println(it) },
//                    onError = { it.printStackTrace() },
//                    onComplete = { println("Done!") }
//            )
// alternativa 2
//            .subscribe { t -> logger.info("next: $t") }
// alternativa 3
            .subscribe { logger.info("next: $it") }


}