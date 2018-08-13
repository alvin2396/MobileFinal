package skripsigame.skripsi.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import skripsigame.skripsi.Adapter.CartAdapter;
import skripsigame.skripsi.ApiClient.ApiClient;
import skripsigame.skripsi.ApiClient.UserService;
import skripsigame.skripsi.Model.Cart;
import skripsigame.skripsi.R;

public class CartActivity extends AppCompatActivity {
    RecyclerView rv;
    AlertDialog alertDialog;
    List<Cart> listcart;
    AlertDialog.Builder alertDialogBuilder;
    CartAdapter cartAdapter;
    LinearLayoutManager linaer;
    String email,token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        rv.setHasFixedSize(true);
//        linaer = new LinearLayoutManager(this);
//        rv.setLayoutManager(linaer);
//        initdata();
        initRview();



    }

    private void initRview(){
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        token = intent.getStringExtra("token");
        ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
        listcart = new ArrayList<>();


        UserService userService = ApiClient.getClient().create(UserService.class);
        Call<List<Cart>> call = userService.getcart("Bearer"+token,email);
        call.enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                if(response.isSuccessful()){
                    final List<Cart> list = response.body();
                    Cart cart = new Cart();
                    for(int i=0;i<list.size();i++){
                        cart = new Cart();
                        String game_name = list.get(i).getNama_game();
                        String harga = list.get(i).getHarga();
                        String game_id = list.get(i).getId_game();
                        String user_id = list.get(i).getUser_id();
                        String photo = list.get(i).getPhoto_url();
                        String id = list.get(i).getId();
                        cart.setNama_game(game_name);
                        cart.setHarga(harga);
                        cart.setId(id);
                        cart.setPhoto_url(photo);
                        cart.setId_game(game_id);
                        cart.setUser_id(user_id);
                        cart.setEmail(email);
                        listcart.add(cart);


                    }

                    cartAdapter = new CartAdapter(listcart,ImageLoader.getInstance());
                    rv.setHasFixedSize(true);
                    RecyclerView.LayoutManager recycle = new LinearLayoutManager(CartActivity.this);
                    rv.setLayoutManager(recycle);
                    rv.setAdapter(cartAdapter);
                }
                else {
                    Toast.makeText(CartActivity.this, "Response Fail", Toast.LENGTH_SHORT).show();
                    alertDialogBuilder.setMessage("Connection Problem").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alertDialog =alertDialogBuilder.create();
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Fail to Load API", Toast.LENGTH_SHORT).show();
                alertDialogBuilder.setMessage("Fail to Load API").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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

//    private void initdata(){
//        Intent intent = getIntent();
//        String email = intent.getStringExtra("email");
//        String token = intent.getStringExtra("token");
//        listcart = new ArrayList<>();
//        final UserService userService = ApiClient.getClient().create(UserService.class);
//        Call<List<Cart>> call = userService.getcart("Bearer"+token,email);
//        call.enqueue(new Callback<List<Cart>>() {
//            @Override
//            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
//                if(response.isSuccessful()){
//                    final List<Cart> carts = response.body();
//                    Cart cart = new Cart();
//                    for(int i =0 ;i>carts.size();i++){
//                        cart = new Cart();
//                        String game_name = carts.get(i).getNama_game();
//                        String harga = carts.get(i).getHarga();
//                        String game_id = carts.get(i).getId_game();
//                        String user_id = carts.get(i).getUser_id();
//                        String photo = carts.get(i).getPhoto_url();
//                        String id = carts.get(i).getId();
//                        cart.setNama_game(game_name);
//                        cart.setHarga(harga);
//                        cart.setId(id);
//                        cart.setPhoto_url(photo);
//                        cart.setId_game(game_id);
//                        cart.setUser_id(user_id);
//                        listcart.add(cart);
//                    }
//                    cartAdapter.AddItems(listcart);
//
//                }
//                else {
//                    Toast.makeText(CartActivity.this, "Fail to Call Data", Toast.LENGTH_SHORT).show();
//                    alertDialogBuilder.setMessage("Connection Problem").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            dialogInterface.dismiss();
//                        }
//                    });
//                    alertDialog = alertDialogBuilder.create();
//                    alertDialog.show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Cart>> call, Throwable t) {
//                Toast.makeText(CartActivity.this, "Fail to Load API", Toast.LENGTH_SHORT).show();
//                alertDialogBuilder.setMessage("Connection Problem").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                });
//                alertDialog = alertDialogBuilder.create();
//                alertDialog.show();
//            }
//        });
//    }
}
