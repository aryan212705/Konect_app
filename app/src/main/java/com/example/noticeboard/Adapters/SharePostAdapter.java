package com.example.noticeboard.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noticeboard.Activities.HomeActivity;
import com.example.noticeboard.ApiResponses.GeneralResponse;
import com.example.noticeboard.ApiResponses.UserGroupResponse;
import com.example.noticeboard.AppApiService;
import com.example.noticeboard.Activities.HomeActivity;
import com.example.noticeboard.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.noticeboard.Constants.BASE_URL;

public class SharePostAdapter extends RecyclerView.Adapter<SharePostAdapter.MyViewHolder> {

    private Context context;
    private Integer post_id;
    private List<UserGroupResponse> groups;
    private static Retrofit retrofit = null;

    public SharePostAdapter(Context context, List<UserGroupResponse> groups, Integer post_id) {
        this.context = context;
        this.groups = groups;
        this.post_id = post_id;
    }

    @NonNull
    @Override
    public SharePostAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_row, parent, false);
        SharePostAdapter.MyViewHolder vh = new SharePostAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull SharePostAdapter.MyViewHolder holder, final int position) {
        holder.group_name.setText(groups.get(position).getGroupName());
        holder.admin.setText("Admin: " + groups.get(position).getAdmin());
        holder.joined_date.setText("Joined on: " + groups.get(position).getTimeOfJoining());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectAndGetApiData(groups.get(position).getGroupId());
            }
        });

    }

    public void connectAndGetApiData(Integer group_id) {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        AppApiService apiService = retrofit.create(AppApiService.class);
        SharedPreferences sharedpref = context.getSharedPreferences("mypref", 0);
        String token = "Token " + sharedpref.getString("token", "");
        Log.d("ERROR", token + "ID: "  + group_id + post_id);
        Call<GeneralResponse> call = apiService.sharePost(token, post_id, group_id);
        call.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                if(response.isSuccessful()) {
                    Intent i = new Intent(context, HomeActivity.class);
                    i.putExtra("tab", 2);
                    context.startActivity(i);
                    Toast.makeText(context, "Post shared successfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.d("ERROR", "Something went wrong. Try again later.");
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                Log.d("ERROR", "Something went wrong. Try again later.");
            }
        });
    }


    @Override
    public int getItemCount() {
        return groups.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView group_name, admin, joined_date;

        public MyViewHolder(View itemView) {
            super(itemView);

            group_name = itemView.findViewById(R.id.group_name);
            admin = itemView.findViewById(R.id.group_admin);
            joined_date = itemView.findViewById(R.id.group_joined_date);
        }
    }
}
