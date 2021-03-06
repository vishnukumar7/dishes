package com.app.dishes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.app.dishes.databinding.ActivityMainBinding;
import com.app.dishes.model.DishesResponse;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ApiInterface apiInterface;
    ActivityMainBinding binding;
    DishesImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        adapter = new DishesImageAdapter(this, new ArrayList<>());
        binding.viewPager.setAdapter(adapter);
        getData();
        binding.itemTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void getData() {
        Call<DishesResponse> call = apiInterface.getDishesList();
        call.enqueue(new Callback<DishesResponse>() {
            @Override
            public void onResponse(@NonNull Call<DishesResponse> call, @NonNull Response<DishesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DishesResponse data = response.body();
                    adapter.setDishImageUrl(response.body().getMoreImages());
                    adapter.notifyDataSetChanged();
                    binding.itemTitle.setText(data.getName());
                }
            }

            @Override
            public void onFailure(@NonNull Call<DishesResponse> call, @NonNull Throwable t) {

            }
        });
    }
}