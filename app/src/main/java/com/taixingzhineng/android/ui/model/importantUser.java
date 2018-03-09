package com.taixingzhineng.android.ui.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/18.
 */
public class importantUser implements Serializable {
    private String id;
    private String userNo;		// 重要用户编号
    private String doorNo;		// 户号
    private String userName;		// 用户实际名称
    private String electricAddress;		// 供电地址
    private String businessType;		// 行业分类
    private String importRank;		// 重要等级
    private String powerCount;		// 供电电源数
    private String voltageRank;		// 电压等级
    private String jdDate;		// 接电日期
    private String updateDate;		// 更新日期
    private String supLeader;		// 主管部门
    private String tel;		// 联系方式
    private String totalCapacity;		// 总容量(KVA)
    private String htrl;//合同容量
    private String powerCompany;		// 供电单位
    private String cmsName;		// CMS户名

    private String pinyin;//姓名的拼音
    private String firstLetter;//用户姓名拼音的首字母

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getDoorNo() {
        return doorNo;
    }

    public void setDoorNo(String doorNo) {
        this.doorNo = doorNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getElectricAddress() {
        return electricAddress;
    }

    public void setElectricAddress(String electricAddress) {
        this.electricAddress = electricAddress;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getImportRank() {
        return importRank;
    }

    public void setImportRank(String importRank) {
        this.importRank = importRank;
    }

    public String getPowerCount() {
        return powerCount;
    }

    public void setPowerCount(String powerCount) {
        this.powerCount = powerCount;
    }

    public String getVoltageRank() {
        return voltageRank;
    }

    public void setVoltageRank(String voltageRank) {
        this.voltageRank = voltageRank;
    }

    public String getJdDate() {
        return jdDate;
    }

    public void setJdDate(String jdDate) {
        this.jdDate = jdDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getSupLeader() {
        return supLeader;
    }

    public void setSupLeader(String supLeader) {
        this.supLeader = supLeader;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(String totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public String getHtrl() {
        return htrl;
    }

    public void setHtrl(String htrl) {
        this.htrl = htrl;
    }

    public String getPowerCompany() {
        return powerCompany;
    }

    public void setPowerCompany(String powerCompany) {
        this.powerCompany = powerCompany;
    }

    public String getCmsName() {
        return cmsName;
    }

    public void setCmsName(String cmsName) {
        this.cmsName = cmsName;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }
}
