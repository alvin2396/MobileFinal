package skripsigame.skripsi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import skripsigame.skripsi.Activity.ActivityCarts;
import skripsigame.skripsi.Activity.Home;
import skripsigame.skripsi.ApiClient.ApiClient;
import skripsigame.skripsi.ApiClient.UserService;
import skripsigame.skripsi.Model.Cart;
import skripsigame.skripsi.Model.Games;
import skripsigame.skripsi.R;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{

    List<Cart> cartList;
    ImageLoader imageLoader;
    Context mcontext;

    public CartAdapter(List<Cart> listcart, ImageLoader imageLoader) {
        this.cartList = listcart;
        this.imageLoader = imageLoader;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // di tutorial ini kita hanya menggunakan data String untuk tiap item
        public TextView judulgame,harga;
        ImageView cartimageView,delete;
        Button cartdelete;

        public ViewHolder(View itemview) {
            super(itemview);
            judulgame = (TextView) itemview.findViewById(R.id.cartjudulgame);
            harga = (TextView) itemview.findViewById(R.id.cartharga);
            cartimageView = (ImageView)itemview.findViewById(R.id.cartimage);
//            delete = (ImageView)itemview.findViewById(R.id.cartdelete);
            cartdelete = (Button)itemview.findViewById(R.id.deletecart);
            mcontext = itemview.getContext();

        }
    }

    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // membuat view baru
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitem, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        return  new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CartAdapter.ViewHolder holder, int position) {

        final Cart cartitem = this.cartList.get(position);
        holder.judulgame.setText(cartitem.getNama_game());
        final String currency = NumberFormat.getNumberInstance(Locale.ENGLISH).format(Integer.parseInt(cartList.get(position).getHarga()));
        holder.harga.setText(currency);
        final String idgame = cartitem.getId_game();
        final String token = cartitem.getToken();
        final String email = cartitem.getEmail();
        final String cartphoto = cartitem.getPhoto_url();
        imageLoader.displayImage(cartphoto, holder.cartimageView);
        final String id = cartitem.getId();

        holder.cartdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                UserService userService = ApiClient.getClient().create(UserService.class);
                Call call = userService.removecart("Bearer"+cartitem.getToken(),cartitem.getId());
                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if(response.isSuccessful()){
                            Intent intent = new Intent(view.getContext(),ActivityCarts.class);
                            intent.putExtra("token", token);
                            intent.putExtra("email",email);
                            view.getContext().startActivity(intent);

                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {

                    }
                });

            }
        });




    }

    @Override
    public int getItemCount() {
        // menghitung ukuran dataset / jumlah data yang ditampilkan di RecyclerView
        return cartList.size();
    }

    public void AddItems(List<Cart> carts)
    {
        for (Cart in : carts)
        {
            cartList.add(in);
        }
        notifyDataSetChanged();
    }


}
