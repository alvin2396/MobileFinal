package skripsigame.skripsi.Activity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import skripsigame.skripsi.R;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder>{

    private ArrayList<String> rvData;

    public PopularAdapter(ArrayList<String> inputData) {
        rvData = inputData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // di tutorial ini kita hanya menggunakan data String untuk tiap item
        public TextView judulgame;

        public ViewHolder(View v) {
            super(v);
            judulgame = (TextView) v.findViewById(R.id.judulgame);
        }
    }

    @Override
    public PopularAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // membuat view baru
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itempopular, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        PopularAdapter.ViewHolder vh = new PopularAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PopularAdapter.ViewHolder holder, int position) {
        // - mengambil elemen dari dataset (ArrayList) pada posisi tertentu
        // - mengeset isi view dengan elemen dari dataset tersebut
        final String name = rvData.get(position);
        holder.judulgame.setText(rvData.get(position));
    }

    @Override
    public int getItemCount() {
        // menghitung ukuran dataset / jumlah data yang ditampilkan di RecyclerView
        return rvData.size();
    }
}
