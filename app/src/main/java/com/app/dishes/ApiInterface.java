package com.app.dishes;

import com.app.dishes.model.DishesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("dishesoftheday")
    Call<DishesResponse> getDishesList();

    @GET("dishes/{itemId}")
    Call<DishesResponse> getDishesItem(@Path("itemId") String itemId);


}
