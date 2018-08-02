package skripsigame.skripsi.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import skripsigame.skripsi.Model.Games;
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
        holder.tvreleasedatenew.setText(populerItem.getRelease_date());
        holder.tvharganew.setText(populerItem.getHarga());
        holder.ratingnew.setText(populerItem.getRating());
        imageLoader.displayImage(populerPhhoto, holder.newImageview);

    }
    @Override
    public int getItemCount() {
        return Gamesnew.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvjudulgamenew, tvharganew,tvreleasedatenew,ratingnew;
        ImageView newImageview;

        public ViewHolder(View itemView) {
            super(itemView);
            tvjudulgamenew = (TextView) itemView.findViewById(R.id.judulgamenew);
            newImageview = (ImageView)itemView.findViewById(R.id.gambarnew);
            tvharganew = (TextView)itemView.findViewById(R.id.hargagamenew);
            tvreleasedatenew = (TextView)itemView.findViewById(R.id.tanggalgamenew);
            ratingnew = (TextView)itemView.findViewById(R.id.ratingangkanew);


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


