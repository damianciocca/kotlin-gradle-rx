package example11Threads

import com.sun.corba.se.impl.orbutil.ORBUtility.getThreadName


fun main(args: Array<String>) {

    Thread {
        println("-Thread--- start : ${getThreadName(Thread.currentThread())}")
        Thread.sleep(1000)
        println("-Thread--- ended : ${getThreadName(Thread.currentThread())}")
    }.run()

    run {
        println("-Run------ start : ${getThreadName(Thread.currentThread())}")
        Thread.sleep(200)
        println("-Run------ ended : ${getThreadName(Thread.currentThread())}")
    }

    /**
     * Calling Thread.run() for a Thread isn’t do anything other than like a normal run on the same thread
     *
     * Result
     *
    -Thread--- start : main
    -Thread--- ended : main
    -Run------ start : main
    -Run------ ended : main
     */


    println("OTRO EJEMPLO")

    Thread {
        println("-Thread--- start : ${getThreadName(Thread.currentThread())}")
        Thread.sleep(1000)
        println("-Thread--- ended : ${getThreadName(Thread.currentThread())}")
    }.start()

    run {
        println("-Run------ start : ${getThreadName(Thread.currentThread())}")
        Thread.sleep(200)
        println("-Run------ ended : ${getThreadName(Thread.currentThread())}")
    }

    /**
     * Calling Thread.start() for a Thread is actuall starting a new thread, i.e. Thread-0 thread.
     * The code get run asynchronously, as the two threads are run in parallel.
     *
     * Result
     *
    -Run------ start : main
    -Thread--- start : Thread-2
    -Run------ ended : main
    -Thread--- ended : Thread-2
     */

}
