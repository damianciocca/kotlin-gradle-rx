package example12Operators

import io.reactivex.rxkotlin.toObservable

fun main(args: Array<String>) {

    val list = listOf<MyItemInherit>(
            MyItemInherit(1),
            MyItemInherit(2),
            MyItemInherit(3),
            MyItemInherit(4),
            MyItemInherit(5),
            MyItemInherit(6),
            MyItemInherit(7),
            MyItemInherit(8),
            MyItemInherit(9),
            MyItemInherit(10)
    )

    list.toObservable()
            .map { it as MyItem }
            .subscribe {
                println(it)
            }

    println("cast")

    list.toObservable()
            .cast(MyItem::class.java)
            .subscribe {
                println(it)
            }
}

open class MyItem(val id: Int) {
    override fun toString(): String {
        return "[MyItem $id]"
    }
}

class MyItemInherit(id: Int) : MyItem(id) {
    override fun toString(): String {
        return "[MyItemInherit $id]"
    }
}
