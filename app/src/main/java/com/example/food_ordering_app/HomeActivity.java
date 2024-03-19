package com.example.food_ordering_app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food_ordering_app.models.Food;
import com.example.food_ordering_app.services.ServiceBuilder;
import com.example.food_ordering_app.services.foodService;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final Context context = this;
        class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
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
                    mTextDescription = itemView.findViewById(R.id.food_description);
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
                Glide.with(mContext).load(food.getImgURL()).placeholder(R.drawable.img_bg).error(R.drawable.ic_launcher_background).into(holder.mImageFood);
                holder.mTextName.setText(food.getName());
                holder.mTextDescription.setText(food.getDescription());
            }

            @Override
            public int getItemCount() {
                return mFoods.size();
            }
        }

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView_foodList);
        foodService foodService = ServiceBuilder.buildService(foodService.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Call<ArrayList<Food>> request = foodService.getIdeas();

        request.enqueue(new Callback<ArrayList<Food>>() {
            @Override
            public void onResponse(Call<ArrayList<Food>> request, Response<ArrayList<Food>> response) {
                if (response.isSuccessful()) {
                    recyclerView.setAdapter(new FoodAdapter(HomeActivity.this, response.body()));
                } else if (response.code() == 401) {
                    Toast.makeText(context, "Your session has expired", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Failed to retrieve items", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Food>> request, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(context, "A connection error occured", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Failed to retrieve items", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
