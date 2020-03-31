package com.kborid.kotlin

import com.kborid.kotlin.pojo.CheckInfo

/**
 * 1、在顶层声明
 * 2、在伴生对象中声明
 * 3、在object类中声明
 */
const val TAG: String = "TTest"

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

    // begin kotlin
    println("$TAG, Hello World By Kotlin!")

    // 懒加载，必须val
    val name: String by lazy {
        "234"
    }
    println("$TAG, $name")

    // 延迟加载，必须var
    lateinit var check: CheckInfo
    check = CheckInfo()
    println(check.info)

    var a = 200
    var b = "123"
    var c = 0.4f
    println(a.tt().toBigDecimal())

    val a1 = 128
    val a1Box: Int? = a1
    println(a1 === a1Box)

    val arr: IntArray = intArrayOf(2, 3, 4)
    arr.map { i -> i.toString() }
    val arr2 = Array(4, { i -> i * 3 })
    println(arr2)

    var numA = 100
    var numB = 10

    numA.plus(numB)

    val str: String = "sdklgslndglsdfsf"

    println(str[str.lastIndex])
    println(str.last())
}

fun Int.tt(): Int {
    return this * 2
}
