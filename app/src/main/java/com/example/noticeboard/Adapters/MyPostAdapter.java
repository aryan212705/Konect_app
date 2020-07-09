package com.example.noticeboard.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noticeboard.Activities.DisplayPost;
import com.example.noticeboard.ApiResponses.MyPostsResponse;
import com.example.noticeboard.R;
import com.example.noticeboard.Activities.SharePostActivity;

import org.w3c.dom.Text;

import java.util.List;

public class MyPostAdapter extends RecyclerView.Adapter<MyPostAdapter.MyViewHolder> {

    private Context context;
    private List<MyPostsResponse> posts;

    public MyPostAdapter(Context context, List<MyPostsResponse> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_post_row, parent, false);
        MyPostAdapter.MyViewHolder vh = new MyPostAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.title.setText(posts.get(position).getTitle());
        holder.content.setText(posts.get(position).getShortContent());
        holder.edited_on.setText("Posted by: " + posts.get(position).getTimeOfEditing());
        holder.posted_on.setText("Posted on: " + posts.get(position).getTimeOfPosting());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DisplayPost.class);
                i.putExtra("title", posts.get(position).getTitle());
                i.putExtra("content", posts.get(position).getContent());
                i.putExtra("author", posts.get(position).getAuthor());
                i.putExtra("posted_on", posts.get(position).getTimeOfPosting());
                i.putExtra("edited_on", "Last edited on: " + posts.get(position).getTimeOfEditing());
                context.startActivity(i);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i = new Intent(context, SharePostActivity.class);
                i.putExtra("post_id", posts.get(position).getPostId());
                context.startActivity(i);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title, content, edited_on, posted_on;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.my_post_title);
            content = itemView.findViewById(R.id.my_post_content);
            edited_on = itemView.findViewById(R.id.last_edited_time);
            posted_on = itemView.findViewById(R.id.my_posted_time);
        }
    }
}
