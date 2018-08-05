package skripsigame.skripsi.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import skripsigame.skripsi.ApiClient.ApiClient;
import skripsigame.skripsi.ApiClient.UserService;
import skripsigame.skripsi.Model.Transaksi;
import skripsigame.skripsi.R;

public class TopUpActivity extends AppCompatActivity {
    ImageButton tambah100,tambah200,tambah500,kurang100,kurang200,kurang500;
    TextView seratusribu, duaratusribu,limaratusribu,total;
    Integer seratus,duaratus,limaratus,totaltopup;
    String confirmationcode,token,email,type,userid,gettotal,status;
    Button confirmtopup;
    RadioButton banktf,paypal;
    AlertDialog alertDialog;
    AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);
        seratus = 0;
        duaratus = 0;
        limaratus = 0;
        totaltopup = 0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Top Up");
        banktf = (RadioButton)findViewById(R.id.radiobank);
        banktf.setChecked(true);
        paypal = (RadioButton)findViewById(R.id.radiopaypal);
        confirmationcode = GenerateRandomString.randomString(6);
        confirmtopup = (Button)findViewById(R.id.confirmtopup);
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
        initDialogBuilder();

        final Intent intent = getIntent();
        token = intent.getStringExtra("token");
        email = intent.getStringExtra("email");
        userid = intent.getStringExtra("id");
        type = "topup";
        status = "pending";



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



        confirmtopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gettotal = total.getText().toString();
                final UserService userService = ApiClient.getClient().create(UserService.class);
                Call<Transaksi> call = userService.topupcreateMobile("Bearer"+token,userid,gettotal,status,confirmationcode,type);
                call.enqueue(new Callback<Transaksi>() {
                    @Override
                    public void onResponse(Call<Transaksi> call, Response<Transaksi> response) {
                        try {
                            if(response.isSuccessful()){
                                Transaksi transaksi = response.body();
                                final String transaksi_id = transaksi.getId();
                                final String confirmation = transaksi.getConfirmation_code();
                                Intent intentconfirm = new Intent(TopUpActivity.this, KonfirmasiActivity.class);
                                intentconfirm.putExtra("token", token);
                                intentconfirm.putExtra("email", email);
                                intentconfirm.putExtra("confirmation", confirmation);
                                intentconfirm.putExtra("transaksi_id",transaksi_id);
                                intentconfirm.putExtra("tipe", type);
                                intentconfirm.putExtra("total",gettotal);
                                startActivity(intentconfirm);
                            }
                            else {
                                Toast.makeText(TopUpActivity.this, "Fail To Load API", Toast.LENGTH_SHORT).show();
                                alertDialogBuilder.setMessage("Connection Problem").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                                alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }
                        }catch (Exception e){
                            Toast.makeText(TopUpActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            alertDialogBuilder.setMessage("Connection Problem").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Transaksi> call, Throwable t) {
                        Toast.makeText(TopUpActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        alertDialogBuilder.setMessage("Connection Problem").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                });
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

    public static class GenerateRandomString {

        public static final String DATA = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        public static Random RANDOM = new Random();

        public static String randomString(int len) {
            StringBuilder sb = new StringBuilder(len);

            for (int i = 0; i < len; i++) {
                sb.append(DATA.charAt(RANDOM.nextInt(DATA.length())));
            }

            return sb.toString();
        }

    }

    private void initDialogBuilder() {
        alertDialogBuilder = new AlertDialog.Builder(this);
    }

    public boolean onSupportNavigateUp() {
        Intent intc = getIntent();
        String tokens  =intc.getStringExtra("token");
        String email = intc.getStringExtra("email");
        Intent intens = new Intent(TopUpActivity.this, Profile.class);
        intens.putExtra("email",email);
        intens.putExtra("token",tokens);
        startActivity(intens);
        finish();
        return true;
    }

}
