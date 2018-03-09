package com.taixingzhineng.android.ui.model;

/**
 * Created by Administrator on 2017/12/16.
 * 预警
 */
public class HLEquipment {
    private String id;
    private String impuserNo;		// 重要用户编号
    private String switchType;		// 开关型号
    private String ratedCurrent;		// 额定电流
    private String voltageRank;		// 电压等级
    private String operatMechanism;		// 操作机构
    private String count;		// 台数
    private String operatConditions;		// 运行工况
    private String image;		// 图片
    private String updateTime;		// 修改时间

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

    public String getSwitchType() {
        return switchType;
    }

    public void setSwitchType(String switchType) {
        this.switchType = switchType;
    }

    public String getRatedCurrent() {
        return ratedCurrent;
    }

    public void setRatedCurrent(String ratedCurrent) {
        this.ratedCurrent = ratedCurrent;
    }

    public String getVoltageRank() {
        return voltageRank;
    }

    public void setVoltageRank(String voltageRank) {
        this.voltageRank = voltageRank;
    }

    public String getOperatMechanism() {
        return operatMechanism;
    }

    public void setOperatMechanism(String operatMechanism) {
        this.operatMechanism = operatMechanism;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getOperatConditions() {
        return operatConditions;
    }

    public void setOperatConditions(String operatConditions) {
        this.operatConditions = operatConditions;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
