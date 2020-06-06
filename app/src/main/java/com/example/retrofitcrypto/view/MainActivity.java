package com.example.retrofitcrypto.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;

import com.example.retrofitcrypto.R;
import com.example.retrofitcrypto.adaptor.RecyclerViewAdaptor;
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
    RecyclerView recyclerView;
    RecyclerViewAdaptor recyclerViewAdaptor;

    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //https://api.nomics.com/v1/prices?key=22e5d080aaa594236172d8a53358051b

        Gson gson = new GsonBuilder().setLenient().create();//gson'ı oluşturuyor
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))//gelen gson'ı serialized namedeki gibi alacağını retrofite'de bildirmemiz gerekiyor
                .build();
        loadData();

    }

    private void loadData() {
        final CryptoAPI cryptoAPI = retrofit.create(CryptoAPI.class);//veriyi yükleyebilmek için interface'den obje üretmemiz lazım

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(cryptoAPI.getData()
                .subscribeOn(Schedulers.io())//hangi threat'te bu gözlemleme kayıt alma işlemlerinin yapılacağını
                .observeOn(AndroidSchedulers.mainThread())//aldığımız veriyi hangi threat'te gözlemleyeceğiz
                .subscribe(this::handleResponse));//ortaya çıkan veriyi nerde ele alacağız call kullanırken onResponse falan vardı burda da bu var


        //servis oluşmuş oldu artıl call ile veriyi çekebiliriz
       /* Call<List<CryptoModel>> call = cryptoAPI.getData();
        call.enqueue(new Callback<List<CryptoModel>>() {
            @Override
            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {
                if (response.isSuccessful()) {
                    List<CryptoModel> responseList = response.body();
                    cryptoModels = new ArrayList<>(responseList);
//recyclerview burda olması lazım çünkü call yaparak aldığımız verileri aldıktan sonra recyclerview'de göstereceğiz
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerViewAdaptor=new RecyclerViewAdaptor(cryptoModels);
                    recyclerView.setAdapter(recyclerViewAdaptor);
                }
            }

            @Override
            public void onFailure(Call<List<CryptoModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
*/

    }

    private void handleResponse(List<CryptoModel> cryptoModelList) {
        cryptoModels = new ArrayList<>(cryptoModelList);
//recyclerview burda olması lazım çünkü call yaparak aldığımız verileri aldıktan sonra recyclerview'de göstereceğiz
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerViewAdaptor=new RecyclerViewAdaptor(cryptoModels);
        recyclerView.setAdapter(recyclerViewAdaptor);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();//yukarıdaki activite bittiğinde eklenen tüm api call'larının hepsini temizleme işlemi bir kereden yapılır
    }
}
