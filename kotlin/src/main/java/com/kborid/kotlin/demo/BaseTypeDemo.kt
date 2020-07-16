package com.kborid.kotlin.demo

class BaseTypeDemo {
    fun demo1() {
        val f1 = 1_0_0_0f
        var d1 = f1.toInt()
        d1 *= 3
        println(f1)
        println(d1)
    }

    fun demo2() {
        var count = 0
        while (count < 5) {
            val type = (Math.random() * 10).toInt();
            when(type) {
                in 0..5 -> println("我在0到5之间，$type")
                5, 6, 7 -> println("我们是三剑客，$type")
                else -> println("我是被遗弃的，$type")
            }
            count++;

            when {
                isEvent(count) -> println("执行双数次了，$count")
            }
        }
    }

    private fun isEvent(type: Int): Boolean {
        return (type / 2) == 1
    }
}

fun main() {
    val demo = BaseTypeDemo()
    demo.demo1()
    demo.demo2()
}

