package com.taixingzhineng.android.ui.model;

/**
 * Created by Administrator on 2017/12/16.
 * 预警
 */
public class EarlyWarningAction {

    private String id;
    private String warnMessageno;		// 预警信息编号
    private String name;		// 预警名称
    private String rank;		// 级别
    private String type;		// 预警类别
    private String companyId;		// 来源单位
    private String startTime;		// 开始时间
    private String endTime;		// 结束时间
    private String state;		// 预警状态
    private String content;		// 预警内容
    private String releaseTime;		// 发布时间
    private String effectRange;		// 影响范围
    private String warnAction;		// 预警行动
    private String image;		// 预警图标
    private String adjustFlag;		// 调整状态(0表示未调整，1表示调整)
    private String impuserNo;//重要用户编号
    private String userName;//重要用户名称
    private String userId;//发布预警用户id
    private String num;		//回复条数

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getWarnAction() {
        return warnAction;
    }

    public void setWarnAction(String warnAction) {
        this.warnAction = warnAction;
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

    public String getImpuserNo() {
        return impuserNo;
    }

    public void setImpuserNo(String impuserNo) {
        this.impuserNo = impuserNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
