package skripsigame.skripsi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import skripsigame.skripsi.Activity.ActivityCarts;
import skripsigame.skripsi.Activity.GameDetail;
import skripsigame.skripsi.ApiClient.ApiClient;
import skripsigame.skripsi.ApiClient.UserService;
import skripsigame.skripsi.Model.Cart;
import skripsigame.skripsi.Model.Games;
import skripsigame.skripsi.Model.Owngame;
import skripsigame.skripsi.Model.User;
import skripsigame.skripsi.R;

public class AdapterRVPopular extends RecyclerView.Adapter<AdapterRVPopular.ViewHolder> {
    List<Games> Gamespopular;
    ImageLoader imageLoader;


    public AdapterRVPopular(List<Games> Gamespopular, ImageLoader imageLoader)
    {
        this.Gamespopular = Gamespopular;
        this.imageLoader = imageLoader;
    }

    @Override
    public AdapterRVPopular.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itempopular, viewGroup, false);
        return new AdapterRVPopular.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final AdapterRVPopular.ViewHolder holder, int position) {
        final Games populerItem = this.Gamespopular.get(position);
        holder.tvjudulgame.setText(populerItem.getGame_name());
        final String populerPhhoto = populerItem.getPhoto_url();
        holder.tvreleasedate.setText(populerItem.getRelease_date());
        final String currency = NumberFormat.getNumberInstance(Locale.ENGLISH).format(Integer.parseInt(populerItem.getHarga()));
        holder.tvharga.setText(currency);
        final String idgame = populerItem.getId();
        holder.tvrating.setText(populerItem.getRating());
        imageLoader.displayImage(populerPhhoto, holder.populerImageview);
        float rating2 = Float.parseFloat(populerItem.getRating());
        rating2 = rating2/2;
        holder.ratingBar.setRating(rating2);
        final UserService userService = ApiClient.getClient().create(UserService.class);


        holder.btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final Call<User> userCall = userService.find("Bearer"+populerItem.getToken(),populerItem.getEmail());
                userCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()){
                            User user = response.body();
                            final String user_id = user.getId();
                            Call checkcart = userService.checkcart("Bearer"+populerItem.getToken(),populerItem.getEmail(),populerItem.getId());
                            checkcart.enqueue(new Callback() {
                                @Override
                                public void onResponse(Call call, Response response) {
                                    if(response.code() == 204){
                                        Call checkown = userService.checkowngame("Bearer"+populerItem.getToken(),user_id,populerItem.getId());
                                        checkown.enqueue(new Callback() {
                                            @Override
                                            public void onResponse(Call call, Response response) {
                                                if(response.code() == 204){
                                                    Call addcart = userService.addcart("Bearer"+populerItem.getToken(),user_id,populerItem.getGame_name(),populerItem.getId(),populerItem.getHarga(),populerItem.getPhoto_url());
                                                    addcart.enqueue(new Callback() {
                                                        @Override
                                                        public void onResponse(Call call, Response response) {
                                                            if(response.isSuccessful()){
                                                                Intent intent = new Intent(view.getContext(),ActivityCarts.class);
                                                                intent.putExtra("email",populerItem.getEmail());
                                                                intent.putExtra("token",populerItem.getToken());
                                                                view.getContext().startActivity(intent);
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call call, Throwable t) {
                                                            Toast.makeText(view.getContext(), "Fail to add item", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }
                                                else {
                                                    Toast.makeText(view.getContext(), "item already owned", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call call, Throwable t) {

                                            }
                                        });
                                    }
                                    else if(response.isSuccessful()){
                                        Toast.makeText(view.getContext(), "Item already added", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call call, Throwable t) {
                                    Toast.makeText(view.getContext(), "Fail to check cart", Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(view.getContext(), "Fail to Load User Data", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        holder.populerImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentdetail = new Intent(view.getContext(), GameDetail.class);
                intentdetail.putExtra("idgame",idgame );
                intentdetail.putExtra("games", populerItem);
                intentdetail.putExtra("token", populerItem.getToken());
                intentdetail.putExtra("email", populerItem.getEmail());
                view.getContext().startActivity(intentdetail);
            }
        });

    }
    @Override
    public int getItemCount() {
        return Gamespopular.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvjudulgame, tvharga,tvreleasedate,tvrating;
        ImageView populerImageview;
        RatingBar ratingBar;
        Button btnadd;

        public ViewHolder(View itemView) {
            super(itemView);
            tvjudulgame = (TextView) itemView.findViewById(R.id.judulgamepopular);
            populerImageview = (ImageView)itemView.findViewById(R.id.gambarpopular);
            tvharga = (TextView)itemView.findViewById(R.id.hargagame);
            tvreleasedate = (TextView)itemView.findViewById(R.id.tanggalgame);
            tvrating = (TextView)itemView.findViewById(R.id.ratingangka);
            btnadd = (Button)itemView.findViewById(R.id.addgamepopular);
            ratingBar = (RatingBar)itemView.findViewById(R.id.ratingbarpopular);


        }
    }


    public void AddItems(List<Games> gamesPop)
    {
        for (Games in : gamesPop)
        {
            Gamespopular.add(in);
        }
        notifyDataSetChanged();
    }

}
