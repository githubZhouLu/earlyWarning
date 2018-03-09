package com.taixingzhineng.android.ui.model;

/**
 * Created by Administrator on 2018/1/11.
 */

public class DieselGenerator {
    private String id;
    private String impuserNo;		// 重要用户编号
    private String capacity;		// 容量
    private String voltageRank;		// 电压等级
    private String startCondition;		// 启动条件
    private String offerCharge;		// 所供电荷
    private String url;		// 图片
    private String remarks;		// 备注
    private String updateDate;		// 修改时间

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

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getVoltageRank() {
        return voltageRank;
    }

    public void setVoltageRank(String voltageRank) {
        this.voltageRank = voltageRank;
    }

    public String getStartCondition() {
        return startCondition;
    }

    public void setStartCondition(String startCondition) {
        this.startCondition = startCondition;
    }

    public String getOfferCharge() {
        return offerCharge;
    }

    public void setOfferCharge(String offerCharge) {
        this.offerCharge = offerCharge;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
