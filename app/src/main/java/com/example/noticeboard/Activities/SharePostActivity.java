package com.example.noticeboard.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noticeboard.Adapters.SharePostAdapter;
import com.example.noticeboard.ApiResponses.UserGroupResponse;
import com.example.noticeboard.AppApiService;
import com.example.noticeboard.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.noticeboard.Constants.BASE_URL;

public class SharePostActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private static Retrofit retrofit = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_post);
        recyclerView = findViewById(R.id.group_share_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        Intent i = getIntent();
        connectAndGetApiData(i.getIntExtra("post_id", 0));
    }

    public void connectAndGetApiData(final Integer post_id) {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        AppApiService apiService = retrofit.create(AppApiService.class);
        SharedPreferences sharedpref = getSharedPreferences("mypref", 0);
        String token = sharedpref.getString("token", "");
        Call<List<UserGroupResponse>> call = apiService.userGroups("Token " + token);
        call.enqueue(new Callback<List<UserGroupResponse>>() {
            @Override
            public void onResponse(Call<List<UserGroupResponse>> call, Response<List<UserGroupResponse>> response) {
                if(response.isSuccessful()) {
                    List<UserGroupResponse> groups = response.body();
                    recyclerView.setAdapter(new SharePostAdapter(getBaseContext(), groups, post_id));
                }
                else {
                    Log.d("Error", "Something went wrong.");
                }
            }

            @Override
            public void onFailure(Call<List<UserGroupResponse>> call, Throwable t) {
                Log.d("Error", "Something went wrong. Api call failed.");
            }
        });
    }
}
