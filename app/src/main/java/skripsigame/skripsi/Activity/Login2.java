package skripsigame.skripsi.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import skripsigame.skripsi.Model.User;
import skripsigame.skripsi.R;

public class Login2 extends AppCompatActivity {
    EditText txtemail, txtpassword;
    Button btnlogin;
    Context mcontext;
    TextView tvregister;
    AlertDialog alertDialog;
    AlertDialog.Builder alertDialogBuilder;
    String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        mcontext = this;
        inicomponent();
        initDialogBuilder();


    }

    public void inicomponent(){
        txtpassword = (EditText)findViewById(R.id.password);
        txtemail = (EditText)findViewById(R.id.email);
        btnlogin = (Button)findViewById(R.id.login);
        tvregister = (TextView)findViewById(R.id.register);
        tvregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mcontext,Register.class));
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isvalid = true;
                if(TextUtils.isEmpty(txtemail.getText())){
                    isvalid = false;
                    txtemail.setError("Email required");
                }
                else if(TextUtils.isEmpty(txtpassword.getText())){
                    isvalid = false;
                    txtpassword.setError("Password required");
                }
                if(TextUtils.isEmpty(txtemail.getText()) || TextUtils.isEmpty(txtpassword.getText())){
                    alertDialogBuilder.setMessage("Please fill all field").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }

                if(isvalid){
                    try {
                        final User user = new User();
                        email = txtemail.getText().toString();
                        password = txtpassword.getText().toString();
                        user.setEmail(email.toString());
                        user.setPassword(password.toString());
                        final UserService userService = ApiClient.getClient().create(UserService.class);
                        Call call = userService.login(user);

                        call.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if(response.code() == 200){
                                    Intent intent = new Intent(Login2.this, Home.class);
                                    String token = response.body().getToken().toString();
                                    User users = response.body();

                                    String iduser = users.getId();
                                    intent.putExtra("token", token);
                                    intent.putExtra("email", email.toString());
                                    intent.putExtra("id_user", iduser);

                                    startActivity(intent);
                                    Toast.makeText(Login2.this, "Login Success", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else{
                                    txtemail.setText("");
                                    txtpassword.setText("");
                                    alertDialogBuilder.setMessage("Email or Password Invalid").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
                            public void onFailure(Call call, Throwable t) {
                                Toast.makeText(Login2.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                                alertDialogBuilder.setMessage("Connection problem").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
                        Toast.makeText(mcontext, "Failed to load Api", Toast.LENGTH_SHORT).show();
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
            }
        });
    }

    private void initDialogBuilder() {
        alertDialogBuilder = new AlertDialog.Builder(this);
    }


}
