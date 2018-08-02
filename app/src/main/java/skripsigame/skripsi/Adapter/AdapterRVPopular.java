package skripsigame.skripsi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import skripsigame.skripsi.Model.Games;
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
        holder.tvharga.setText(populerItem.getHarga());
        holder.tvrating.setText(populerItem.getRating());
        imageLoader.displayImage(populerPhhoto, holder.populerImageview);

    }
    @Override
    public int getItemCount() {
        return Gamespopular.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvjudulgame, tvharga,tvreleasedate,tvrating;
        ImageView populerImageview;

        public ViewHolder(View itemView) {
            super(itemView);
            tvjudulgame = (TextView) itemView.findViewById(R.id.judulgamepopular);
            populerImageview = (ImageView)itemView.findViewById(R.id.gambarpopular);
            tvharga = (TextView)itemView.findViewById(R.id.hargagame);
            tvreleasedate = (TextView)itemView.findViewById(R.id.tanggalgame);
            tvrating = (TextView)itemView.findViewById(R.id.ratingangka);


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
