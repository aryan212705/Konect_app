package com.example.noticeboard.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.noticeboard.Adapters.PostAdapter;
import com.example.noticeboard.ApiResponses.PostsResponse;
import com.example.noticeboard.AppApiService;
import com.example.noticeboard.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.noticeboard.Constants.BASE_URL;
import static com.example.noticeboard.Constants.POSTS_DELAY;

public class GroupPostsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private static Retrofit retrofit = null;
    private Handler handler;
    private Runnable runnable;
    private Button add_member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recent_posts);

        add_member = findViewById(R.id.create_new_post);
        add_member.setText("+Add new member");

        recyclerView = findViewById(R.id.recent_posts_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        Intent i = getIntent();
        final Integer group_id = i.getIntExtra("group_id", 0);
        connectAndGetApiData(group_id);

        handler = new Handler();
        runnable = new Runnable(){
            public void run(){
                connectAndGetApiData(group_id);
                handler.postDelayed(runnable, POSTS_DELAY);
            }
        };

        add_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddGroupMembers.class);
                intent.putExtra("group_id", group_id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    @Override
    public void onResume() {
        handler.postDelayed(runnable, POSTS_DELAY);
        super.onResume();
    }

    public void connectAndGetApiData(Integer group_id) {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        AppApiService apiService = retrofit.create(AppApiService.class);
        SharedPreferences sharedpref = getSharedPreferences("mypref", 0);
        String token = sharedpref.getString("token", "");
        Call<List<PostsResponse>> call = apiService.getGroupPosts("Token " + token, group_id);
        call.enqueue(new Callback<List<PostsResponse>>() {
            @Override
            public void onResponse(Call<List<PostsResponse>> call, Response<List<PostsResponse>> response) {
                if(response.isSuccessful()) {
                    List<PostsResponse> posts = response.body();
                    Parcelable recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
                    recyclerView.setAdapter(new PostAdapter(GroupPostsActivity.this, posts));
                    recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                }
                else {
                    Log.d("Error", "Something went wrong.");
                }
            }

            @Override
            public void onFailure(Call<List<PostsResponse>> call, Throwable t) {
                Log.d("Error", "Something went wrong. Api call failed.");
            }
        });
    }
}
