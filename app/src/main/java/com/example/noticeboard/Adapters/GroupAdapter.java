package com.example.noticeboard.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noticeboard.Activities.GroupPostsActivity;
import com.example.noticeboard.ApiResponses.UserGroupResponse;
import com.example.noticeboard.R;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.MyViewHolder> {

    private Context context;
    private List<UserGroupResponse> groups;

    public GroupAdapter(Context context, List<UserGroupResponse> groups) {
        this.context = context;
        this.groups = groups;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_row, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.group_name.setText(groups.get(position).getGroupName());
        holder.admin.setText("Admin: " + groups.get(position).getAdmin());
        holder.joined_date.setText("Joined on: " + groups.get(position).getTimeOfJoining());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, GroupPostsActivity.class);
                i.putExtra("group_id", groups.get(position).getGroupId());
                context.startActivity(i);
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
