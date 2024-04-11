package com.example.food_ordering_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_ordering_app.EditFoodActivity;
import com.example.food_ordering_app.OrderDetailActivity;
import com.example.food_ordering_app.R;
import com.example.food_ordering_app.models.Food;
import com.example.food_ordering_app.models.Order;
import com.example.food_ordering_app.services.OrderService;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private Context mContext;
    private List<Order> mOrders;
    private OrderService orderService;

    public OrderAdapter(Context mContext, List<Order> mOrders) {
        this.mContext = mContext;
        this.mOrders = mOrders;
        this.orderService = new OrderService(mContext);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView orderId;
        private TextView total;
        private View delivering;
        private View delivered;

        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            orderId = itemView.findViewById(R.id.order_id);
            total = itemView.findViewById(R.id.total);
            delivering = itemView.findViewById(R.id.progress_delivering);
            delivered = itemView.findViewById(R.id.progress_delivered);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Order order = mOrders.get(pos);
                        Intent i = new Intent(mContext, OrderDetailActivity.class);
                        i.putExtra("orderId", order.getOrderId());
                        mContext.startActivity(i);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View orderView = inflater.inflate(R.layout.order_item, parent, false);
        OrderAdapter.ViewHolder viewHolder = new OrderAdapter.ViewHolder(orderView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = mOrders.get(position);
        holder.orderId.setText("Order Id: " + order.getOrderId());
        holder.total.setText("Total: " + order.getOrderTotal());
        if (order.getOrderStatus().equals("Delivering")) {
            holder.delivering.setBackgroundResource(R.drawable.delivery_done);
        }
        if (order.getOrderStatus().equals("Delivered")) {
            holder.delivering.setBackgroundResource(R.drawable.delivery_done);
            holder.delivered.setBackgroundResource(R.drawable.delivery_done);
        }
    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }
}
