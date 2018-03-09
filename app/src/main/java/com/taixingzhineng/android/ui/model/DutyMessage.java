package com.taixingzhineng.android.ui.model;

/**
 * 值班情况Entity
 * @author jack zou
 * @version 2017-12-29
 */
public class DutyMessage {

	private String id;
	private String dqmanager;		// 电气主管
	private String dgperson;		// 电工负责人
	private String yhperson;		// 用户负责人
	private String dutyWay;		// 值班方式
	private String personYb;		// 持证人数是否满足配置要求
	private String peoples;		// 持证总人数
	private String managerTel;		// 电气主管电话
	private String dutyTel;		// 电工负责人电话
	private String userTel;		// 用户负责人电话
	private String dutyCount;		// 值班人数
	private String dutyDate;		// 值班日期
	private String by1;		// 备用字段1
	private String impuserNo;		// 备用字段2
	private String by3;		// 备用字段3

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDqmanager() {
		return dqmanager;
	}

	public void setDqmanager(String dqmanager) {
		this.dqmanager = dqmanager;
	}

	public String getDgperson() {
		return dgperson;
	}

	public void setDgperson(String dgperson) {
		this.dgperson = dgperson;
	}

	public String getYhperson() {
		return yhperson;
	}

	public void setYhperson(String yhperson) {
		this.yhperson = yhperson;
	}

	public String getDutyWay() {
		return dutyWay;
	}

	public void setDutyWay(String dutyWay) {
		this.dutyWay = dutyWay;
	}

	public String getPersonYb() {
		return personYb;
	}

	public void setPersonYb(String personYb) {
		this.personYb = personYb;
	}

	public String getPeoples() {
		return peoples;
	}

	public void setPeoples(String peoples) {
		this.peoples = peoples;
	}

	public String getManagerTel() {
		return managerTel;
	}

	public void setManagerTel(String managerTel) {
		this.managerTel = managerTel;
	}

	public String getDutyTel() {
		return dutyTel;
	}

	public void setDutyTel(String dutyTel) {
		this.dutyTel = dutyTel;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public String getDutyCount() {
		return dutyCount;
	}

	public void setDutyCount(String dutyCount) {
		this.dutyCount = dutyCount;
	}

	public String getDutyDate() {
		return dutyDate;
	}

	public void setDutyDate(String dutyDate) {
		this.dutyDate = dutyDate;
	}

	public String getBy1() {
		return by1;
	}

	public void setBy1(String by1) {
		this.by1 = by1;
	}

	public String getImpuserNo() {
		return impuserNo;
	}

	public void setImpuserNo(String impuserNo) {
		this.impuserNo = impuserNo;
	}

	public String getBy3() {
		return by3;
	}

	public void setBy3(String by3) {
		this.by3 = by3;
	}
}