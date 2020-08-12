package com.kborid.java.collection;

import java.util.*;

public class MapDemo {
    static final int MAXIMUM_CAPACITY = 1 << 30;

    Map<String, String> map = new HashMap<>(20);

    public static void main(String[] args) {

//        System.out.println(tableSizeFor(1));
//        System.out.println(tableSizeFor(7));
//        System.out.println(tableSizeFor(8));
//        System.out.println(tableSizeFor(20));
//
//        System.out.println(1 << 30);
        int i = 8;
        System.out.println(i >> 1);
        int j = 8;
        System.out.println(j << 1);
//        stringOut();
//        asListTest();
//
//        Pizza pizza = new Pizza();
//        pizza.setStatus(Pizza.PizzaStatus.READY);
//        System.out.println(pizza.getStatus().isDelivered());
//        System.out.println(pizza.getStatus().isOrdered());
//        System.out.println(pizza.getStatus().isReady());
    }

    /**
     * Returns a power of two size for the given target capacity.
     */
    static int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    static void stringOut() {
        // 中文常见字
        String s = "你好";
        System.out.println("1. string length =" + s.length());
        System.out.println("1. string bytes length =" + s.getBytes().length);
        System.out.println("1. string char length =" + s.toCharArray().length);
        System.out.println();
        // emojis
        s = "👦👩";
        System.out.println("2. string length =" + s.length());
        System.out.println("2. string bytes length =" + s.getBytes().length);
        System.out.println("2. string char length =" + s.toCharArray().length);
        System.out.println();
        // 中文生僻字
        s = "𡃁妹";
        System.out.println("3. string length =" + s.length());
        System.out.println("3. string bytes length =" + s.getBytes().length);
        System.out.println("3. string char length =" + s.toCharArray().length);
        System.out.println();
    }

    static void asListTest() {

        // original data
        String[] arr = {"apple", "pear", "orange"};
        // after asList data
        List<String> list = Arrays.asList(arr);
        // traversal遍历
        traversal(list);

        // UnsupportedOperationException
//        list.add("44");

        // 会改变原始数组的值，这里指的是 arr[]
        list.set(0, "11");
        traversal(list);

        System.out.println("======");
        traversal(arr);

        ArrayList<String> list1 = new ArrayList<>(list);

        Iterator<String> it = list1.iterator();
        String tmp = null;
        while (it.hasNext()) {
            tmp = it.next();
            System.out.println(tmp);
            if ("apple".equals(tmp)) {
                list1.remove(tmp);
//                list1.add("34");
            }
        }

        traversal(list1);
    }

    private static <T> void traversal(T[] arr) {
        for (T str :
                arr) {
            System.out.println(str);
        }
    }

    private static <T> void traversal(List<T> list) {
        for (T str :
                list) {
            System.out.println(str);
        }
    }
}
