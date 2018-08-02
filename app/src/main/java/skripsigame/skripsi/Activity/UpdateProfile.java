package skripsigame.skripsi.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.security.cert.Certificate;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import skripsigame.skripsi.ApiClient.ApiClient;
import skripsigame.skripsi.ApiClient.UserService;
import skripsigame.skripsi.R;

public class UpdateProfile extends AppCompatActivity {
    Button confirmupdate;
    String nama,email,alamat,upnama,upalamat;
    TextView txtnama,txtemail,txtalamat;
    ArrayList<String> genre = new ArrayList<String>();
    ArrayList<String> genrelist = new ArrayList<String>();
    ArrayList<String> genrenama = new ArrayList<String>();
    ArrayList<CheckBox> checkBoxArrayList = new ArrayList<CheckBox>();
    Integer totalcheck;
    CheckBox action,adventure,strategy,simulation,rpg,violent,singleplayer,massivelymultiplayer,sports,racing,multiplayer,horror,openworld,fantasy,scifi,indie,fps,mystery,crime;
    AlertDialog alertDialog;
    AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Update Profile");
        initDialogBuilder();
        initcomponent();
        checkgenre();
    }

    private void initcomponent(){
        final Intent intentupdateprofil = getIntent();
        final String token = intentupdateprofil.getStringExtra("token");
        final String email = intentupdateprofil.getStringExtra("email");
        final String setalamat = intentupdateprofil.getStringExtra("alamat");
        final String setnama = intentupdateprofil.getStringExtra("nama");
        final String id = intentupdateprofil.getStringExtra("id");
        genrelist = intentupdateprofil.getStringArrayListExtra("genrelist");
        genrenama = intentupdateprofil.getStringArrayListExtra("genrenama");

        confirmupdate = (Button)findViewById(R.id.saveupdateprofil);
        txtnama = (TextView)findViewById(R.id.updatenama);
        txtnama.setText(setnama);
        txtemail = (TextView)findViewById(R.id.updateemail);
        txtemail.setText(email);
        txtemail.setEnabled(false);
        txtalamat = (TextView)findViewById(R.id.updatealamat);
        txtalamat.setText(setalamat);
        action = (CheckBox)findViewById(R.id.action);
        adventure = (CheckBox)findViewById(R.id.adventure);
        strategy = (CheckBox)findViewById(R.id.strategy);
        simulation = (CheckBox)findViewById(R.id.simulation);
        rpg = (CheckBox)findViewById(R.id.rpg);
        violent = (CheckBox)findViewById(R.id.violent);
        singleplayer = (CheckBox)findViewById(R.id.singleplayer);
        massivelymultiplayer = (CheckBox)findViewById(R.id.massivelymultiplayer);
        sports = (CheckBox)findViewById(R.id.sports);
        racing = (CheckBox)findViewById(R.id.racing);
        multiplayer = (CheckBox)findViewById(R.id.multiplayer);
        horror = (CheckBox)findViewById(R.id.horror);
        openworld = (CheckBox)findViewById(R.id.openworld);
        fantasy = (CheckBox)findViewById(R.id.fantasy);
        scifi = (CheckBox)findViewById(R.id.scifi);
        indie = (CheckBox)findViewById(R.id.indie);
        fps = (CheckBox)findViewById(R.id.fps);
        mystery = (CheckBox)findViewById(R.id.mystery);
        crime = (CheckBox)findViewById(R.id.crime);
        checkBoxArrayList.add(action);
        checkBoxArrayList.add(adventure);
        checkBoxArrayList.add(strategy);
        checkBoxArrayList.add(simulation);
        checkBoxArrayList.add(rpg);
        checkBoxArrayList.add(violent);
        checkBoxArrayList.add(singleplayer);
        checkBoxArrayList.add(massivelymultiplayer);
        checkBoxArrayList.add(sports);
        checkBoxArrayList.add(racing);
        checkBoxArrayList.add(multiplayer);
        checkBoxArrayList.add(horror);
        checkBoxArrayList.add(openworld);
        checkBoxArrayList.add(fantasy);
        checkBoxArrayList.add(scifi);
        checkBoxArrayList.add(indie);
        checkBoxArrayList.add(fps);
        checkBoxArrayList.add(mystery);
        checkBoxArrayList.add(crime);

        totalcheck = 0;

        for(int i =0 ;i<checkBoxArrayList.size();i++){
            for(int j=0 ; j<genrelist.size();j++){
                if(checkBoxArrayList.get(i).getText().toString().toLowerCase().equals(genrenama.get(j).toLowerCase().toString())){
                    checkBoxArrayList.get(i).setChecked(true);
                    genre.add(genrelist.get(j).toString().toLowerCase());
                    totalcheck+=1;
                }

            }

        }

        action.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischeck) {
                if(ischeck){
                    totalcheck +=1;
                    genre.add("5b34f1d1f7460b278469cc33");
                    checkgenre();




                }
                else {

                    totalcheck-=1;
                    remove("5b34f1d1f7460b278469cc33");
                    checkgenre();


                }
            }
        });

        adventure.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischeck) {
                if(ischeck){
                    totalcheck +=1;
                    genre.add("5b34f1d3f7460b278469cc34");
                    checkgenre();

                }
                else {

                    totalcheck-=1;
                    remove("5b34f1d3f7460b278469cc34");
                    checkgenre();

                }
            }
        });

        simulation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischeck) {
                if(ischeck){
                    totalcheck +=1;
                    genre.add("5b34f1d6f7460b278469cc36");
                    checkgenre();



                }
                else {

                    totalcheck-=1;
                    remove("5b34f1d6f7460b278469cc36");
                    checkgenre();

                }
            }
        });

        strategy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischeck) {
                if(ischeck){
                    totalcheck +=1;
                    genre.add("5b34f1d5f7460b278469cc35");
                    checkgenre();



                }
                else {

                    totalcheck-=1;
                    remove("5b34f1d5f7460b278469cc35");
                    checkgenre();

                }
            }
        });

        rpg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischeck) {
                if(ischeck){
                    totalcheck +=1;
                    genre.add("5b34f1d8f7460b278469cc37");
                    checkgenre();



                }
                else {

                    totalcheck-=1;
                    remove("5b34f1d8f7460b278469cc37");
                    checkgenre();

                }
            }
        });



        violent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischeck) {
                if(ischeck){
                    totalcheck +=1;
                    genre.add("5b34f1d9f7460b278469cc38");
                    checkgenre();
                    final String cek = genre.toString();
                    Toast.makeText(UpdateProfile.this, cek, Toast.LENGTH_SHORT).show();


                }
                else {

                    totalcheck-=1;
                    remove("5b34f1d9f7460b278469cc38");
                    checkgenre();

                }
            }
        });

        singleplayer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischeck) {
                if(ischeck){
                    totalcheck +=1;
                    genre.add("5b34f1dbf7460b278469cc39");
                    checkgenre();

                }
                else {

                    totalcheck-=1;
                    remove("5b34f1dbf7460b278469cc39");
                    checkgenre();

                }
            }
        });

        massivelymultiplayer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischeck) {
                if(ischeck){
                    totalcheck +=1;
                    genre.add("5b34f1ddf7460b278469cc3a");
                    checkgenre();

                }
                else {

                    totalcheck-=1;
                    remove("5b34f1ddf7460b278469cc3a");
                    checkgenre();
                    final String cek = genre.toString();
                    Toast.makeText(UpdateProfile.this, cek, Toast.LENGTH_SHORT).show();
                }
            }
        });

        sports.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischeck) {
                if(ischeck){
                    totalcheck +=1;
                    genre.add("5b34f1def7460b278469cc3b");
                    checkgenre();

                }
                else {

                    totalcheck-=1;
                    remove("5b34f1def7460b278469cc3b");
                    checkgenre();

                }
            }
        });

        racing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischeck) {
                if(ischeck){
                    totalcheck +=1;
                    genre.add("5b34f1e0f7460b278469cc3c");
                    checkgenre();

                }
                else {

                    totalcheck-=1;
                    remove("5b34f1e0f7460b278469cc3c");
                    checkgenre();

                }
            }
        });

        multiplayer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischeck) {
                if(ischeck){
                    totalcheck +=1;
                    genre.add("5b34f244f7460b278469cc3d");
                    checkgenre();

                }
                else {

                    totalcheck-=1;
                    remove("5b34f244f7460b278469cc3d");
                    checkgenre();

                }
            }
        });

        horror.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischeck) {
                if(ischeck){
                    totalcheck +=1;
                    genre.add("5b34f246f7460b278469cc3e");
                    checkgenre();



                }
                else {

                    totalcheck-=1;
                    remove("5b34f246f7460b278469cc3e");
                    checkgenre();
                    final String cek = genre.toString();
                    Toast.makeText(UpdateProfile.this, cek, Toast.LENGTH_SHORT).show();
                }
            }
        });

        openworld.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischeck) {
                if(ischeck){
                    totalcheck +=1;
                    genre.add("5b34f248f7460b278469cc3f");
                    checkgenre();

                }
                else {

                    totalcheck-=1;
                    remove("5b34f248f7460b278469cc3f");
                    checkgenre();

                }
            }
        });

        fantasy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischeck) {
                if(ischeck){
                    totalcheck +=1;
                    genre.add("5b34f24af7460b278469cc40");
                    checkgenre();


                }
                else {

                    totalcheck-=1;
                    remove("5b34f24af7460b278469cc40");
                    checkgenre();

                }
            }
        });

        scifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischeck) {
                if(ischeck){
                    totalcheck +=1;
                    genre.add("5b34f24cf7460b278469cc41");
                    checkgenre();

                }
                else {

                    totalcheck-=1;
                    remove("5b34f24cf7460b278469cc41");
                    checkgenre();

                }
            }
        });

        indie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischeck) {
                if(ischeck){
                    totalcheck +=1;
                    genre.add("5b34f24ef7460b278469cc42");
                    checkgenre();

                }
                else {

                    totalcheck-=1;
                    remove("5b34f24ef7460b278469cc42");
                    checkgenre();

                }
            }
        });

        fps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischeck) {
                if(ischeck){
                    totalcheck +=1;
                    genre.add("5b34f250f7460b278469cc43");
                    checkgenre();

                }
                else {

                    totalcheck-=1;
                    remove("5b34f250f7460b278469cc43");
                    checkgenre();

                }
            }
        });

        mystery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischeck) {
                if(ischeck){
                    totalcheck +=1;
                    genre.add("5b34f253f7460b278469cc44");
                    checkgenre();


                }
                else {

                    totalcheck-=1;
                    remove("5b34f253f7460b278469cc44");
                    checkgenre();

                }
            }
        });

        crime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischeck) {
                if(ischeck){
                    totalcheck +=1;
                    genre.add("5b34f255f7460b278469cc45");
                    checkgenre();

                }
                else {

                    totalcheck-=1;
                    remove("5b34f255f7460b278469cc45");
                    checkgenre();

                }
            }
        });












        confirmupdate = (Button)findViewById(R.id.saveupdateprofil);
        confirmupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isvalid = true;
                if(TextUtils.isEmpty(txtnama.getText())){
                    isvalid = false;
                    txtnama.setError("Name required");
                }
                if(TextUtils.isEmpty(txtalamat.getText())){
                    isvalid = false;
                    txtalamat.setError("Address required");
                }
                if(totalcheck == 0){
                    isvalid = false;
                    alertDialogBuilder.setMessage("Genre required at least one").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }

                if(isvalid){
                    try{
                        nama = txtnama.getText().toString();
                        alamat = txtalamat.getText().toString();

                        UserService userService = ApiClient.getClient().create(UserService.class);
                        Call call = userService.update("Bearer"+token,id,nama,alamat,genre);
                        call.enqueue(new Callback() {
                            @Override
                            public void onResponse(Call call, Response response) {
                                if(response.isSuccessful()){
                                    Intent updateprof = new Intent(UpdateProfile.this, Profile.class);
                                    updateprof.putExtra("token", token);
                                    updateprof.putExtra("email", email);
                                    Toast.makeText(UpdateProfile.this, "Profil Updated", Toast.LENGTH_SHORT).show();
                                    startActivity(updateprof);

                                }
                                else {
                                    Toast.makeText(UpdateProfile.this, "Fail to Load Api", Toast.LENGTH_SHORT).show();
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
                            public void onFailure(Call call, Throwable t) {
                                Toast.makeText(UpdateProfile.this, "Failed", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(UpdateProfile.this, "Failed to Call", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void remove(String removedgenre){
        for(int i =0;i < genre.size();i++){
            if(genre.get(i).toLowerCase().toString().equals(removedgenre.toString().toLowerCase())){
                genre.remove(i);
            }
        }
    }

    private void checkgenre (){
        if(totalcheck == 4){
            for(int i = 0;i<checkBoxArrayList.size();i++){
                if(!checkBoxArrayList.get(i).isChecked()){
                    checkBoxArrayList.get(i).setEnabled(false);
                }
            }
        }
        else {
            for(int i = 0;i<checkBoxArrayList.size();i++){
                checkBoxArrayList.get(i).setEnabled(true);
            }
        }
    }

    public boolean onSupportNavigateUp() {
        Intent intc = getIntent();
        String tokens  =intc.getStringExtra("token");
        email = intc.getStringExtra("email");
        Intent intens = new Intent(UpdateProfile.this, Profile.class);
        intens.putExtra("name",nama);
        intens.putExtra("email",email);
        intens.putExtra("token",tokens);
        startActivity(intens);
        finish();
        return true;
    }

    private void initDialogBuilder() {
        alertDialogBuilder = new AlertDialog.Builder(this);
    }




}
