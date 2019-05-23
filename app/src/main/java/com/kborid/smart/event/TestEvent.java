package com.kborid.smart.event;

public class TestEvent {
    private String one;
    private String two;

    public TestEvent(String one, String two) {
        this.one = one;
        this.two = two;
    }

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public String getTwo() {
        return two;
    }

    public void setTwo(String two) {
        this.two = two;
    }

    @Override
    public String toString() {
        return "TestEvent{" +
                "one='" + one + '\'' +
                ", two='" + two + '\'' +
                '}';
    }
}
