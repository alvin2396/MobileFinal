package skripsigame.skripsi.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import skripsigame.skripsi.R;

public class KonfirmasiActivity extends AppCompatActivity {

    String email,token,confirmation,transaksi_id,total,tipe;
    Button btnconfirm;
    TextView transaksi_id_topup,tvtipe,tvtotal,tvconfirmationcode;
    EditText confirminput;

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



    }
}
