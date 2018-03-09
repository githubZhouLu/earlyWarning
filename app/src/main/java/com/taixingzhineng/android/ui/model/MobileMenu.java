package com.taixingzhineng.android.ui.model;

/**
 * Created by Administrator on 2017/12/16.
 * 预警
 */
public class MobileMenu {

    private String id;
    private String createDate;//
    private String updateDate;//
    private String parentId; // 父级编号
    private String parentIds; // 所有父级编号
    private String name; 	// 名称
    private String isShow; 	// 是否在菜单中显示（1：显示；0：不显示）

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }
}
