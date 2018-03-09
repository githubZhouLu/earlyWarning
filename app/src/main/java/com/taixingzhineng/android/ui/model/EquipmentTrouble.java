package com.taixingzhineng.android.ui.model;

/**
 * 设备隐患Entity
 * @author jack zou
 * @version 2017-12-29
 */
public class EquipmentTrouble{

	private String id;
	private String impuserNo;		// 重要用户编号
	private String result;		// 检查结果
	private String checker;		// 检查人员
	private String checkTime;		// 实际检查时间
	private String troubleType;		// 安全隐患类型
	private String suggestion;		// 整改建议
	private String planTime;		// 计划整改时间
	private String realTime;		// 实际整改时间
	private String by1;		// 备用字段1
	private String by2;		// 备用字段2
	private String by3;		// 备用字段3

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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public String getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}

	public String getTroubleType() {
		return troubleType;
	}

	public void setTroubleType(String troubleType) {
		this.troubleType = troubleType;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public String getPlanTime() {
		return planTime;
	}

	public void setPlanTime(String planTime) {
		this.planTime = planTime;
	}

	public String getRealTime() {
		return realTime;
	}

	public void setRealTime(String realTime) {
		this.realTime = realTime;
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
}