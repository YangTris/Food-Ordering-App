package com.example.food_ordering_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food_ordering_app.EditFoodActivity;
import com.example.food_ordering_app.EditProfileActivity;
import com.example.food_ordering_app.ProfileActivity;
import com.example.food_ordering_app.R;
import com.example.food_ordering_app.models.Food;
import com.example.food_ordering_app.models.User;
import com.example.food_ordering_app.services.UserService;

import java.util.List;

public class AdminUserAdapter extends RecyclerView.Adapter<AdminUserAdapter.ViewHolder> {

    private Context mContext;
    private List<User> mUsers;
    private UserService userService;

    public AdminUserAdapter(Context context, List<User> mUsers) {
        this.mContext = context;
        this.mUsers = mUsers;
        this.userService = new UserService(context);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageUser;
        private TextView mUserName;
        private TextView mUserPhone;
        private ImageView mDeleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageUser = itemView.findViewById(R.id.item_customer_img);
            mUserName = itemView.findViewById(R.id.item_customer_name);
            mUserPhone = itemView.findViewById(R.id.item_customer_phone);
            mDeleteButton = itemView.findViewById(R.id.admin_customer_delete);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        User user = mUsers.get(pos);
                        Intent i = new Intent(mContext, EditProfileActivity.class);
                        i.putExtra("userId", user.getUserId());
                        i.putExtra("userImg",user.getUserImg());
                        i.putExtra("password",user.getPassword());
                        i.putExtra("address",user.getAddress());
                        mContext.startActivity(i);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public AdminUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View userView = inflater.inflate(R.layout.admin_user_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(userView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdminUserAdapter.ViewHolder holder, int position) {
        User user = mUsers.get(position);
        Glide.with(mContext).load(user.getUserImg()).error(R.drawable.error).into(holder.mImageUser);
        holder.mUserName.setText(user.getName());
        holder.mUserPhone.setText(String.valueOf(user.getPhone()));
        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    removeItem(adapterPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public void removeItem(int position) {
        userService.deleteUser(mUsers.get(position).getUserId());
        mUsers.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }
}