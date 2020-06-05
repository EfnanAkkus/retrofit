package com.example.retrofitcrypto.adaptor;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.retrofitcrypto.R;
import com.example.retrofitcrypto.model.CryptoModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdaptor extends RecyclerView.Adapter<RecyclerViewAdaptor.RowHolder> {
    private ArrayList<CryptoModel> cryptoList;
    private String[] colors={"#f01a1a", "#4f6742", "#0800b0", "#7f4870", "#ff4500", "#38a1db", "#f20b8e", "#10c891"};

    public RecyclerViewAdaptor(ArrayList<CryptoModel> cryptoList) {//her RecyclerViewAdaptor sınıftan obje oluşturulduğunda
        // bir tane cryptoList bir değişkene eşitlenir
        this.cryptoList = cryptoList;
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflater'lar farklı xml'leri sınıfa bağlamamızda yardımcı oluyor

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_layout, parent, false);
        return new RowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {//görünümleri adaptore bağlıyoruz
        holder.bind(cryptoList.get(position), colors, position);
    }

    @Override
    public int getItemCount() {
        return cryptoList.size();
    }

    public class RowHolder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textPrice;

        public RowHolder(@NonNull View itemView) {
            super(itemView);


        }

        public void bind(CryptoModel cryptoModel, String[] colors, Integer position) {//buradaki cryptoModel'i onBindView'dan alacağız
            itemView.setBackgroundColor(Color.parseColor(colors[position % 8]));
            textName = itemView.findViewById(R.id.text_name);
            textPrice = itemView.findViewById(R.id.text_price);
            textName.setText(cryptoModel.currency);
            textPrice.setText(cryptoModel.price);

        }
    }
}
