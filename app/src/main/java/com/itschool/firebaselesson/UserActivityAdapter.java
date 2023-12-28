package com.itschool.firebaselesson;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserActivityAdapter extends RecyclerView.Adapter<UserActivityAdapter.UserViewHolder> {
    private List<User> users = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;

    public UserActivityAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(inflater.inflate(R.layout.user_view_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.update(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void update(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView textId, textName, textBio;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textId = itemView.findViewById(R.id.text_id);
            textName = itemView.findViewById(R.id.text_name);
            textBio = itemView.findViewById(R.id.text_bio);
        }

        public void update(User user) {
            textId.setText(user.getId());
            textName.setText(user.getName());
            textBio.setText(user.getBio());
        }
    }
}
