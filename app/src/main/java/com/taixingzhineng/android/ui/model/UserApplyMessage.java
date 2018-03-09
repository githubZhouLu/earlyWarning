package com.taixingzhineng.android.ui.model;

/**
 * Created by Administrator on 2017/12/16.
 * 预警
 */
public class UserApplyMessage {

    private String id;
    private String impuserNo;		// 重要用户编号
    private String confirmYear;		// 核定年份
    private String iptRank;		// 重要等级
    private String confirmState;		// 重要用户确认情况
    private String giveAt;		// 申报表已发放
    private String submitAt;		// 申报表是否提交
    private String applyFormid;		// 申报表
    private String meetState;		// 供电电源是否满足配置要求
    private String ynDown;		// 是否地下站
    private String ynGeneratrix;		// 是否专有应急母线
    private String lowVoltage;		// 低电压释放装置情况
    private String securityProtocal;		// 安全协议
    private String protectProtocal;		// 安全保障协议
    private String superior;		// 上级行业主管部门
    private String by1;		// 备用字段1
    private String by2;		// 备用字段2
    private String by3;		// 备用字段3
    private String adjustFlag;		// 调整状态(0表示未调整，1表示调整)

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

    public String getConfirmYear() {
        return confirmYear;
    }

    public void setConfirmYear(String confirmYear) {
        this.confirmYear = confirmYear;
    }

    public String getIptRank() {
        return iptRank;
    }

    public void setIptRank(String iptRank) {
        this.iptRank = iptRank;
    }

    public String getConfirmState() {
        return confirmState;
    }

    public void setConfirmState(String confirmState) {
        this.confirmState = confirmState;
    }

    public String getGiveAt() {
        return giveAt;
    }

    public void setGiveAt(String giveAt) {
        this.giveAt = giveAt;
    }

    public String getSubmitAt() {
        return submitAt;
    }

    public void setSubmitAt(String submitAt) {
        this.submitAt = submitAt;
    }

    public String getApplyFormid() {
        return applyFormid;
    }

    public void setApplyFormid(String applyFormid) {
        this.applyFormid = applyFormid;
    }

    public String getMeetState() {
        return meetState;
    }

    public void setMeetState(String meetState) {
        this.meetState = meetState;
    }

    public String getYnDown() {
        return ynDown;
    }

    public void setYnDown(String ynDown) {
        this.ynDown = ynDown;
    }

    public String getYnGeneratrix() {
        return ynGeneratrix;
    }

    public void setYnGeneratrix(String ynGeneratrix) {
        this.ynGeneratrix = ynGeneratrix;
    }

    public String getLowVoltage() {
        return lowVoltage;
    }

    public void setLowVoltage(String lowVoltage) {
        this.lowVoltage = lowVoltage;
    }

    public String getSecurityProtocal() {
        return securityProtocal;
    }

    public void setSecurityProtocal(String securityProtocal) {
        this.securityProtocal = securityProtocal;
    }

    public String getProtectProtocal() {
        return protectProtocal;
    }

    public void setProtectProtocal(String protectProtocal) {
        this.protectProtocal = protectProtocal;
    }

    public String getSuperior() {
        return superior;
    }

    public void setSuperior(String superior) {
        this.superior = superior;
    }

    public String getBy1() {
        return by1;
    }

    public void setBy1(String by1) {
        this.by1 = by1;
    }

    public String getBy2() {
        return by2;
    }

    public void setBy2(String by2) {
        this.by2 = by2;
    }

    public String getBy3() {
        return by3;
    }

    public void setBy3(String by3) {
        this.by3 = by3;
    }

    public String getAdjustFlag() {
        return adjustFlag;
    }

    public void setAdjustFlag(String adjustFlag) {
        this.adjustFlag = adjustFlag;
    }
}
