package com.example.noticeboard.ApiResponses;

import com.google.gson.annotations.SerializedName;

public class SignupResponse {

    @SerializedName("token")
    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
