package com.example.retrofitcrypto.model;

import com.google.gson.annotations.SerializedName;

public class CryptoModel {
    @SerializedName("currency")//parse yapacagımız veri ile ismi birebir aynı olmalı
    public String currency;
    @SerializedName("price")
    public String price;

}
