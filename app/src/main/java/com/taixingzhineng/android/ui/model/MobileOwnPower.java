package com.taixingzhineng.android.ui.model;

/**
 * Created by Administrator on 2018/1/11.
 */

public class MobileOwnPower {
    private String id;
    private String impuserNo;		// 重要用户编号
    private String loadCap;		// 自备电源满足负荷容量
    private String proWay;		// 重要电荷保障方式
    private String carPos;		// 发电车接入位置

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImpuserNo() {
        return impuserNo;
    }

    public void setImpuserNo(String impuserNo) {
        this.impuserNo = impuserNo;
    }

    public String getLoadCap() {
        return loadCap;
    }

    public void setLoadCap(String loadCap) {
        this.loadCap = loadCap;
    }

    public String getProWay() {
        return proWay;
    }

    public void setProWay(String proWay) {
        this.proWay = proWay;
    }

    public String getCarPos() {
        return carPos;
    }

    public void setCarPos(String carPos) {
        this.carPos = carPos;
    }
}
