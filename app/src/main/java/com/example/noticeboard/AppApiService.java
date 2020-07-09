package com.example.noticeboard;

import com.example.noticeboard.ApiResponses.GeneralResponse;
import com.example.noticeboard.ApiResponses.LoginResponse;
import com.example.noticeboard.ApiResponses.MyPostsResponse;
import com.example.noticeboard.ApiResponses.PostsResponse;
import com.example.noticeboard.ApiResponses.SignupResponse;
import com.example.noticeboard.ApiResponses.UserGroupResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AppApiService {

    @FormUrlEncoded
    @POST("api/login/")
    Call<LoginResponse> userLogin(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/signup/")
    Call<SignupResponse> userSignup(@Field("username") String username, @Field("password") String password);

    @GET("api/get_user_posts/")
    Call<List<PostsResponse>> getRecentPosts(@Header("Authorization") String token);

    @GET("api/user_groups/")
    Call<List<UserGroupResponse>> userGroups(@Header("Authorization") String token);

    @GET("api/get_my_posts/")
    Call<List<MyPostsResponse>> getMyPosts(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("api/get_group_posts/")
    Call<List<PostsResponse>> getGroupPosts(@Header("Authorization") String token, @Field("group_id") Integer group_id);

    @FormUrlEncoded
    @POST("api/create_post/")
    Call<GeneralResponse> createPost(@Header("Authorization") String token, @Field("title") String title, @Field("content") String content);

    @FormUrlEncoded
    @POST("api/create_group/")
    Call<GeneralResponse> createGroup(@Header("Authorization") String token, @Field("group_name") String group_name);

    @FormUrlEncoded
    @POST("api/add_member/")
    Call<GeneralResponse> addMember(@Header("Authorization") String token, @Field("username") String username, @Field("group_id") Integer group_id);

    @FormUrlEncoded
    @POST("api/share_to_group/")
    Call<GeneralResponse> sharePost(@Header("Authorization") String token, @Field("post_id") Integer post_id, @Field("group_id") Integer group_id);
}
