package com.kborid.java.泛型;

import java.util.ArrayList;
import java.util.List;

/**
 * 泛型类
 *
 * @param <T>
 */
public class Fruit<T> {
    private T value;

    Fruit(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    /**
     * 泛型方法
     *
     * @param key
     * @param <P>
     * @return
     */
    public <P> T findValue(P key) {
        return (T) new Fruit("Apple").getValue();
    }

    /**
     * 静态方法必须在方法上重新生命泛型类型
     *
     * @param key
     * @param <T>
     */
    public static <T> void show1(T key) {
    }

    public static void main(String[] args) {
        Fruit<String> f = new Fruit<>("Apple1");
        System.out.println(f.findValue("3"));
    }
}

/**
 * 泛型接口
 *
 * @param <T>
 */
interface ISweet<T> {
    T sweet(String sis);
}

class Demo2 {
    // 无法重载相同泛型参数方法
//    public void print(List<String> list) {
//    }

//    public void print(List<Boolean> list) {
//    }

    public static void main(String[] args) {
        // 1、编译期通过泛型区分不同类型
        // 2、运行期泛型会被擦除
        List<String> strings = new ArrayList<String>();
        List<Integer> integers = new ArrayList<Integer>();
        Class<?> classStringArrayList = strings.getClass();
        Class<?> classIntegerArrayList = integers.getClass();
        if (classStringArrayList.equals(classIntegerArrayList)) {
            System.out.println("泛型测试：类型相同");
        }
    }
}
