package com.kborid.kotlin.classT

class Empty1

class Empty2 {}

class Empty3(name: String) {}

class Empty4 constructor(name: String) {}

class Empty5 constructor(name: String) {
    init {
        println("empty init")
    }

    val prop1 = "prop1 property: $name".also(::println)

    init {
        println("prop1 initializer block that prints ${name}")
    }

    val prop2 = "prop2 property: ${name.length}".also(::println)

    init {
        println("prop2 initializer block that prints ${name.length}")
    }
}


// ============== demo ==============


// 🐖🐖🐖🐖🐖🐖🐖🐖
// 🐖 主构造函数 🐖
// 🐖🐖🐖🐖🐖🐖🐖🐖
open class Animal constructor(name: String, age: Int) {
//class Person(name: String, age: Int) {

    var name: String = ""

    init {
        println("初始化代码块执行，$name, $age")
    }

    // 次构造函数1
    constructor(name: String) : this(name, age = 10) {
        println("次构造函数1调用，$name")
    }

    // 次构造函数2
    constructor(name: String, add: String) : this(name, age = 10) {
        println("次构造函数2调用，姓名：$name, 地址：$add")
    }
}

class Person : Animal {

    constructor(name: String) : super(name, age = 10) {
        println("派生类次构造函数1调用,$name")
    }

    constructor(name: String, age: Int) : super(name, age) {
        println("派生类次构造函数2调用,$name, $age")
        this.name = name + age
    }
}

fun main() {
//    InitOrderDemo("李四")
//    Animal("王五")
    Animal("老虎", "非洲")
//    val p = Person("李四", 20)
//    println(p.name)
}