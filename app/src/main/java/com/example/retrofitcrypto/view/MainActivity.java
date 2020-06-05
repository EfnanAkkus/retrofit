package com.example.retrofitcrypto.view;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;

import com.example.retrofitcrypto.R;
import com.example.retrofitcrypto.model.CryptoModel;
import com.example.retrofitcrypto.service.CryptoAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<CryptoModel> cryptoModels;
    private String BASE_URL = "https://api.nomics.com/v1/";
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //https://api.nomics.com/v1/prices?key=22e5d080aaa594236172d8a53358051b

        Gson gson = new GsonBuilder().setLenient().create();//gson'ı oluşturuyor
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))//gelen gson'ı serialized namedeki gibi alacağını retrofite'de bildirmemiz gerekiyor
                .build();
        loadData();

    }

    private void loadData() {
        CryptoAPI cryptoAPI = retrofit.create(CryptoAPI.class);//veriyi yükleyebilmek için interface'den obje üretmemiz lazım
        //servis oluşmuş oldu artıl call ile veriyi çekebiliriz
        Call<List<CryptoModel>> call = cryptoAPI.getData();
        call.enqueue(new Callback<List<CryptoModel>>() {
            @Override
            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {
                if (response.isSuccessful()) {
                    List<CryptoModel> responseList = response.body();
                    cryptoModels=new ArrayList<>(responseList);

                }
            }

            @Override
            public void onFailure(Call<List<CryptoModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }


}
