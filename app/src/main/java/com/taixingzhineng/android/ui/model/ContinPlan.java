package com.taixingzhineng.android.ui.model;

/**
 * Created by Administrator on 2018/1/12.
 */

public class ContinPlan {
    private String id;
    private String impuserNo;		// 重要用户编号
    private String planmessages;		// 应急预案
    private String emergencyRehearsal;		// 定期开展应急演练
    private String lastTime;		// 上次演练时间
    private String planTime;		// 近期计划开展时间

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

    public String getPlanmessages() {
        return planmessages;
    }

    public void setPlanmessages(String planmessages) {
        this.planmessages = planmessages;
    }

    public String getEmergencyRehearsal() {
        return emergencyRehearsal;
    }

    public void setEmergencyRehearsal(String emergencyRehearsal) {
        this.emergencyRehearsal = emergencyRehearsal;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getPlanTime() {
        return planTime;
    }

    public void setPlanTime(String planTime) {
        this.planTime = planTime;
    }
}
