package com.kborid.demo.design.bridge;

//修正抽象化角色Refined Abstraction(矩形)
public class Rantangle extends AbstractShape {

    public Rantangle(Drawing drawing) {
        super(drawing);
    }

    @Override
    public void draw() {
        drawRectangle();
    }
}
