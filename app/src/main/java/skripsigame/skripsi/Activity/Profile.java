package skripsigame.skripsi.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import skripsigame.skripsi.ApiClient.ApiClient;
import skripsigame.skripsi.ApiClient.UserService;
import skripsigame.skripsi.Model.Processor;
import skripsigame.skripsi.Model.Ram;
import skripsigame.skripsi.Model.User;
import skripsigame.skripsi.Model.UserGenre;
import skripsigame.skripsi.Model.Vga;
import skripsigame.skripsi.R;

public class Profile extends AppCompatActivity {
    Button btnchangephoto,btnupdateprofil,btnupdatespek,btntopup;
    String nama,emails,alamat,processor,vga,ram,id,saldo,genreuser;
    List<UserGenre> listgenre = new ArrayList<>();
    ImageView photoprofil;
    TextView txtnama,txtemail,txtalamat,txtproc,txtvga,txtram,wallet,genrepref;
    ArrayList<String> genrelist = new ArrayList<String>();
    ArrayList<String> genrenama = new ArrayList<String>();
    AlertDialog alertDialog;
    AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Profile");

        initcomponent();
        initusergenre();
        initprocessor();
        initram();
        initvga();
        initDialogBuilder();

    }

    private void initcomponent(){
        final Intent intentprofil = getIntent();
        final String token = intentprofil.getStringExtra("token");
        final String email = intentprofil.getStringExtra("email");
        btnchangephoto = (Button)findViewById(R.id.updatefoto);
        btnupdateprofil = (Button)findViewById(R.id.updateprofile);
        btntopup = (Button)findViewById(R.id.topup);

        btnupdatespek = (Button)findViewById(R.id.updatespecification);


        final UserService userService = ApiClient.getClient().create(UserService.class);
        Call<User> call = userService.find("Bearer"+token,email);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body() != null ){
                    User users = response.body();
                    id = users.getId();
                    emails = users.getEmail();
                    nama = users.getNama();
                    alamat = users.getAlamat();
                    saldo = users.getWallet();
//                    processor = users.getProcessor_id();


                    txtnama = (TextView)findViewById(R.id.namauser);
                    txtnama.setText(nama);
                    txtemail = (TextView)findViewById(R.id.emailuser);
                    txtemail.setText(emails);
                    txtalamat = (TextView)findViewById(R.id.alamatuser);
                    txtalamat.setText(alamat);
                    wallet = (TextView) findViewById(R.id.saldo);
                    wallet.setText(NumberFormat.getNumberInstance(Locale.ENGLISH).format(Integer.parseInt(saldo)));
//                    txtproc = (TextView)findViewById(R.id.processorspec);
//                    txtproc.setText(processor);
                }
            }



            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(Profile.this, "Failed to load Data", Toast.LENGTH_SHORT).show();
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

        btnupdateprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this,UpdateProfile.class);
                intent.putExtra("token", token);
                intent.putExtra("email", email);
                intent.putExtra("nama", nama);
                intent.putExtra("alamat", alamat);
                intent.putExtra("id", id);
                intent.putStringArrayListExtra("genrelist", genrelist);
                intent.putStringArrayListExtra("genrenama", genrenama);
                startActivity(intent);
            }
        });

        btnupdatespek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, UpdateSpecification.class);
                intent.putExtra("token", token);
                intent.putExtra("email", email);
                intent.putExtra("id", id);
                intent.putExtra("processor", txtproc.getText().toString());
                intent.putExtra("ram", ram.toString());
                intent.putExtra("vga", txtvga.getText().toString());
                startActivity(intent);
            }
        });

        btntopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, TopUpActivity.class);
                intent.putExtra("token",token);
                intent.putExtra("email", email);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });


    }

    public void initprocessor(){
        final Intent intentprofil = getIntent();
        final String token = intentprofil.getStringExtra("token");
        final String email = intentprofil.getStringExtra("email");

        final UserService userService = ApiClient.getClient().create(UserService.class);
        Call<Processor> call = userService.findprocessor("Bearer"+token,email);
        call.enqueue(new Callback<Processor>() {
            @Override
            public void onResponse(Call<Processor> call, Response<Processor> response) {
                if(response.body() != null){
                    Processor procmodel = response.body();
                    processor = procmodel.getProcessor_name();

                    txtproc = (TextView)findViewById(R.id.processorspec);
                    txtproc.setText(processor);

                }
            }

            @Override
            public void onFailure(Call<Processor> call, Throwable t) {
                Toast.makeText(Profile.this, "Failed to load Data processor", Toast.LENGTH_SHORT).show();
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

    public void initram(){
        final Intent intentprofil = getIntent();
        final String token = intentprofil.getStringExtra("token");
        final String email = intentprofil.getStringExtra("email");

        final UserService userService = ApiClient.getClient().create(UserService.class);
        Call<Ram> call = userService.findram("Bearer"+token,email);
        call.enqueue(new Callback<Ram>() {
            @Override
            public void onResponse(Call<Ram> call, Response<Ram> response) {
                if(response.body() != null){
                    Ram rammodel = response.body();
                    ram = rammodel.getRam_size();

                    txtram = (TextView)findViewById(R.id.memoryspec);
                    txtram.setText(ram+" GB");

                }
            }

            @Override
            public void onFailure(Call<Ram> call, Throwable t) {
                Toast.makeText(Profile.this, "Failed to load Data Ram", Toast.LENGTH_SHORT).show();
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

    public void initvga(){
        final Intent intentprofil = getIntent();
        final String token = intentprofil.getStringExtra("token");
        final String email = intentprofil.getStringExtra("email");

        final UserService userService = ApiClient.getClient().create(UserService.class);
        Call<Vga> call = userService.findvga("Bearer"+token,email);
        call.enqueue(new Callback<Vga>() {
            @Override
            public void onResponse(Call<Vga> call, Response<Vga> response) {
                if(response.body() != null){
                    Vga vgamodel = response.body();
                    vga = vgamodel.getVga_name();

                    txtvga = (TextView)findViewById(R.id.graphicsspec);
                    txtvga.setText(vga);

                }
            }

            @Override
            public void onFailure(Call<Vga> call, Throwable t) {
                Toast.makeText(Profile.this, "Failed to load Data Vga", Toast.LENGTH_SHORT).show();
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

    public void initusergenre(){
        final Intent intentprofil = getIntent();
        final String token = intentprofil.getStringExtra("token");
        final String email = intentprofil.getStringExtra("email");
        genreuser = "";

        final UserService userService = ApiClient.getClient().create(UserService.class);
        Call<List<UserGenre>> call = userService.findgenre("Bearer"+token,email);
        call.enqueue(new Callback<List<UserGenre>>() {
            @Override
            public void onResponse(Call<List<UserGenre>> call, Response<List<UserGenre>> response) {
                if(response.body() != null){
                    List<UserGenre> usergenremodel = response.body();

                    for (int i =0;i<usergenremodel.size();i++){
                        genrelist.add(usergenremodel.get(i).getId().toString());
                        genrenama.add(usergenremodel.get(i).getNama_genre().toString());
                        genreuser = genreuser + " " + usergenremodel.get(i).getNama_genre().toString();
                    }

                    genrepref = (TextView)findViewById(R.id.pref1);
                    genrepref.setText(genreuser);


                }
            }

            @Override
            public void onFailure(Call<List<UserGenre>> call, Throwable t) {
                Toast.makeText(Profile.this, "Failed to load Data User Genre", Toast.LENGTH_SHORT).show();
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

    public boolean onSupportNavigateUp() {
        Intent intc = getIntent();
        String tokens  =intc.getStringExtra("token");
        Intent intens = new Intent(Profile.this, Home.class);
        intens.putExtra("name",nama);
        intens.putExtra("email",emails);
        intens.putExtra("token",tokens);
        startActivity(intens);
        finish();
        return true;
    }

    private void initDialogBuilder() {
        alertDialogBuilder = new AlertDialog.Builder(this);
    }
}
