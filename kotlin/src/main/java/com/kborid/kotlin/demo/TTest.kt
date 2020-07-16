package com.kborid.kotlin.demo

import com.kborid.kotlin.demo.pojo.CheckInfo
import com.kborid.kotlin.demo.pojo.UserInfoImpl

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

    /**
     * 运算符demo
     */
    fun ysfDemo() {
        var arr = arrayOfNulls<Int>(3)
        arr.forEachIndexed { index, i ->
            run {
                println(i)
                arr[index] = (index + 1) * 3
            }
        }
        arr.forEach { i -> println(i) }

        val numA: Int = 4
        // 在Java中可以这么写，但是Kotlin中直接会报错，kotlin不支持...?...:...三目运算符。
//    var numB: Int = (numA > 2) ? 3 : 5

        // kotlin中直接用if..else替代。例：
        val numB: Int = if (numA > 2) 3 else 5  // 当numA大于2时输出numB的值为3，反之为5
        println("numB = > $numB")

        // 循环5次，且步长为1的递增
        for (i in 0 until 5 step 2) {
            print("i => $i \t")
        }

        for (i in "abcdefg") {
            print("i => $i \t")
            println()
        }

        // 初始化一个长度为3，所有值为null的数组
        var array = arrayOfNulls<String>(3)
        when {
            true -> println("true")
            else -> println("false")
        }
    }

    fun convert() {
        val origin: Float = 65.0f;println(origin.toString())
        val int: Int = origin.toInt();println(int.toString())
        val long = origin.toLong();println("++${long.toString()}++")
        val float = origin.toDouble().toFloat(); println(float.toString())
    }

    fun arrayDemo() {
        val int_array: IntArray = intArrayOf(3, 4, 6)
        println(int_array.get(2))
        var long_array: LongArray = longArrayOf(1, 2, 3)
        var float_array: FloatArray = floatArrayOf(1.0f, 2.0f, 3.0f)
        var double_array: DoubleArray = doubleArrayOf(1.0, 2.0, 3.0)
        var boolean_array: BooleanArray = booleanArrayOf(true, false, true)
        var char_array: CharArray = charArrayOf('a', 'b', 'c')

        var int_array1: Array<Int> = arrayOf(1, 2, 3)
        var long_array1: Array<Long> = arrayOf(1, 2, 3)
        var float_array1: Array<Float> = arrayOf(1.0f, 2.0f, 3.0f)
        var double_array1: Array<Double> = arrayOf(1.0, 2.0, 3.0)
        var boolean_array1: Array<Boolean> = arrayOf(true, false, true)
        var char_array1: Array<Char> = arrayOf('a', 'b', 'c')
        var string_array1: Array<String> = arrayOf("How", "Are", "You")
    }

    fun containerDemo() {
        //to方式映射
        var goodsMap: Map<String, String> = mapOf("苹果" to "iPhone8", "华为" to "Mate10", "小米" to "M6", "欧珀" to "OPPO R11", "步步高" to "vivo X9S", "魅族" to "MZPro6S")
        //Pair方式映射
        var goodsMutMap: MutableMap<String, String> = mutableMapOf(Pair("苹果", "iPhone8"), Pair("华为", "Mate10"), Pair("小米", "M6"), Pair("欧珀", "OPPO R11"), Pair("步步高", "vivo X9S"), Pair("魅族", "MZPro6S"))
    }

    fun iteratorDemo() {
        val poems: Array<String?> = arrayOf("床前明月光", "疑是地上霜", null, "举头望明月", "低头思故乡")
        for (poemIndex in poems.indices) {
            println("${poems.get(poemIndex)}${lastSymbol(poems.size, poemIndex)}")
        }

        var i: Int = 0
        var isFounded = false
        outside@ while (i < poems.size) {
            var j = 0
            val item = poems[i];

            if (item.isNullOrEmpty()) {
                continue
            }
            while (j < item.length) {
                if (item.get(j) == '望') {
                    isFounded = true
                    break@outside
                }
                j++
            }
            i++
        }
    }

    /**
     * 判断句子末尾标点符号
     */
    private fun lastSymbol(size: Int, index: Int): String {
        return if (index == size - 1) "。" else "，"
    }

    fun nullVarDemo() {
        val str: String? = null
        val str1: String = null.toString()
        println(str1)
        println(str?.length)
        println(str?.length ?: 0) // elvis运算符
//        println(str!!.length)
    }
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

    TTest.instance?.nullVarDemo()
}

fun Int.tt(): Int {
    return this * 2
}
