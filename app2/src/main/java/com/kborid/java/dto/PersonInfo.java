package com.kborid.java.dto;

public class PersonInfo {
    private String name;
    private Integer age;

    public PersonInfo(String name, Integer age) {
        System.out.println("PersonInfo construction invoke");
        this.name = name;
        this.age = age;
    }

    {
        System.out.println("我是代码块");
    }

    static {
        System.out.println("我是静态代码块");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "PersonInfo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
