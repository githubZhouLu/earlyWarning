package com.taixingzhineng.android.ui.model;

/**
 * Created by Administrator on 2018/1/12.
 */

public class UpsEps {
    private String id;
    private String impuserNo;		// 重要用户编号
    private String capacity;		// 容量
    private String hour;		// 支持时间（小时）
    private String linkWay;		// 接入方式
    private String installWay;		// 安装方式
    private String offerCharge;		// 所供负荷
    private String remarks;		// 备注
    private String image;		// 图片
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

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getLinkWay() {
        return linkWay;
    }

    public void setLinkWay(String linkWay) {
        this.linkWay = linkWay;
    }

    public String getInstallWay() {
        return installWay;
    }

    public void setInstallWay(String installWay) {
        this.installWay = installWay;
    }

    public String getOfferCharge() {
        return offerCharge;
    }

    public void setOfferCharge(String offerCharge) {
        this.offerCharge = offerCharge;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
