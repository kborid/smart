package com.kborid.kotlin.demo.classT

open class Fruit(name: String) {
    init {
        println("基类Fruit[${name}]初始化块调用")
    }

    var name: String = "水果"

    init {
        this.name = name
    }

    open fun display() {
        println("${name}，超级甜")
    }

    fun say() {
        println("我特别好吃")
    }
}

class Orange : Fruit(name = "橙子") {

    init {
        println("${this.javaClass.simpleName}[${this.name}]初始化块调用")
    }

    override fun display() {
        super.display()
        print("我不酸，${super.say()}")
    }
}

class Other : Fruit {

    init {
        println("${this.javaClass.simpleName}[${this.name}]初始化块调用")
    }

    var size: Float = 50f

    constructor(size: Float) : this("其他种类", size) {
        when {
            size <= 10 -> this.name = "我猜是海棠"
            size >= 100 -> this.name = "我猜是西瓜"
        }
    }

    constructor(name: String, size: Float) : super(name) {
        this.size = size
    }

    override fun display() {
        super.display()
        println("我的大小是${size.toInt()}")
    }
}

fun main() {
    val orange = Orange()
    orange.display()
    val other = Other("苹果", 60f)
    other.display()
    val other1 = Other(100f)
    other1.display()
}

open class BaseA(add: String) {
    var name: String = ""

    init {
        name = add
    }

    fun draw() {
        println("我在父类${name}中draw")
    }
}

open class BaseB(name: String) {
    var name: String = "B"

    fun draw() {
        println("我在父类${name}中draw")
    }
}

open class Son(name: String) : BaseA(name) {

    init {
        println("次构造函数, name:$name")
    }

    open fun invoke() {
        println("派生类方法调用, t()")
        // 调用超类实现
        super<BaseA>.draw()
    }

    inner class Inner {
        fun invoke() {
            // 一个类的内部类中访问该类的超类方法或属性
            println(super@Son.name)
            super@Son.draw()
        }
    }
}

//fun main() {
//    val s = Son("蛋")
//    s.invoke()
//    s.Inner().invoke()
//
//    val o = object {
//        var name: String = "我是匿名类"
//    }
//
//    println("我是${o}, ${o.name}")
//}