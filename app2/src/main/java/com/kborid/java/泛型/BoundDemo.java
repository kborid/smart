package com.kborid.java.泛型;

import java.util.ArrayList;
import java.util.List;

class Animal {
    protected void move() {
        System.out.println("我会移动");
    }
}

class Person extends Animal {

    @Override
    protected void move() {
        System.out.println("我会行走");
    }
}

class Dog extends Animal {

    @Override
    protected void move() {
        System.out.println("我会奔跑");
    }
}

public class BoundDemo {

    public <T> List<? super T> getAnimal(T obj) {
        List<? super T> list = new ArrayList<>();
        list.add(obj);
        return list;
    }

    public static void main(String[] args) {
        Animal person = new Person();
        Animal dog = new Dog();

        /*
          PECS（Producer Extends Consumer Super）原则。

          Producer Extends 生产者使用Extends来确定上界，往里面放东西来生产
          Consumer Super 消费者使用Super来确定下界，往外取东西来消费

          1、频繁往外读取内容的，适合用上界Extends，即extends 可用于的返回类型限定，不能用于参数类型限定。
          2、经常往里插入的，适合用下界Super，super 可用于参数类型限定，不能用于返回类型限定。
          3、带有 super 超类型限定的通配符可以向泛型对象用写入，带有 extends 子类型限定的通配符可以向泛型对象读取。
         */
        List<? extends Animal> animals = new ArrayList<>();
//        animals.add(person);
//        animals.add(dog);
        Animal animal = animals.get(0);

        List<? super Animal> dogs = new ArrayList<>();
        dogs.add(dog);
        dogs.add(person);
//        Animal animal1 = dogs.get(0);
    }
}

