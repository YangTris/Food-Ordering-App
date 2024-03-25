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

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    public FoodAdapter(Context mContext, ArrayList<Food> mFoods) {
        this.mContext = mContext;
        this.mFoods = mFoods;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageFood;
        private TextView mTextName;
        private TextView mTextDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageFood = itemView.findViewById(R.id.food_img);
            mTextName = itemView.findViewById(R.id.food_name);
            mTextDescription = itemView.findViewById(R.id.food_price);
        }
    }

    private Context mContext;
    private ArrayList<Food> mFoods;

    @NonNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View foodView = inflater.inflate(R.layout.food_items, parent, false);
        ViewHolder viewHolder = new ViewHolder(foodView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.ViewHolder holder, int position) {
        Food food = mFoods.get(position);
        Glide.with(mContext)
                .load(food.getImgURL())
                .placeholder(R.drawable.img_bg)
                .error(R.drawable.profile)
                .into(holder.mImageFood);
        holder.mTextName.setText(food.getName());
        holder.mTextDescription.setText(food.getDescription());
    }

    @Override
    public int getItemCount() {
        return mFoods.size();
    }
}