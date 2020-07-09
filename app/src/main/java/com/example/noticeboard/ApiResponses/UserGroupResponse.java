package com.example.noticeboard.ApiResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserGroupResponse {

    @SerializedName("group_name")
    @Expose
    private String groupName;
    @SerializedName("time_of_joining")
    @Expose
    private String timeOfJoining;
    @SerializedName("is_admin")
    @Expose
    private Boolean isAdmin;
    @SerializedName("group_id")
    @Expose
    private Integer groupId;
    @SerializedName("admin")
    @Expose
    private String admin;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTimeOfJoining() {
        return timeOfJoining;
    }

    public void setTimeOfJoining(String timeOfJoining) {
        this.timeOfJoining = timeOfJoining;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
}
