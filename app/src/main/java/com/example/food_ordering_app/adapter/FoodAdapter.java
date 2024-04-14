package com.example.food_ordering_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food_ordering_app.FoodDetailActivity;
import com.example.food_ordering_app.R;
import com.example.food_ordering_app.models.CartItem;
import com.example.food_ordering_app.models.Food;
import com.example.food_ordering_app.services.CartService;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Food> mFoods;
    private CartService cartService;
    private SharedPreferences sharedPreferences;
    public FoodAdapter(Context mContext, ArrayList<Food> mFoods) {
        this.mContext = mContext;
        this.mFoods = mFoods;
        this.cartService = new CartService(mContext);
        this.sharedPreferences = mContext.getSharedPreferences("sharedPrefKey",Context.MODE_PRIVATE);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageFood;
        private TextView mTextName;
        private TextView mTextDescription;
        private ImageView btnAddToCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageFood = itemView.findViewById(R.id.item_food_img);
            mTextName = itemView.findViewById(R.id.item_food_name);
            mTextDescription = itemView.findViewById(R.id.item_food_price);
            btnAddToCart = itemView.findViewById(R.id.add_to_cart_icon);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        Food food = mFoods.get(pos);
                        Intent i = new Intent(mContext, FoodDetailActivity.class);
                        i.putExtra("foodId",food.getId());
                        mContext.startActivity(i);
                    }
                }
            });
            btnAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        Food food = mFoods.get(pos);
                        CartItem item = new CartItem();
                        item.setImgURL(food.getImgURL());
                        item.setFoodName(food.getName());
                        item.setFoodId(food.getId());
                        item.setPrice(food.getPrice());
                        item.setQuantity(1);
                        item.setTotal(food.getPrice()*item.getQuantity());
                        cartService.getCartId(sharedPreferences.getString("userIdKey",null),item,null);
                    }
                }
            });
        }
    }



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
                .placeholder(R.drawable.admin_dish)
                .error(R.drawable.error)
                .into(holder.mImageFood);
        holder.mTextName.setText(food.getName());
        holder.mTextDescription.setText(Double.valueOf(food.getPrice()).toString());
    }

    @Override
    public int getItemCount() {
        return mFoods.size();
    }
}