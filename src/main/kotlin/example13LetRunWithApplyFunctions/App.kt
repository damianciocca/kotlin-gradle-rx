package example13LetRunWithApplyFunctions

fun main(args: Array<String>) {

    /**
     * If you decide to write Kotlin code, eventually you will see a lot of usage of the following 4 functions from standard library: run, let, also and apply.
     * So when trying to choose from these 4 functions, think in terms of transformation and mutation, NOT the four function names (the names are terrible in my opinion).
    https://www.lvguowei.me/post/kotlin-run-let-also-apply/

     */

    val student = Student("Bob", 19, "1234")

    // Takes a student and returns its name, Student -> String

    println("RUN-------")
    val name = student.run {
        println(this) // aca se usa THIS
        name
    }
    println(name)

    println("LET-------")
    val name1 = student.let {
        println(it) // ACA SE USA IT
        it.name
    }
    println(name1)

    println("APPLY-------")
    val result = student.apply {
        increaseAge()
        nameToUpperCase()
        println(this.name)
        println(this.age)
    }

    println("ALSO-------")
    val result1 = student.also {
        it.increaseAge()
        it.nameToUpperCase()
        println(it.name)
        println(it.age)
    }


}

class Student(name: String, age: Int, stuNum: String) {

    var name = name
        private set

    var age = age
        private set

    var stuNum = stuNum
        private set

    fun increaseAge() {
        age++
    }

    fun nameToUpperCase() {
        name = name.toUpperCase()
    }

    override fun toString(): String {
        return "($name, $age, $stuNum)"
    }
}