package com.kborid.kotlin

class TTest {

    val oneMillion = 1_000_000
    val a: Int = 10;
    val b: Int = 2;
    val max = if (a > b) a else b

    companion object {
        @JvmStatic
        var instance: TTest? = TTest()
            private set
    }

    public fun test(s: String) {
        oneMillion.shr(3)
        println(oneMillion)
        println(s)
    }

    public fun sum(a: Int, b: Int): Int = a + b
}

fun main(args: Array<String>) {
    println("Hello World By Kotlin!")
    println(TTest.instance?.sum(3, 9))
    TTest.instance?.test(TTest.instance?.oneMillion.toString())
}

