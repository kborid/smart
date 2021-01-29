package com.kborid.demo.design.bridge;

//修正抽象化角色Refined Abstraction(圆形)
public class Circle extends AbstractShape {

    public Circle(Drawing drawing) {
        super(drawing);
    }

    @Override
    public void draw() {
        drawCircle();
    }
}

