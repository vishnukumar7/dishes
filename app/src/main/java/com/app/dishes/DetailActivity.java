package com.app.dishes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.app.dishes.databinding.ActivityDetailBinding;
import com.app.dishes.databinding.ActivityMainBinding;
import com.app.dishes.model.DishesResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    ApiInterface apiInterface;
    DishesImageAdapter adapter;
    ActivityDetailBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_detail);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        adapter = new DishesImageAdapter(this, new ArrayList<>());
        binding.viewPager.setAdapter(adapter);
        getData(getIntent().getStringExtra("item_id"));
    }

    private void getData(String item_id) {
        Call<DishesResponse> call = apiInterface.getDishesItem(item_id);
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
