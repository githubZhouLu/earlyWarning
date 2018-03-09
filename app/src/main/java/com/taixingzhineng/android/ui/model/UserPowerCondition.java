package com.taixingzhineng.android.ui.model;

/**
 * Created by Administrator on 2017/12/16.
 * 预警
 */
public class UserPowerCondition {
    private String id;
    private String impuserNo;		// 重要用户编号
    private String electricNo;		// 电系名称及编号
    private String lineName;		// 电源站及线路名称
    private String lineWay;		// 电源进线方式
    private String lockWay;		// 各电源之间连锁方式
    private String plineWay;		// 进线保护方式
    private String voltageWay;		// 电压等级
    private String powerUrl;		// 电源路径
    private String updatetime;		// 修改时间

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

    public String getElectricNo() {
        return electricNo;
    }

    public void setElectricNo(String electricNo) {
        this.electricNo = electricNo;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getLineWay() {
        return lineWay;
    }

    public void setLineWay(String lineWay) {
        this.lineWay = lineWay;
    }

    public String getLockWay() {
        return lockWay;
    }

    public void setLockWay(String lockWay) {
        this.lockWay = lockWay;
    }

    public String getPlineWay() {
        return plineWay;
    }

    public void setPlineWay(String plineWay) {
        this.plineWay = plineWay;
    }

    public String getVoltageWay() {
        return voltageWay;
    }

    public void setVoltageWay(String voltageWay) {
        this.voltageWay = voltageWay;
    }

    public String getPowerUrl() {
        return powerUrl;
    }

    public void setPowerUrl(String powerUrl) {
        this.powerUrl = powerUrl;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
}
