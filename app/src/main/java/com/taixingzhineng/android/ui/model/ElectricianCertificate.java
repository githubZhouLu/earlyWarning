package com.taixingzhineng.android.ui.model;

/**
 * Created by Administrator on 2018/1/12.
 */

public class ElectricianCertificate {
    private String id;
    private String impuserNo;		// 重要用户编号
    private String electricianName;		// 电工姓名
    private String electricianNumber;		// 电工证编号
    private String validityDate;		// 有效期
    private String registrationUnit;		// 注册单位

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

    public String getElectricianName() {
        return electricianName;
    }

    public void setElectricianName(String electricianName) {
        this.electricianName = electricianName;
    }

    public String getElectricianNumber() {
        return electricianNumber;
    }

    public void setElectricianNumber(String electricianNumber) {
        this.electricianNumber = electricianNumber;
    }

    public String getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(String validityDate) {
        this.validityDate = validityDate;
    }

    public String getRegistrationUnit() {
        return registrationUnit;
    }

    public void setRegistrationUnit(String registrationUnit) {
        this.registrationUnit = registrationUnit;
    }
}
