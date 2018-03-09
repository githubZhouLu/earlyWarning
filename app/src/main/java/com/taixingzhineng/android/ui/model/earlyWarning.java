package com.taixingzhineng.android.ui.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2017/12/16.
 * 预警
 */
public class earlyWarning {
    private String id;// 预警ID
    private String warnMessageno;		// 预警信息编号
    private String name;		// 预警名称
    private String rank;		// 级别
    private String companyId;		// 来源单位
    private String startTime;		// 开始时间
    private String endTime;		// 结束时间
    private String state;		// 预警状态
    private String content;		// 预警内容
    private String releaseTime;		// 发布时间
    private String effectRange;		// 影响范围
    private String sugAction;		// 建议行动
    private String impuserNo;		// 重要用户清单
    private String impuserName;		// 重要用户清单
    private String userOrgan;		// 重要用户管理单位
    private String image;		// 预警图标
    private String adjustFlag;		// 调整状态0表示未调整，1表示调整
    private String reportTime;  //预警上报时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWarnMessageno() {
        return warnMessageno;
    }

    public void setWarnMessageno(String warnMessageno) {
        this.warnMessageno = warnMessageno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getEffectRange() {
        return effectRange;
    }

    public void setEffectRange(String effectRange) {
        this.effectRange = effectRange;
    }

    public String getSugAction() {
        return sugAction;
    }

    public void setSugAction(String sugAction) {
        this.sugAction = sugAction;
    }

    public String getImpuserNo() {
        return impuserNo;
    }

    public void setImpuserNo(String impuserNo) {
        this.impuserNo = impuserNo;
    }

    public String getImpuserName() {
        return impuserName;
    }

    public void setImpuserName(String impuserName) {
        this.impuserName = impuserName;
    }

    public String getUserOrgan() {
        return userOrgan;
    }

    public void setUserOrgan(String userOrgan) {
        this.userOrgan = userOrgan;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAdjustFlag() {
        return adjustFlag;
    }

    public void setAdjustFlag(String adjustFlag) {
        this.adjustFlag = adjustFlag;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }
}
