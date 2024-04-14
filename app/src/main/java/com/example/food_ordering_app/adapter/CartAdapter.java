package com.example.food_ordering_app.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food_ordering_app.CartActivity;
import com.example.food_ordering_app.R;
import com.example.food_ordering_app.models.CartItem;
import com.example.food_ordering_app.services.CartService;

import java.util.List;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private Context mContext;
    private List<CartItem> mItems;
    private TextView txtTotal;
    private CartService cartService;
    private SharedPreferences sharedPreferences;

    public CartAdapter(Context mContext, List<CartItem> mItems, TextView txtTotal) {
        this.mContext = mContext;
        this.mItems = mItems;
        this.txtTotal = txtTotal;
        txtTotal.setText(calculateTotal());
        cartService = new CartService(mContext);
        sharedPreferences = mContext.getSharedPreferences("sharedPrefKey", Context.MODE_PRIVATE);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImage;
        private TextView mFoodName;
        private TextView mQuantity;
        private TextView mFoodPrice;
        private ImageView btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.cart_food_img);
            mFoodName = itemView.findViewById(R.id.cart_food_name);
            mQuantity = itemView.findViewById(R.id.cart_food_quantity);
            mFoodPrice = itemView.findViewById(R.id.cart_food_price);
            btnDelete = itemView.findViewById(R.id.cart_item_delete);
            btnDelete.setVisibility(View.INVISIBLE);
            if (mContext instanceof CartActivity) {
                btnDelete.setVisibility(View.VISIBLE);
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            CartItem item = mItems.get(pos);
                            cartService.deleteCartItem(sharedPreferences.getString("userIdKey", null), item.getFoodId());
                            mItems.remove(pos);
                            txtTotal.setText(calculateTotal().toString());
                            notifyItemRemoved(pos);
                            notifyItemRangeChanged(pos, getItemCount());
                        }
                    }
                });
            }
        }
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View cartView = inflater.inflate(R.layout.cart_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(cartView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        CartItem item = mItems.get(position);
        Glide.with(mContext)
                .load(item.getImgURL())
                .placeholder(R.drawable.admin_dish)
                .error(R.drawable.error)
                .into(holder.mImage);
        holder.mFoodName.setText(item.getFoodName());
        holder.mQuantity.setText("Quantity: " + item.getQuantity());
        holder.mFoodPrice.setText("Price: " + item.getPrice());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private String calculateTotal() {
        Double total = 0.0;
        for (CartItem item : mItems) {
            total += item.getTotal();
        }
        return "Total Price : " + total;
    }
}
