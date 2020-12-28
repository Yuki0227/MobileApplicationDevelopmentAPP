package com.app.task.entity;

import java.sql.Date;

public class TaskAssign {

    //任务ID
    private Integer id;
    //任务创建者用户ID
    private Integer creatorId;
    //被分配者用户ID
    private Integer assigneeId;
    //任务的标题
    private String taskTitle;
    //任务的具体内容
    private String taskContent;
    private Date taskCreateTime;
    private Date taskFinishTime;
    //用于标记该任务是否完成,完成则为1,未完成则为0
    private Integer status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(Integer assigneeId) {
        this.assigneeId = assigneeId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public Date getTaskCreateTime() {
        return taskCreateTime;
    }

    public void setTaskCreateTime(Date taskCreateTime) {
        this.taskCreateTime = taskCreateTime;
    }

    public Date getTaskFinishTime() {
        return taskFinishTime;
    }

    public void setTaskFinishTime(Date taskFinishTime) {
        this.taskFinishTime = taskFinishTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TaskAssign{" +
                "id=" + id +
                ", creatorId=" + creatorId +
                ", assigneeId=" + assigneeId +
                ", taskTitle='" + taskTitle + '\'' +
                ", taskContent='" + taskContent + '\'' +
                ", taskCreateTime=" + taskCreateTime +
                ", taskFinishTime=" + taskFinishTime +
                ", status=" + status +
                '}';
    }
}
