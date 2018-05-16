package example10lamdas

fun main(args: Array<String>) {

    highOrderFunc(12, { a: Int -> true })
    highOrderFunc(19, { a: Int -> false })
}

fun highOrderFunc(a: Int, validityCheckFunc: (a: Int) -> Boolean) {
    if (validityCheckFunc(a)) {//(2)
        println("a $a is Valid")
    } else {
        println("a $a is Invalid")
    }
}