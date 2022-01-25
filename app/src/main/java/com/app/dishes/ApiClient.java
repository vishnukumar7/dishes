package com.app.dishes;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.library.BuildConfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.level(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .callTimeout(60, TimeUnit.SECONDS);

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            if (BuildConfig.DEBUG) {
                clientBuilder.addInterceptor(new LogJson2Interceptor());
            }

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://demo1256134.mockable.io/")
                    .client(clientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    @SuppressWarnings("LogNotTimber")
    public static class LogJson2Interceptor implements Interceptor {
        private static final String TAG = LogJson2Interceptor.class.getSimpleName();

        @NonNull
        @Override
        public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
            Request request = chain.request();

            Response response = chain.proceed(request);
            String rawJson = Objects.requireNonNull(response.body()).string();

            try {
                Object object = new JSONTokener(rawJson).nextValue();
// String jsonLog = object instanceof JSONObject
// ? ((JSONObject) object).toString(4)
// : ((JSONArray) object).toString(4);
                if (object instanceof JSONObject) {
                    int index = request.url().toString().lastIndexOf('/');
                    Log.d(TAG, String.format("%s <-- %d Response: %s", request.url().toString().substring(index),
                            response.code(),
                            ((JSONObject) object).toString(4)));
                }
            } catch (JSONException e) {
                e.printStackTrace();


            }

// Re-create the response before returning it because body can be read only once
            return response.newBuilder()
                    .body(ResponseBody.create(
                            Objects.requireNonNull(response.body()).contentType(),
                            rawJson
                    )).build();
        }
    }
}
