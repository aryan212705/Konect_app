package com.example.noticeboard.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.noticeboard.Activities.CreatePostActivity;
import com.example.noticeboard.Adapters.MyPostAdapter;
import com.example.noticeboard.ApiResponses.MyPostsResponse;
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


public class MyPostFragment extends Fragment {

    private RecyclerView recyclerView;
    private static Retrofit retrofit = null;
    private Handler handler;
    private Runnable runnable;
    private Button create_post;
    private Parcelable recyclerViewState;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_recent_posts, container, false);
        recyclerView = view.findViewById(R.id.recent_posts_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        connectAndGetApiData();

        handler = new Handler();
        runnable = new Runnable(){
            public void run(){
                connectAndGetApiData();
                handler.postDelayed(runnable, POSTS_DELAY);
            }
        };

        create_post = view.findViewById(R.id.create_new_post);
        create_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), CreatePostActivity.class);
                startActivity(i);
            }
        });

        return view;
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

    public void connectAndGetApiData() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        AppApiService apiService = retrofit.create(AppApiService.class);
        SharedPreferences sharedpref = this.getActivity().getSharedPreferences("mypref", 0);
        String token = sharedpref.getString("token", "");
        Call<List<MyPostsResponse>> call = apiService.getMyPosts("Token " + token);
        call.enqueue(new Callback<List<MyPostsResponse>>() {
            @Override
            public void onResponse(Call<List<MyPostsResponse>> call, Response<List<MyPostsResponse>> response) {
                if(response.isSuccessful()) {
                    List<MyPostsResponse> posts = response.body();
                    recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
                    recyclerView.setAdapter(new MyPostAdapter(getContext(), posts));
                    recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                }
                else {
                    Log.d("Error", "Something went wrong.");
                }
            }

            @Override
            public void onFailure(Call<List<MyPostsResponse>> call, Throwable t) {
                Log.d("Error", "Something went wrong. Api call failed.");
            }
        });
    }
}
