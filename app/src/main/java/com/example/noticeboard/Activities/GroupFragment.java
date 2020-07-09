package com.example.noticeboard.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noticeboard.Activities.CreateGroupActivity;
import com.example.noticeboard.Adapters.GroupAdapter;
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
import static com.example.noticeboard.Constants.USER_GROUP_DELAY;

public class GroupFragment extends Fragment {

    private RecyclerView recyclerView;
    private static Retrofit retrofit = null;
    private Handler handler;
    private Runnable runnable;
    private Button create_group;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_group, container, false);
        recyclerView = view.findViewById(R.id.group_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        connectAndGetApiData();

        handler = new Handler();
        runnable = new Runnable(){
            public void run(){
                connectAndGetApiData();
                handler.postDelayed(runnable, USER_GROUP_DELAY);
            }
        };

        create_group = view.findViewById(R.id.create_new_group);
        create_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), CreateGroupActivity.class);
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
        handler.postDelayed(runnable, USER_GROUP_DELAY);
        super.onResume();
    }


    public void connectAndGetApiData() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        AppApiService apiService = retrofit.create(AppApiService.class);
        SharedPreferences sharedpref = this.getActivity().getSharedPreferences("mypref", 0);
        String token = sharedpref.getString("token", "");
        Call<List<UserGroupResponse>> call = apiService.userGroups("Token " + token);
        call.enqueue(new Callback<List<UserGroupResponse>>() {
            @Override
            public void onResponse(Call<List<UserGroupResponse>> call, Response<List<UserGroupResponse>> response) {
                if(response.isSuccessful()) {
                    List<UserGroupResponse> groups = response.body();
                    Parcelable recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
                    recyclerView.setAdapter(new GroupAdapter(getContext(), groups));
                    recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
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