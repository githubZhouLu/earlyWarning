package com.taixingzhineng.android.ui.model;

/**
 * Created by Administrator on 2017/12/16.
 * 预警
 */
public class UserPowerTransformer {
    private String id;
    private String impuserNo;		// 重要用户编号
    private String transformerType;		// 型号
    private String nameplate;		// 变压器铭牌
    private String factory;		// 制造厂
    private String type;		// 类型
    private String capacity;		// 容量
    private String fVoltage;		// 一次测电压
    private String sVoltage;		// 二次测电压
    private String linkGroup;		// 接线组别
    private String impedance;		// 阻抗（欧姆）
    private String outfactoryDate;		// 出厂日期
    private String operatConditions;		// 运行工况
    private String url;		// 图片
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

    public String getTransformerType() {
        return transformerType;
    }

    public void setTransformerType(String transformerType) {
        this.transformerType = transformerType;
    }

    public String getNameplate() {
        return nameplate;
    }

    public void setNameplate(String nameplate) {
        this.nameplate = nameplate;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getfVoltage() {
        return fVoltage;
    }

    public void setfVoltage(String fVoltage) {
        this.fVoltage = fVoltage;
    }

    public String getsVoltage() {
        return sVoltage;
    }

    public void setsVoltage(String sVoltage) {
        this.sVoltage = sVoltage;
    }

    public String getLinkGroup() {
        return linkGroup;
    }

    public void setLinkGroup(String linkGroup) {
        this.linkGroup = linkGroup;
    }

    public String getImpedance() {
        return impedance;
    }

    public void setImpedance(String impedance) {
        this.impedance = impedance;
    }

    public String getOutfactoryDate() {
        return outfactoryDate;
    }

    public void setOutfactoryDate(String outfactoryDate) {
        this.outfactoryDate = outfactoryDate;
    }

    public String getOperatConditions() {
        return operatConditions;
    }

    public void setOperatConditions(String operatConditions) {
        this.operatConditions = operatConditions;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
