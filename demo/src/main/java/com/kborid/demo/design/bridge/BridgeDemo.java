package com.kborid.demo.design.bridge;

public class BridgeDemo {

    public static void main(String[] args) {
        AbstractShape shape = new Circle(new V1Drawing());
        shape.drawCircle();
        shape.drawRectangle();
    }
}
