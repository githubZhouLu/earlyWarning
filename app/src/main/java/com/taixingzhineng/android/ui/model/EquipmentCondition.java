package com.taixingzhineng.android.ui.model;

/**
 * Created by Administrator on 2018/1/12.
 */

public class EquipmentCondition {
    private String id;
    private String impuserNo;		// 重要用户编号
    private String equipmentfile;		// 电气设备按周期开展实验情况
    private String toolsfile;		// 安全工器具齐全
    private String aqgqjfile;		// 安全工器具按周期开展实验
    private String jdbhfile;		// 继电保护定值按周期校验

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

    public String getEquipmentfile() {
        return equipmentfile;
    }

    public void setEquipmentfile(String equipmentfile) {
        this.equipmentfile = equipmentfile;
    }

    public String getToolsfile() {
        return toolsfile;
    }

    public void setToolsfile(String toolsfile) {
        this.toolsfile = toolsfile;
    }

    public String getAqgqjfile() {
        return aqgqjfile;
    }

    public void setAqgqjfile(String aqgqjfile) {
        this.aqgqjfile = aqgqjfile;
    }

    public String getJdbhfile() {
        return jdbhfile;
    }

    public void setJdbhfile(String jdbhfile) {
        this.jdbhfile = jdbhfile;
    }
}
