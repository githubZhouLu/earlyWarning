package com.taixingzhineng.android.ui.model;

/**
 * Created by Administrator on 2017/12/16.
 * 预警
 */
public class UserLoadCharge {

    private String id;
    private String impuserNo;		// 重要用户编号
    private String loadType;		// 重要负荷类型
    private String supplyEletric;		// 连续供电需求
    private String capacity;		// 容量（KW）
    private String remarks;		// 备注
    private String updateDate;      //更新时间

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

    public String getLoadType() {
        return loadType;
    }

    public void setLoadType(String loadType) {
        this.loadType = loadType;
    }

    public String getSupplyEletric() {
        return supplyEletric;
    }

    public void setSupplyEletric(String supplyEletric) {
        this.supplyEletric = supplyEletric;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
