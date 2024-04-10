package com.example.food_ordering_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_ordering_app.R;
import com.example.food_ordering_app.models.Food;
import com.example.food_ordering_app.models.Order;
import com.example.food_ordering_app.services.FoodService;
import com.example.food_ordering_app.services.OrderService;

import org.w3c.dom.Text;

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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.order_id);
            total = itemView.findViewById(R.id.total);
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
        holder.orderId.setText("Order No."+order.getOrderId());
        holder.total.setText("Total: "+order.getOrderTotal());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
