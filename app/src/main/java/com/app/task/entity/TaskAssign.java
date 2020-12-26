package com.app.task.entity;

import java.sql.Date;

public class TaskAssign {

    //任务ID
    private Integer id;
    //任务创建者用户ID
    private Integer creatorId;
    //被分配者用户ID
    private Integer assigneeId;
    private String task;
    private Date taskCreateTime;
    private Date taskFinishTime;

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

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
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

    @Override
    public String toString() {
        return "TaskAssign{" +
                "id=" + id +
                ", creatorId=" + creatorId +
                ", assigneeId=" + assigneeId +
                ", task='" + task + '\'' +
                ", taskCreateTime=" + taskCreateTime +
                ", taskFinishTime=" + taskFinishTime +
                '}';
    }
}
