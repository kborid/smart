package com.kborid.kotlin.demo.classT

class Object {
    // 私有函数，所以其返回类型是匿名对象类型
    private fun foo() = object {
        val x: String = "x"
    }

    private fun foo1(): Any {
        return object {
            val y: String = "y"
        }
    }

    // 公有函数，所以其返回类型是 Any
    fun publicFoo() = object {
        val x: String = "x"
    }

    // 公有函数，所以其返回类型是 Any
    fun publicFoo1(): Any {
        return object {
            val y: String = "y"
        }
    }

    fun bar() {
        val x1 = foo().x        // 没问题
//        val x4 = foo1().y
//        val x2 = publicFoo().x  // 错误：未能解析的引用“x”
//        val x3 = publicFoo1().y
    }
}

object ObjectTest {

}

class MyClass1 {
    companion object Named {}
}

val x = MyClass1

class MyClass2 {
    companion object {}
}

val y = MyClass2

interface Factory<T> {
    fun create(): T
}

class MyClass {
    companion object : Factory<MyClass> {
        override fun create(): MyClass = MyClass()
    }
}

val f: Factory<MyClass> = MyClass

fun tt() {
    f.create()
}