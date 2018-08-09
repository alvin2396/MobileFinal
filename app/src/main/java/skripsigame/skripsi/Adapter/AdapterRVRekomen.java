package skripsigame.skripsi.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import skripsigame.skripsi.Activity.GameDetail;
import skripsigame.skripsi.Model.Games;
import skripsigame.skripsi.R;

public class AdapterRVRekomen extends RecyclerView.Adapter<AdapterRVRekomen.ViewHolder> {
    List<Games> Gamesrekomen;
    ImageLoader imageLoader;

    public AdapterRVRekomen(List<Games> Gamesrekomen, ImageLoader imageLoader) {
        this.Gamesrekomen = Gamesrekomen;
        this.imageLoader = imageLoader;
    }

    @Override
    public AdapterRVRekomen.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itemrekomendasi, viewGroup, false);
        return new AdapterRVRekomen.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final AdapterRVRekomen.ViewHolder holder, int position) {

        final Games populerItem = this.Gamesrekomen.get(position);
        holder.tvjudulgamenew.setText(populerItem.getGame_name());
        final String populerPhhoto = populerItem.getPhoto_url();
        final String idgame = populerItem.getId();
        holder.tvreleasedatenew.setText(populerItem.getRelease_date());
        holder.tvharganew.setText(populerItem.getHarga());
        holder.ratingnew.setText(populerItem.getRating());
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
        return Gamesrekomen.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvjudulgamenew, tvharganew,tvreleasedatenew,ratingnew;
        ImageView newImageview;


        public ViewHolder(View itemView) {
            super(itemView);
            tvjudulgamenew = (TextView) itemView.findViewById(R.id.judulgamerecom);
            newImageview = (ImageView)itemView.findViewById(R.id.gambarrecom);
            tvharganew = (TextView)itemView.findViewById(R.id.hargagamerecom);
            tvreleasedatenew = (TextView)itemView.findViewById(R.id.tanggalgamerecom);
            ratingnew = (TextView)itemView.findViewById(R.id.ratingangkarecom);


        }
    }

    public void AddItems(List<Games> gamesPop)
    {
        for (Games in : gamesPop)
        {
            Gamesrekomen.add(in);
        }
        notifyDataSetChanged();
    }

    public void initdata(){

    }



}


