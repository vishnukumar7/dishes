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
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        apiInterface=ApiClient.getClient().create(ApiInterface.class);
        adapter=new DishesImageAdapter(this,new ArrayList<>());
        binding.viewPager.setAdapter(adapter);
        getData();
    }

    private void getData(){
        Call<DishesResponse> call=apiInterface.getDishesList();
        call.enqueue(new Callback<DishesResponse>() {
            @Override
            public void onResponse(@NonNull Call<DishesResponse> call, @NonNull Response<DishesResponse> response) {
                if(response.isSuccessful() && response.body()!=null){
                    DishesResponse data=response.body();
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



    class DishesImageAdapter extends PagerAdapter{
        Context context;
        LayoutInflater inflater;
        List<String> dishImageUrl;

        DishesImageAdapter(Context context,List<String> dishImageUrl){
            this.context=context;
            this.dishImageUrl=dishImageUrl;
        }

        public void setDishImageUrl(List<String> dishImageUrl) {
            this.dishImageUrl = dishImageUrl;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            inflater= (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view=inflater.inflate(R.layout.image_dish_view,container,false);
            ImageView image=view.findViewById(R.id.image);
            Glide.with(MainActivity.this).load(dishImageUrl.get(position)).fitCenter().into(image);
            ((ViewPager) container).addView(view, 0);
            return view;
        }

        /**
         * Return the number of views available.
         */
        @Override
        public int getCount() {
            return dishImageUrl.size();
        }

        /**
         * Determines whether a page View is associated with a specific key object
         * as returned by {@link #instantiateItem(ViewGroup, int)}. This method is
         * required for a PagerAdapter to function properly.
         *
         * @param view   Page View to check for association with <code>object</code>
         * @param object Object to check for association with <code>view</code>
         * @return true if <code>view</code> is associated with the key object <code>object</code>
         */
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == ((View) object);
        }

        @Override
        public void destroyItem(ViewGroup arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }

    }
}