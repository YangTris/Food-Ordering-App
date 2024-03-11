package com.example.food_ordering_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.food_ordering_app.models.Food;
import com.example.food_ordering_app.services.foodService;
import com.example.food_ordering_app.services.ServiceBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class foodListActivity extends AppCompatActivity {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_food_list);
         setContentView(R.layout.food_list_recycleview);

        final Context context = this;

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle(getTitle());
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, foodCreateActivity.class);
//                context.startActivity(intent);
//            }
//        });

        final RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView_foodList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        assert recyclerView != null;

//        if (findViewById(R.id.idea_detail_container) != null) {
//            mTwoPane = true;
//        }

        foodService foodService = ServiceBuilder.buildService(foodService.class);
        Call<ArrayList<Food>> request = foodService.getIdeas();

        request.enqueue(new Callback<ArrayList<Food>>() {
            @Override
            public void onResponse(Call<ArrayList<Food>> request, Response<ArrayList<Food>> response) {
                if(response.isSuccessful()){
                    recyclerView.setAdapter(new FoodAdapter(foodListActivity.this,response.body()));

                } else if(response.code() == 401) {
                    Toast.makeText(context, "Your session has expired", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Failed to retrieve items", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Food>> request, Throwable t) {
                if (t instanceof IOException){
                    Toast.makeText(context, "A connection error occured", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Failed to retrieve items", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

//region Adapter Region
    public class FoodAdapter
            extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    public FoodAdapter(Context mContext, ArrayList<Food> mFoods) {
        this.mContext = mContext;
        this.mFoods = mFoods;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
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
            Glide.with(mContext)
                    .load(food.getImgURL())
                    .placeholder(R.drawable.img_bg)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.mImageFood);

            holder.mTextName.setText(food.getName());
            holder.mTextDescription.setText(food.getDescription());
        }

        @Override
        public int getItemCount() {
            return mFoods.size();
        }
    }
}
//        @Override
//        public void onBindViewHolder(final ViewHolder holder, int position) {
//            holder.mItem = mValues.get(position);
//            holder.mIdView.setText(Integer.toString(mValues.get(position).getId()));
//            holder.mContentView.setText(mValues.get(position).getName());

//            holder.mView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mTwoPane) {
//                        Bundle arguments = new Bundle();
//                        arguments.putInt(IdeaDetailFragment.ARG_ITEM_ID, holder.mItem.getId());
//                        IdeaDetailFragment fragment = new IdeaDetailFragment();
//                        fragment.setArguments(arguments);
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.idea_detail_container, fragment)
//                                .commit();
//                    } else {
//                        Context context = v.getContext();
//                        Intent intent = new Intent(context, IdeaDetailActivity.class);
//                        intent.putExtra(IdeaDetailFragment.ARG_ITEM_ID, holder.mItem.getId());
//
//                        context.startActivity(intent);
//                    }
//                }
//            });

//endregion

