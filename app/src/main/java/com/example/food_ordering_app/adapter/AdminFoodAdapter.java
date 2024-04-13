package com.example.food_ordering_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food_ordering_app.EditFoodActivity;
import com.example.food_ordering_app.R;
import com.example.food_ordering_app.models.Food;
import com.example.food_ordering_app.services.FoodService;

import java.util.List;

public class AdminFoodAdapter extends RecyclerView.Adapter<AdminFoodAdapter.ViewHolder> {

    private Context mContext;
    private List<Food> mFoods;
    private FoodService foodService;

    public AdminFoodAdapter(Context context, List<Food> mFoods) {
        this.mContext = context;
        this.mFoods = mFoods;
        this.foodService = new FoodService(context);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageFood;
        private TextView mTextName;
        private TextView mTextPrice;
        private ImageView mDeleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageFood = itemView.findViewById(R.id.item_food_img);
            mTextName = itemView.findViewById(R.id.item_food_name);
            mTextPrice = itemView.findViewById(R.id.item_food_price);
            mDeleteButton= itemView.findViewById(R.id.admin_delete);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        Food food = mFoods.get(pos);
                        Intent i = new Intent(mContext, EditFoodActivity.class);
                        i.putExtra("foodId",food.getId());
                        i.putExtra("imgUrl",food.getImgURL());
                        mContext.startActivity(i);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public AdminFoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View foodView = inflater.inflate(R.layout.admin_food_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(foodView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdminFoodAdapter.ViewHolder holder, int position) {
        Food food = mFoods.get(position);
        Glide.with(mContext).load(food.getImgURL()).placeholder(R.drawable.category_foods).error(R.drawable.error).into(holder.mImageFood);
        holder.mTextName.setText(food.getName());
        holder.mTextPrice.setText(String.valueOf(food.getPrice()));

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
        return mFoods.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public void removeItem(int position) {
        foodService.deleteFood(mFoods.get(position).getId());
        mFoods.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }
}