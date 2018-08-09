package skripsigame.skripsi.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import skripsigame.skripsi.ApiClient.ApiClient;
import skripsigame.skripsi.ApiClient.UserService;
import skripsigame.skripsi.Model.Transaksi;
import skripsigame.skripsi.R;

public class KonfirmasiActivity extends AppCompatActivity {

    String email,token,confirmation,transaksi_id,total,tipe;
    Button btnconfirm;
    TextView transaksi_id_topup,tvtipe,tvtotal,tvconfirmationcode;
    EditText confirminput;
    AlertDialog alertDialog;
    AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        token = intent.getStringExtra("token");
        transaksi_id = intent.getStringExtra("transaksi_id");
        confirmation = intent.getStringExtra("confirmation");
        total = intent.getStringExtra("total");
        tipe = intent.getStringExtra("tipe");
        btnconfirm = (Button)findViewById(R.id.btnconfirmtopup);
        transaksi_id_topup = (TextView)findViewById(R.id.transaksi_id_topup);
        transaksi_id_topup.setText(transaksi_id);
        tvtipe = (TextView)findViewById(R.id.tipe_transaksi);
        tvtipe.setText(tipe);
        tvconfirmationcode = (TextView)findViewById(R.id.confirmation_code);
        tvconfirmationcode.setText(confirmation);
        tvtotal = (TextView)findViewById(R.id.total_transaksi);
        tvtotal.setText(total);
        confirminput = (EditText)findViewById(R.id.confirmcode);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Top Up Confirmation");
        initDialogBuilder();

        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(confirminput.getText().toString().equals(confirmation)){
                    try{
                        final UserService userService = ApiClient.getClient().create(UserService.class);
                        Call<Transaksi> call = userService.validatecode("Bearer"+token,email,transaksi_id,confirmation);
                        call.enqueue(new Callback<Transaksi>() {
                            @Override
                            public void onResponse(Call<Transaksi> call, Response<Transaksi> response) {
                                if(response.isSuccessful()){
                                    Intent intenttoprofil = new Intent(KonfirmasiActivity.this,Profile.class);
                                    intenttoprofil.putExtra("token",token);
                                    intenttoprofil.putExtra("email", email);
                                    Toast.makeText(KonfirmasiActivity.this, "Top Up Success", Toast.LENGTH_SHORT).show();
                                    startActivity(intenttoprofil);
                                }
                                else {
                                    Toast.makeText(KonfirmasiActivity.this, "Response Failed", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(KonfirmasiActivity.this, "Fail to Load API", Toast.LENGTH_SHORT).show();
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
                    }catch (Exception e){
                        Toast.makeText(KonfirmasiActivity.this, "Failed", Toast.LENGTH_SHORT).show();
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
                else {
                    Toast.makeText(KonfirmasiActivity.this, "Invalid Code", Toast.LENGTH_SHORT).show();
                    alertDialogBuilder.setMessage("Enter a valid code").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });


    }

    private void initDialogBuilder() {
        alertDialogBuilder = new AlertDialog.Builder(this);
    }

    public boolean onSupportNavigateUp() {
        Intent intens = new Intent(KonfirmasiActivity.this, Profile.class);
        intens.putExtra("email",email);
        intens.putExtra("token",token);
        startActivity(intens);
        finish();
        return true;
    }

}
