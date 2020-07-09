package com.example.noticeboard.ApiResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static com.example.noticeboard.Constants.SHORT_POST_LENGTH;

public class MyPostsResponse {

    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("time_of_posting")
    @Expose
    private String timeOfPosting;
    @SerializedName("time_of_editing")
    @Expose
    private String timeOfEditing;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimeOfPosting() {
        return timeOfPosting;
    }

    public void setTimeOfPosting(String timeOfPosting) {
        this.timeOfPosting = timeOfPosting;
    }

    public String getTimeOfEditing() {
        return timeOfEditing;
    }

    public void setTimeOfEditing(String timeOfEditing) {
        this.timeOfEditing = timeOfEditing;
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
