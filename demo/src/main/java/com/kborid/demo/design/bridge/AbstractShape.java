package com.kborid.demo.design.bridge;

//抽象化角色Abstraction
abstract class AbstractShape {

    //持有实现的角色Implementor(作图类)
    protected Drawing myDrawing;

    public AbstractShape(Drawing drawing) {
        this.myDrawing = drawing;
    }

    abstract public void draw();

    //保护方法drawRectangle
    protected void drawRectangle() {
        myDrawing.drawRantangle();
    }

    //保护方法drawCircle
    protected void drawCircle() {
        myDrawing.drawCircle();
    }
}
