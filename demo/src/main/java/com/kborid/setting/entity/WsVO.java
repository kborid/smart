package com.kborid.setting.entity;

import java.time.LocalDateTime;

/**
 * WsVO
 *
 * @description: 文书VO
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @date: 2021/1/19
 */
public class WsVO {

    /**
     * 主键编号
     */
    private String bh;

    /**
     * 任务编号
     */
    private String bhRw;

    /**
     * 大任务编号
     */
    private String bhDrw;

    /**
     * 子任务编号
     */
    private String bhZrw;

    /**
     * 目录编号
     */
    private String bhMl;

    /**
     * 模版编号
     */
    private String bhMb;

    /**
     * 关联业务编号
     */
    private String bhGlyw;

    /**
     * 文书名称
     */
    private String mc;

    /**
     * 文书格式
     */
    private String gs;

    /**
     * 文书路径
     */
    private String path;

    /**
     * 创建人
     */
    private String cjr;

    /**
     * 创建时间
     */
    private LocalDateTime cjsj;

    /**
     * 最后更新时间
     */
    private LocalDateTime zhgxsj;

    @Override
    public String toString() {
        return "WsVO{" +
                "bh='" + bh + '\'' +
                ", bhRw='" + bhRw + '\'' +
                ", bhDrw='" + bhDrw + '\'' +
                ", bhZrw='" + bhZrw + '\'' +
                ", bhMl='" + bhMl + '\'' +
                ", bhMb='" + bhMb + '\'' +
                ", bhGlyw='" + bhGlyw + '\'' +
                ", mc='" + mc + '\'' +
                ", gs='" + gs + '\'' +
                ", path='" + path + '\'' +
                ", cjr='" + cjr + '\'' +
                ", cjsj=" + cjsj +
                ", zhgxsj=" + zhgxsj +
                '}';
    }

    public String getBh() {
        return bh;
    }

    public void setBh(String bh) {
        this.bh = bh;
    }

    public String getBhRw() {
        return bhRw;
    }

    public void setBhRw(String bhRw) {
        this.bhRw = bhRw;
    }

    public String getBhDrw() {
        return bhDrw;
    }

    public void setBhDrw(String bhDrw) {
        this.bhDrw = bhDrw;
    }

    public String getBhZrw() {
        return bhZrw;
    }

    public void setBhZrw(String bhZrw) {
        this.bhZrw = bhZrw;
    }

    public String getBhMl() {
        return bhMl;
    }

    public void setBhMl(String bhMl) {
        this.bhMl = bhMl;
    }

    public String getBhMb() {
        return bhMb;
    }

    public void setBhMb(String bhMb) {
        this.bhMb = bhMb;
    }

    public String getBhGlyw() {
        return bhGlyw;
    }

    public void setBhGlyw(String bhGlyw) {
        this.bhGlyw = bhGlyw;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getGs() {
        return gs;
    }

    public void setGs(String gs) {
        this.gs = gs;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    public LocalDateTime getCjsj() {
        return cjsj;
    }

    public void setCjsj(LocalDateTime cjsj) {
        this.cjsj = cjsj;
    }

    public LocalDateTime getZhgxsj() {
        return zhgxsj;
    }

    public void setZhgxsj(LocalDateTime zhgxsj) {
        this.zhgxsj = zhgxsj;
    }
}
