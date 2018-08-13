package skripsigame.skripsi.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import skripsigame.skripsi.Adapter.CartAdapter;
import skripsigame.skripsi.ApiClient.ApiClient;
import skripsigame.skripsi.ApiClient.UserService;
import skripsigame.skripsi.Model.Cart;
import skripsigame.skripsi.Model.User;
import skripsigame.skripsi.R;

public class ActivityCarts extends AppCompatActivity {
    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    String email,token;
    List<Cart> listcart;
    TextView wallet,total;
    Button submit,delete,topupcart;
    Integer totalharga;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carts);
        Intent intent = getIntent();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Cart");
        String getwallet = intent.getStringExtra("wallet");
        wallet = (TextView)findViewById(R.id.walletcart);
        total = (TextView)findViewById(R.id.totalhargacart);
        delete = (Button) findViewById(R.id.deletecart);
        topupcart = (Button)findViewById(R.id.topupcart);
        submit = (Button)findViewById(R.id.submitcart);
        InitCart();
    }
    private void InitCart()
    {

        Intent intents = getIntent();
        email = intents.getStringExtra("email");
        token = intents.getStringExtra("token");
        recyclerView = (RecyclerView) findViewById(R.id.rviewcart);
        ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
        listcart = new ArrayList<>();
        UserService userService= ApiClient.getClient().create(UserService.class);
        Call<List<Cart>> call = userService.getcart("Bearer "+token,email);
        call.enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                if(response.isSuccessful()){
                    final List<Cart> list = response.body();
                    totalharga = 0;
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
                        cart.setToken(token);
                        cart.setEmail(email);
                        listcart.add(cart);
                        totalharga += Integer.parseInt(list.get(i).getHarga());



                    }
                    final String currency = NumberFormat.getNumberInstance(Locale.ENGLISH).format(totalharga);
                    total.setText(currency);
                    initdatauser();

                    cartAdapter = new CartAdapter(listcart,ImageLoader.getInstance());
                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager recycle = new LinearLayoutManager(ActivityCarts.this);
                    recyclerView.setLayoutManager(recycle);
                    recyclerView.setAdapter(cartAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {
                Toast.makeText(ActivityCarts.this, "Failed to Load API Cart", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initdatauser(){
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String token = intent.getStringExtra("token");

        UserService userService = ApiClient.getClient().create(UserService.class);
        Call<User> call = userService.find("Bearer"+token,email);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User user = response.body();
                    String getwallet = user.getWallet();
                    final String currency = NumberFormat.getNumberInstance(Locale.ENGLISH).format(Integer.parseInt(getwallet));
                    wallet.setText(currency);
                    if(Integer.parseInt(getwallet) < totalharga ){
                        submit.setEnabled(false);
                        submit.setBackgroundColor(Color.DKGRAY);
                    }
                }
                else {
                    Toast.makeText(ActivityCarts.this, "User data load fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ActivityCarts.this, "Fail to Load API User", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean onSupportNavigateUp() {
        Intent intc = getIntent();
        String tokens  =intc.getStringExtra("token");
        String email = intc.getStringExtra("email");
        Intent intens = new Intent(ActivityCarts.this, Home.class);
        intens.putExtra("email",email);
        intens.putExtra("token",tokens);
        startActivity(intens);
        finish();
        return true;
    }



}
