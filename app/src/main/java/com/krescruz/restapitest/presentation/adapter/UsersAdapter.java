package com.krescruz.restapitest.presentation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.krescruz.restapitest.R;
import com.krescruz.restapitest.data.model.User;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    private List<User> users;
    private OnItemClickListener onItemClickListener;

    public UsersAdapter(List<User> mUsers) {
        users = mUsers;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
        return new UserViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {

        final User user = users.get(position);

        holder.tvUserName.setText(user.getUserName());
        holder.tvUserEmail.setText(user.getUserEmail());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onPostItemClicked(user);
                }
            }
        });

    }

    public void addUsers(List<User> users) {
        this.users.addAll(users);
        this.notifyDataSetChanged();
    }

    public void clear() {
        this.users.clear();
        this.notifyDataSetChanged();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onPostItemClicked(User postModel);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class UserViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.user_name)
        TextView tvUserName;
        @Bind(R.id.user_email)
        TextView tvUserEmail;

        public UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}