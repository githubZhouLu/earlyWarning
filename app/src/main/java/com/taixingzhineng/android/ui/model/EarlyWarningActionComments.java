package com.taixingzhineng.android.ui.model;

/**
 * Created by Administrator on 2017/12/16.
 * 预警
 */
public class EarlyWarningActionComments {

    private String id;
    private String cuserId;		// 用户id
    private String cuserName;//用户名称
    private String comments;		// 评论内容
    private String commentId;		// 评论id
    private String titleId;		// 标题id
    private String adjustFlag;		// 调整状态(0表示未调整，1表示调整)
    private String commentTime;		// 评论时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCuserId() {
        return cuserId;
    }

    public void setCuserId(String cuserId) {
        this.cuserId = cuserId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }

    public String getAdjustFlag() {
        return adjustFlag;
    }

    public void setAdjustFlag(String adjustFlag) {
        this.adjustFlag = adjustFlag;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getCuserName() {
        return cuserName;
    }

    public void setCuserName(String cuserName) {
        this.cuserName = cuserName;
    }
}
