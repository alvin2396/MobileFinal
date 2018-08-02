package skripsigame.skripsi.Activity;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import skripsigame.skripsi.R;

public class TopUpActivity extends AppCompatActivity {
    ImageButton tambah100,tambah200,tambah500,kurang100,kurang200,kurang500;
    TextView seratusribu, duaratusribu,limaratusribu,total;
    Integer seratus,duaratus,limaratus,totaltopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);
        seratus = 0;
        duaratus = 0;
        limaratus = 0;
        totaltopup = 0;
        total = (TextView) findViewById(R.id.totaltopup);
        seratusribu = (TextView) findViewById(R.id.seratus);
        duaratusribu = (TextView) findViewById(R.id.duaratus);
        limaratusribu = (TextView) findViewById(R.id.limaratus);
        tambah100 = (ImageButton) findViewById(R.id.tambah100);
        tambah200 = (ImageButton) findViewById(R.id.tambah200);
        tambah500 = (ImageButton) findViewById(R.id.tambah500);
        kurang100 = (ImageButton) findViewById(R.id.kurang100);
        kurang200 = (ImageButton) findViewById(R.id.kurang200);
        kurang500 = (ImageButton) findViewById(R.id.kurang500);

        tambah100.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                tambah100rb();
            }
        });
        tambah200.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                tambah200rb();
            }
        });
        tambah500.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                tambah500rb();
            }
        });
        kurang100.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                kurang100rb();
            }
        });
        kurang200.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                kurang200rb();
            }
        });
        kurang500.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                kurang500rb();
            }
        });
    }

    public void tambah100rb(){
        seratus += 1;
        totaltopup += 100000;
        Integer hasil = seratus;
        seratusribu.setText(String.valueOf(hasil));
        total.setText(String.valueOf(totaltopup));
    }
    public void tambah200rb(){
        duaratus += 1;
        totaltopup += 200000;
        Integer hasil = duaratus;
        duaratusribu.setText(String.valueOf(hasil));
        total.setText(String.valueOf(totaltopup));
    }
    public void tambah500rb(){
        limaratus += 1;
        totaltopup += 500000;
        Integer hasil = limaratus;
        limaratusribu.setText(String.valueOf(hasil));
        total.setText(String.valueOf(totaltopup));
    }
    public void kurang100rb(){
        if(seratus <= 0){
            seratus = 0;
            totaltopup -= 0;
        }
        else {
            seratus -= 1;
            totaltopup -= 100000;
        }
        Integer hasil = seratus;
        seratusribu.setText(String.valueOf(hasil));
        total.setText(String.valueOf(totaltopup));
    }
    public void kurang200rb(){
        if(duaratus <= 0){
            duaratus = 0;
            totaltopup -= 0;
        }
        else {
            duaratus -= 1;
            totaltopup -= 200000;
        }
        Integer hasil = duaratus;
        duaratusribu.setText(String.valueOf(hasil));
        total.setText(String.valueOf(totaltopup));
    }
    public void kurang500rb(){
        if(limaratus <= 0){
            limaratus = 0;
            totaltopup -= 0;
        }
        else {
            limaratus -= 1;
            totaltopup -= 500000;
        }
        Integer hasil = limaratus;
        limaratusribu.setText(String.valueOf(hasil));
        total.setText(String.valueOf(totaltopup));
    }
}
