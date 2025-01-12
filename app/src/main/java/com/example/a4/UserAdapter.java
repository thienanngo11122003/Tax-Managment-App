package com.example.a4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> userList;
    private Context context;
    private UserClickListener listener;

    public UserAdapter(List<User> userList, Context context, UserClickListener listener) {
        this.userList = userList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.nameTextView.setText(user.name);
        holder.phoneTextView.setText(user.phone);
        holder.cityTextView.setText(user.city);
        holder.statusTextView.setText(user.status);

        if (user.status.equals("AWAITED")) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.yellow));
        } else if (user.status.equals("FAILEDTOREACH")) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.red_light));
        } else if (user.status.equals("ONBOARDED")) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.green_light));
        } else if (user.status.equals("INPROCESS")) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.green_mid));
        } else if (user.status.equals("COMPLETED")) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.green_dark));
        } else if (user.status.equals("DENIED")) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.red));
        } else {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.gray)); // Default
        }


        holder.itemView.setOnClickListener(v -> listener.onUserClick(user));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public interface UserClickListener {
        void onUserClick(User user);
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, phoneTextView, cityTextView, statusTextView;

        public UserViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            phoneTextView = itemView.findViewById(R.id.phoneTextView);
            cityTextView = itemView.findViewById(R.id.cityTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
        }
    }
}
