package skripsigame.skripsi.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import skripsigame.skripsi.R;

public class PopularActivity extends AppCompatActivity {

    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> dataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular);

        dataSet = new ArrayList<>();
        initDataset();

        rvView = (RecyclerView) findViewById(R.id.popular);
        rvView.setHasFixedSize(true);

        /**
         * Kita menggunakan LinearLayoutManager untuk list standar
         * yang hanya berisi daftar item
         * disusun dari atas ke bawah
         */
        layoutManager = new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);

        adapter = new PopularAdapter(dataSet);
        rvView.setAdapter(adapter);

    }
    private void initDataset(){

        /**
         * Tambahkan item ke dataset
         * dalam prakteknya bisa bermacam2
         * tidak hanya String seperti di kasus ini
         */
        dataSet.add("Judul Game 1");
        dataSet.add("Judul Game 2");
        dataSet.add("Judul Game 3");
        dataSet.add("Judul Game 4");
        dataSet.add("Judul Game 5");
        dataSet.add("Judul Game 6");
        dataSet.add("Judul Game 7");
        dataSet.add("Judul Game 8");
        dataSet.add("Judul Game 9");
        dataSet.add("Judul Game 10");

    }
}
