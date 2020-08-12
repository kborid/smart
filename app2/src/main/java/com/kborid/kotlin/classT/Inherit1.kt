package com.kborid.kotlin.classT

open class Rect {
    open fun draw() {
        println("Drawing a rectangle")
    }

    var borderColor = "red"
        get() = "black"
        set(value) {
            field = value
        }
}

class Rectangle : Rect() {
    override fun draw() {
        super.draw()
        println("Filling the rectangle")
    }

    val fillColor: String get() = super.borderColor

    inner class Shape {
        fun fill() {
            println("内部类Shape的fill方法调用")
        }

        fun drawShape() {
            // 一个类的内部类中访问该类的超类方法或属性
            println(super@Rectangle.borderColor)
            super@Rectangle.draw() // 调用 Rectangle 的 draw() 实现
            fill()
            println("Drawn a filled rectangle with color ${super@Rectangle.borderColor}") // 使用 Rectangle 所实现的 borderColor 的 get()
        }
    }
}

abstract class T {
    abstract fun tt()
}

class TT : T() {
    val isEmpty get() = this.test.length == 0
    var test: String = ""
        get() = "test"
        set(value) {
            field = value
        }

    override fun tt() {
    }
}

fun main() {
    println(Rect().borderColor)
}