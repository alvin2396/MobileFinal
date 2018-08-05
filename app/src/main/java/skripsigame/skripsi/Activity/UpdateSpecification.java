package skripsigame.skripsi.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import skripsigame.skripsi.ApiClient.ApiClient;
import skripsigame.skripsi.ApiClient.ProcessorService;
import skripsigame.skripsi.ApiClient.RamService;
import skripsigame.skripsi.ApiClient.UserService;
import skripsigame.skripsi.ApiClient.VgaService;
import skripsigame.skripsi.Model.Processor;
import skripsigame.skripsi.Model.Ram;
import skripsigame.skripsi.Model.Vga;
import skripsigame.skripsi.R;

public class UpdateSpecification extends AppCompatActivity {

    Spinner procspinner, vgaspinner, ramspinner;
    Button btnconfirmupdatespek;
    Context mcontext;
    List<Processor> processorList;
    List<Ram> ramList;
    List<Vga> vgaList;
    String procid,vgaid,ramid,cek;
    ArrayList<String> listnamaproc, listscoreproc, listnamaram, listscoreram, listnamavga, listscorevga;
    Processor processormodel = new Processor();
    AlertDialog alertDialog;
    AlertDialog.Builder alertDialogBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_specification);
        procspinner = (Spinner)findViewById(R.id.processorspinner);
        vgaspinner = (Spinner)findViewById(R.id.graphicspinner);
        ramspinner = (Spinner)findViewById(R.id.memoryspinner);
        btnconfirmupdatespek = (Button)findViewById(R.id.updatespecconfirm);
        mcontext = this;
        procid = new String();
        vgaid = new String();
        ramid = new String();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Update Specification");
        initDialogBuilder();
        inispinnerprocessor();
        inispinnervga();
        inispinnerram();




       btnconfirmupdatespek.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               final Intent updatespecintent = getIntent();
               final String id = updatespecintent.getStringExtra("id");
               final String token = updatespecintent.getStringExtra("token");
               final String email = updatespecintent.getStringExtra("email");
               final String proc_id = listscoreproc.get(procspinner.getSelectedItemPosition()).toString();
               final String vga_id = listscorevga.get(vgaspinner.getSelectedItemPosition()).toString();
               final String ram_id = listscoreram.get(ramspinner.getSelectedItemPosition()).toString();

               try {
                   UserService userService = ApiClient.getClient().create(UserService.class);
                   Call call = userService.updatespek("Bearer"+token,id,proc_id,vga_id,ram_id);
                   call.enqueue(new Callback() {
                       @Override
                       public void onResponse(Call call, Response response) {
                           if(response.isSuccessful()){
                               Intent updatespek = new Intent(UpdateSpecification.this, Profile.class);
                               updatespek.putExtra("token", token);
                               updatespek.putExtra("email", email);
                               Toast.makeText(UpdateSpecification.this, "Specification Updated", Toast.LENGTH_SHORT).show();
                               startActivity(updatespek);
                           }
                           else {
                               Toast.makeText(UpdateSpecification.this, "Fail to Load API", Toast.LENGTH_SHORT).show();
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
                           Toast.makeText(UpdateSpecification.this, "Failed", Toast.LENGTH_SHORT).show();
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
                   Toast.makeText(mcontext, "Failed to Call", Toast.LENGTH_SHORT).show();
                   alertDialogBuilder.setMessage("Connection Problem").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           dialogInterface.dismiss();
                       }
                   });
                   alertDialog = alertDialogBuilder.create();
                   alertDialog.show();
               }

//               cek = listscoreproc.get(procspinner.getSelectedItemPosition()).toString() + ", " + listscorevga.get(vgaspinner.getSelectedItemPosition()).toString()
//                       + ", " + listscoreram.get(ramspinner.getSelectedItemPosition()).toString();
//               Toast.makeText(mcontext, cek, Toast.LENGTH_SHORT).show();
           }
       });

    }

    private void inispinnerprocessor(){
        final Intent updatespecintent = getIntent();
        final String token = updatespecintent.getStringExtra("token");
        final String email = updatespecintent.getStringExtra("email");
        final String beforeprocessor = updatespecintent.getStringExtra("processor");


        processorList = new ArrayList<>();
        final ProcessorService processorService = ApiClient.getClient().create(ProcessorService.class);
        Call<List<Processor>> call = processorService.listprocessor("Bearer"+token,email);
        call.enqueue(new Callback<List<Processor>>() {
            @Override
            public void onResponse(Call<List<Processor>> call, Response<List<Processor>> response) {
                if(response.isSuccessful()){
                    final List<Processor> dataprocessor = response.body();
                    listnamaproc = new ArrayList<>();
                    listscoreproc = new ArrayList<>();
                    for(int i = 0 ;i<dataprocessor.size();i++){
                        listnamaproc.add(dataprocessor.get(i).getProcessor_name());
                        listscoreproc.add(dataprocessor.get(i).getId());
                    }

                    final ArrayAdapter<String> processoradapter = new ArrayAdapter<String>(mcontext,android.R.layout.simple_spinner_item,listnamaproc);
                    processoradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    procspinner.setAdapter(processoradapter);
                    for(int i = 0;i<listnamaproc.size();i++){
                        if(listnamaproc.get(i).toLowerCase().equals(beforeprocessor.toLowerCase())){
                            procspinner.setSelection(i);
                            break;
                        }
                    }
                }
                else {
                    Toast.makeText(UpdateSpecification.this, "Fail to load API", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<List<Processor>> call, Throwable t) {
                Toast.makeText(UpdateSpecification.this, "Failed to load Spinner Data", Toast.LENGTH_SHORT).show();
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

    private void inispinnerram(){
        final Intent updatespecintent = getIntent();
        final String token = updatespecintent.getStringExtra("token");
        final String email = updatespecintent.getStringExtra("email");
        final String beforeram = updatespecintent.getStringExtra("ram");


        ramList = new ArrayList<>();
        final RamService ramService = ApiClient.getClient().create(RamService.class);
        Call<List<Ram>> call = ramService.listram("Bearer"+token,email);
        call.enqueue(new Callback<List<Ram>>() {
            @Override
            public void onResponse(Call<List<Ram>> call, Response<List<Ram>> response) {
                if(response.isSuccessful()){
                    final List<Ram> dataram = response.body();
                    listnamaram = new ArrayList<>();
                    listscoreram = new ArrayList<>();

                    for(int i = 0 ;i<dataram.size();i++){
                        listnamaram.add(dataram.get(i).getRam_size());
                        listscoreram.add(dataram.get(i).getId());
                    }

                    final ArrayAdapter<String> ramadapter = new ArrayAdapter<String>(mcontext,android.R.layout.simple_spinner_item,listnamaram);
                    ramadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    ramspinner.setAdapter(ramadapter);
                    for(int i = 0;i<listnamaram.size();i++){
                        if(listnamaram.get(i).toString().toLowerCase().equals(beforeram.toString().toLowerCase())){
                            ramspinner.setSelection(i);
                            break;
                        }
                    }
                }
                else {
                    Toast.makeText(UpdateSpecification.this, "Fail to load API", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<List<Ram>> call, Throwable t) {
                Toast.makeText(UpdateSpecification.this, "Failed to load Spinner Data", Toast.LENGTH_SHORT).show();
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

    private void inispinnervga(){
        final Intent updatespecintent = getIntent();
        final String token = updatespecintent.getStringExtra("token");
        final String email = updatespecintent.getStringExtra("email");
        final String beforevga = updatespecintent.getStringExtra("vga");


        vgaList = new ArrayList<>();
        final VgaService vgaService = ApiClient.getClient().create(VgaService.class);
        Call<List<Vga>> call = vgaService.listvga("Bearer"+token,email);
        call.enqueue(new Callback<List<Vga>>() {
            @Override
            public void onResponse(Call<List<Vga>> call, Response<List<Vga>> response) {
                if(response.isSuccessful()){
                    final List<Vga> datavga = response.body();
                    listnamavga = new ArrayList<>();
                    listscorevga = new ArrayList<>();

                    for(int i = 0 ;i<datavga.size();i++){
                        listnamavga.add(datavga.get(i).getVga_name());
                        listscorevga.add(datavga.get(i).getId());
                    }

                    final ArrayAdapter<String> vgadapter = new ArrayAdapter<String>(mcontext,android.R.layout.simple_spinner_item,listnamavga);
                    vgadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    vgaspinner.setAdapter(vgadapter);
                    for(int i = 0;i<listnamavga.size();i++){
                        if(listnamavga.get(i).toLowerCase().equals(beforevga.toLowerCase())){
                            vgaspinner.setSelection(i);
                            break;
                        }
                    }
                }
                else {
                    Toast.makeText(UpdateSpecification.this, "Fail to load API", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<List<Vga>> call, Throwable t) {
                Toast.makeText(UpdateSpecification.this, "Failed to load Spinner Data", Toast.LENGTH_SHORT).show();
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



    private void initDialogBuilder() {
        alertDialogBuilder = new AlertDialog.Builder(this);
    }

    public boolean onSupportNavigateUp() {
        Intent intc = getIntent();
        String tokens  =intc.getStringExtra("token");
        String email = intc.getStringExtra("email");
        Intent intens = new Intent(UpdateSpecification.this, Profile.class);
        intens.putExtra("email",email);
        intens.putExtra("token",tokens);
        startActivity(intens);
        finish();
        return true;
    }


}
