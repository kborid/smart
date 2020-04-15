package com.kborid.kotlin

import com.kborid.kotlin.pojo.CheckInfo
import com.kborid.kotlin.pojo.UserInfoImpl

/**
 * 1、在顶层声明
 * 2、在伴生对象中声明
 * 3、在object类中声明
 */
const val TAG: String = "TTest"

class TTest {

    val oneMillion = 1_000_000
    val a: Int = 10
    val b: Int = 2
    val max = if (a > b) a else b
    var arr = Array(4, { i -> (i + 1) })
    var arr2 = arr
    val arr3 = arr2

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
    println(CheckInfo().getCount())
    val info = UserInfoImpl()
    println(info.addr)
    println(info.getAge())
    println(info.address)

    var arr = arrayOfNulls<Int>(3)
    arr.forEachIndexed { index, i ->
        run {
            println(i)
            arr[index] = (index + 1) * 3
        }
    }
    arr.forEach { i -> println(i) }

    var numA: Int = 4
    // 在Java中可以这么写，但是Kotlin中直接会报错。
//  var numB: Int = (numA > 2) ? 3 : 5

    // kotlin中直接用if..else替代。例：
    var numB: Int = if (numA > 2) 3 else 5  // 当numA大于2时输出numB的值为3，反之为5
    println("numB = > $numB")

    // 循环5次，且步长为1的递增
    for (i in 0 until 5) {
        print("i => $i \t")
    }

    for (i in "abcdefg") {
        print("i => $i \t")
        println()
    }

    var array = arrayOfNulls<String>(3)
    when {
        true -> {
            println("true")
        }
        else -> {
            println("false")
        }
    }

}

fun Int.tt(): Int {
    return this * 2
}
