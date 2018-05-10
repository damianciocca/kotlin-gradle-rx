import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import org.slf4j.LoggerFactory

class RxExample {

}

class NumberData(name: String)


fun main(args: Array<String>) {

    val logger = LoggerFactory.getLogger(RxExample::class.java)

    val listOfStrings = listOf("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
    val listOfNumbers = listOf(1, 2, 3, 4, 5)

    println("----------------- SIMPLE EXAMPLE ")

    listOfNumbers.map { it * 5 }.filter { it < 25 }.forEach { println(it) }
    /*
    5
    10
    15
    20
     */

    println("----------------- RX ")

    println("----------------- 1. Observable + susbcribe ")

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


    println("----------------- 1. flat map")
// alternativa 1
    val customer = Customer("customer", 12)
    val customerObservable: Observable<Customer> = Observable.just(customer)
    val addressObservable: Observable<Address> = customerObservable.flatMap { person -> person.getAddresses() }
    addressObservable.subscribe({ address -> logger.info("next: $address") })
// alternativa 2
    Observable.just(customer).flatMap { person -> person.getAddresses() }.subscribe({ address -> logger.info("$address") })


}

data class Address(val s: String, val s1: String)

data class Customer(val s: String, val i: Int) {

    fun getAddresses(): Observable<Address> {
        return Observable.create<Address> { ob ->
            ob.onNext(Address("aaa", "bbb"))
            ob.onNext(Address("bbb", "ccc"))
            ob.onComplete()
        }
    }
}
