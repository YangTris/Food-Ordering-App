package com.example.food_ordering_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food_ordering_app.R;
import com.example.food_ordering_app.models.Food;

import java.util.ArrayList;

public class AdminFoodAdapter extends RecyclerView.Adapter<AdminFoodAdapter.ViewHolder> {
    private ArrayList<Food> mFoods;

    public AdminFoodAdapter(ArrayList<Food> mFoods) {
        this.mFoods = mFoods;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageFood;
        private TextView mTextName;
        private TextView mTextPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageFood = itemView.findViewById(R.id.food_img);
            mTextName = itemView.findViewById(R.id.food_name);
            mTextPrice = itemView.findViewById(R.id.food_price);
        }
    }


    @NonNull
    @Override
    public AdminFoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View foodView = inflater.inflate(R.layout.admin_food_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(foodView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdminFoodAdapter.ViewHolder holder, int position) {
        Food food = mFoods.get(position);
//        Glide.with(mContext)
//                .load(food.getImgURL())
//                .placeholder(R.drawable.category_breakfast)
//                .error(R.drawable.image_holder)
//                .into(holder.mImageFood);
        holder.mTextName.setText(food.getName());
        holder.mTextPrice.setText(String.valueOf(food.getPrice()));
    }

    @Override
    public int getItemCount() {
        return mFoods.size();
    }
}