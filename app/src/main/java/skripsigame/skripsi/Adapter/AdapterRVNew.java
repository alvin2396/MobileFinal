package skripsigame.skripsi.Adapter;

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

public class AdapterRVNew extends RecyclerView.Adapter<AdapterRVNew.ViewHolder> {
    List<Games> Gamesnew;
    ImageLoader imageLoader;

    public AdapterRVNew(List<Games> gamesnew, ImageLoader imageLoader) {
        this.Gamesnew = gamesnew;
        this.imageLoader = imageLoader;
    }

    @Override
    public AdapterRVNew.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itemnew, viewGroup, false);
        return new AdapterRVNew.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final AdapterRVNew.ViewHolder holder, int position) {
        final Games populerItem = this.Gamesnew.get(position);
        holder.tvjudulgamenew.setText(populerItem.getGame_name());
        final String populerPhhoto = populerItem.getPhoto_url();
        final String currency = NumberFormat.getNumberInstance(Locale.ENGLISH).format(Integer.parseInt(populerItem.getHarga()));
        holder.tvharganew.setText(currency);
        final String idgame = populerItem.getId();
        holder.ratingnew.setText(populerItem.getRating());
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

        imageLoader.displayImage(populerPhhoto, holder.newImageview);
        holder.newImageview.setOnClickListener(new View.OnClickListener() {
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
        return Gamesnew.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvjudulgamenew, tvharganew,tvreleasedatenew,ratingnew;
        ImageView newImageview;
        RatingBar ratingBar;
        Button btnadd;

        public ViewHolder(View itemView) {
            super(itemView);
            tvjudulgamenew = (TextView) itemView.findViewById(R.id.judulgamenew);
            newImageview = (ImageView)itemView.findViewById(R.id.gambarnew);
            tvharganew = (TextView)itemView.findViewById(R.id.hargagamenew);
            tvreleasedatenew = (TextView)itemView.findViewById(R.id.tanggalgamenew);
            ratingnew = (TextView)itemView.findViewById(R.id.ratingangkanew);
            btnadd = (Button)itemView.findViewById(R.id.addgamenew);
            ratingBar = (RatingBar)itemView.findViewById(R.id.ratingbarnew);



        }
    }

    public void AddItems(List<Games> gamesPop)
    {
        for (Games in : gamesPop)
        {
            Gamesnew.add(in);
        }
        notifyDataSetChanged();
    }


}


