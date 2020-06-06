package com.example.retrofitcrypto.service;

import com.example.retrofitcrypto.model.CryptoModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoAPI {

    //GET(sunucudan veri almak için kull.), POST(sunucuya veri yazmak için kull.), UPDATE, DELETE
    //GET--> =22e5d080aaa594236172d8a53358051b
    //BASE_URL--> https://api.nomics.com/v1/prices?key

    @GET("https://api.nomics.com/v1/prices?key")
//GET işlemini hangi metod ile yapmamız gerektiğini söylüyoruz
   // Call<List<CryptoModel>> getData();//bir liste içinde crypto model gelecek
    Observable<List<CryptoModel>> getData();
}
