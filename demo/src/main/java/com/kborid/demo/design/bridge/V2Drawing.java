package com.kborid.demo.design.bridge;

//具体实现化逻辑ConcreteImplementor
//实现了接口方法，使用DP2进行绘图
public class V2Drawing implements Drawing {

    DP2 dp2;

    public V2Drawing() {
        dp2 = new DP2();
    }

    @Override
    public void drawRantangle() {
        dp2.drawRantanle();
    }

    @Override
    public void drawCircle() {
        dp2.drawCircle();
    }
}
