package com.taixingzhineng.android.ui.model;

/**
 * 安全责任落实情况Entity
 * @author zhou lu
 * @version 2017-12-28
 */
public class SecuritySituation{

	private String id;
	private String impuserNo;		// 重要用户编号
	private String powerContract;		// 供用电合同信息
	private String contractUpdateTime;		// 合同修改时间
	private String DispatchAgreement;		// 调度协议书信息
	private String agreementUpdateTime;		// 协议书修改时间

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

	public String getPowerContract() {
		return powerContract;
	}

	public void setPowerContract(String powerContract) {
		this.powerContract = powerContract;
	}

	public String getContractUpdateTime() {
		return contractUpdateTime;
	}

	public void setContractUpdateTime(String contractUpdateTime) {
		this.contractUpdateTime = contractUpdateTime;
	}

	public String getDispatchAgreement() {
		return DispatchAgreement;
	}

	public void setDispatchAgreement(String dispatchAgreement) {
		DispatchAgreement = dispatchAgreement;
	}

	public String getAgreementUpdateTime() {
		return agreementUpdateTime;
	}

	public void setAgreementUpdateTime(String agreementUpdateTime) {
		this.agreementUpdateTime = agreementUpdateTime;
	}
}