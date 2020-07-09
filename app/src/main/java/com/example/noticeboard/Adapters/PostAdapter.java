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

import com.example.noticeboard.Activities.DisplayPost;
import com.example.noticeboard.ApiResponses.PostsResponse;
import com.example.noticeboard.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    private Context context;
    private List<PostsResponse> posts;

    public PostAdapter(Context context, List<PostsResponse> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row, parent, false);
        PostAdapter.MyViewHolder vh = new PostAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.title.setText(posts.get(position).getTitle());
        holder.content.setText(posts.get(position).getShortContent());
        holder.author.setText("Posted by: " + posts.get(position).getAuthor());
        holder.time.setText("Posted on: " + posts.get(position).getTimeOfSharing());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DisplayPost.class);
                i.putExtra("title", posts.get(position).getTitle());
                i.putExtra("content", posts.get(position).getContent());
                i.putExtra("author", posts.get(position).getAuthor());
                i.putExtra("posted_on", posts.get(position).getTimeOfSharing());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title, content, author, time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.post_title);
            content = itemView.findViewById(R.id.post_content);
            author = itemView.findViewById(R.id.post_author);
            time = itemView.findViewById(R.id.posted_time);
        }
    }
}
