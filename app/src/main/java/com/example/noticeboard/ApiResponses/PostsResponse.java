package com.example.noticeboard.ApiResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static com.example.noticeboard.Constants.SHORT_POST_LENGTH;

public class PostsResponse {

    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("time_of_sharing")
    @Expose
    private String timeOfSharing;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("group_name")
    @Expose
    private String groupName;
    @SerializedName("group_id")
    @Expose
    private Integer groupId;
    @SerializedName("post_id")
    @Expose
    private Integer postId;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimeOfSharing() {
        return timeOfSharing;
    }

    public void setTimeOfSharing(String timeOfSharing) {
        this.timeOfSharing = timeOfSharing;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getShortContent() {
        if(content.length() > SHORT_POST_LENGTH) {
            return content.substring(0, SHORT_POST_LENGTH) + "...";
        }
        return content;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }
}
