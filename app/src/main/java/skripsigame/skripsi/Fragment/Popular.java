package skripsigame.skripsi.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import skripsigame.skripsi.Adapter.AdapterRVPopular;
import skripsigame.skripsi.ApiClient.ApiClient;
import skripsigame.skripsi.ApiClient.GameService;
import skripsigame.skripsi.Model.Games;
import skripsigame.skripsi.R;


public class Popular extends Fragment {
    List<Games> games;
    AdapterRVPopular adapterRVPopular;
    Games gamesModel = new Games();
    RecyclerView rv;
    TextView jdulPop,ratingpop,release_datepop;
    ImageView gambarPop;
    LinearLayoutManager layoutManager;


    private int page_number =1;
    private int item_count =8;
    private int pasVisibleitem,Visibelitemcount,totalitemcount,previouseitemcount =0;
    private int view_Tresholl =10;
    private boolean isloading =true;

    public Popular() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View _view =inflater.inflate(R.layout.fragment_popular, container, false);
        rv = (RecyclerView)_view.findViewById(R.id.rviewPopular);
        jdulPop = (TextView) _view.findViewById(R.id.judulgamepopular);
        ratingpop = (TextView)_view.findViewById(R.id.ratingangka);
        release_datepop = (TextView)_view.findViewById(R.id.tanggalgame);
        gambarPop =(ImageView)_view.findViewById(R.id.gambarpopular);
        layoutManager= new LinearLayoutManager(getContext());
        rv.setHasFixedSize(true);
        rv.setLayoutManager(layoutManager);
        AmbilDataPopler();
        return _view;
    }

    private void AmbilDataPopler()
    {
        final String tokens =getArguments().getString(  "tokens");
        final String email =getArguments().getString(  "email");
        ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(getContext()).build();
        ImageLoader.getInstance().init(config);
        games= new ArrayList<>();

        GameService gameService = ApiClient.getClient().create(GameService.class);
        Call<List<Games>> call = gameService.listgame("Bearer "+tokens,email,page_number,item_count);
        call.enqueue(new Callback<List<Games>>() {
            @Override
            public void onResponse(Call<List<Games>> call, Response<List<Games>> response) {
                final List<Games> dataGame = response.body();
                gamesModel= new Games();
                for (int i =0 ;i<dataGame.size();i++){
                    gamesModel = new Games();
                    final String id = dataGame.get(i).getId();
                    final String rating = dataGame.get(i).getRating();
                    final String release_date = dataGame.get(i).getRelease_date();
                    final String name = dataGame.get(i).getGame_name();
                    final String photo_url = dataGame.get(i).getPhoto_url();
                    final String harga = dataGame.get(i).getHarga();
                    final String screenshot1 = dataGame.get(i).getScreenshot1_url();
                    final String screenshot2 = dataGame.get(i).getScreenshot2_url();
                    final String screenshot3 = dataGame.get(i).getScreenshot3_url();
                    final String publisher = dataGame.get(i).getPublisher();
                    final String video_url = dataGame.get(i).getVideo_url();
                    final String OS = dataGame.get(i).getOS();
                    final String HDD = dataGame.get(i).getHDD_space();
                    final String description = dataGame.get(i).getDescription();

//                    final String image = "http://10.0.2.2:1337/images/user/"+dataGame.get(i).getPhoto_url();
                    gamesModel.setGame_name(name);
                    gamesModel.setId(id);
                    gamesModel.setEmail(email);
                    gamesModel.setPhoto_url(photo_url);
                    gamesModel.setHarga(harga);
                    gamesModel.setRelease_date(release_date);
                    gamesModel.setRating(rating);
                    gamesModel.setScreenshot1_url(screenshot1);
                    gamesModel.setScreenshot2_url(screenshot2);
                    gamesModel.setScreenshot3_url(screenshot3);
                    gamesModel.setPublisher(publisher);
                    gamesModel.setVideo_url(video_url);
                    gamesModel.setOS(OS);
                    gamesModel.setHDD_space(HDD);
                    gamesModel.setDescription(description);

//                    gamesModel.setPhoto_url(image);
                    games.add(gamesModel);
                }
                adapterRVPopular = new AdapterRVPopular(games,ImageLoader.getInstance());
                rv.setAdapter(adapterRVPopular);
            }

            @Override
            public void onFailure(Call<List<Games>> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                AlertDialog dialog;
                builder.setMessage("Fail to load API").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();
            }
        });
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Visibelitemcount = layoutManager.getChildCount();
                totalitemcount =layoutManager.getItemCount();
                pasVisibleitem = layoutManager.findFirstVisibleItemPosition();
                if(dy>0)
                {
                    if(isloading)
                    {
                        if(totalitemcount>previouseitemcount)
                        {
                            isloading=false;
                            previouseitemcount = totalitemcount;
                        }
                    }
                    if(!isloading&&(totalitemcount-Visibelitemcount)<=(pasVisibleitem+view_Tresholl))
                    {
                        page_number++;
                        LoaddataGamePopuler();
                        isloading =true;
                    }
                }
            }
        });

    }
    private void LoaddataGamePopuler()
    {
        ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(getContext()).build();
        ImageLoader.getInstance().init(config);
        games= new ArrayList<>();
        final String tokens =getArguments().getString(  "tokens");
        final String email =getArguments().getString(  "email");
        GameService gameService = ApiClient.getClient().create(GameService.class);
        Call<List<Games>> call = gameService.listgame("Bearer "+tokens,email,page_number,item_count);
        call.enqueue(new Callback<List<Games>>() {
            @Override
            public void onResponse(Call<List<Games>> call, Response<List<Games>> response) {
                if(response.isSuccessful())
                {

                    final List<Games> loadagain = response.body();
                    for (int i =0 ;i<loadagain.size();i++){
                        gamesModel = new Games();
                        final String emails = loadagain.get(i).getEmail();
                        final String idgame = loadagain.get(i).getId();
                        final String name = loadagain.get(i).getGame_name();
                        final String hargagame = loadagain.get(i).getHarga();
                        final String release_date = loadagain.get(i).getRelease_date();
                        final String rating = loadagain.get(i).getRating();
                        final String photo_url = loadagain.get(i).getPhoto_url();
                        final String image = loadagain.get(i).getPhoto_url();
                        final String screenshot1 = loadagain.get(i).getScreenshot1_url();
                        final String screenshot2 = loadagain.get(i).getScreenshot2_url();
                        final String screenshot3 = loadagain.get(i).getScreenshot3_url();
                        final String publisher = loadagain.get(i).getPublisher();
                        final String video_url = loadagain.get(i).getVideo_url();
                        final String OS = loadagain.get(i).getOS();
                        final String HDD = loadagain.get(i).getHDD_space();
                        final String description = loadagain.get(i).getDescription();
                        gamesModel.setPhoto_url(photo_url);
                        gamesModel.setId(idgame);
                        gamesModel.setEmail(email);
                        gamesModel.setGame_name(name);
                        gamesModel.setHarga(hargagame);
                        gamesModel.setRelease_date(release_date);
                        gamesModel.setRating(rating);
                        gamesModel.setScreenshot1_url(screenshot1);
                        gamesModel.setScreenshot2_url(screenshot2);
                        gamesModel.setScreenshot3_url(screenshot3);
                        gamesModel.setPublisher(publisher);
                        gamesModel.setVideo_url(video_url);
                        gamesModel.setOS(OS);
                        gamesModel.setHDD_space(HDD);
                        gamesModel.setDescription(description);
                        games.add(gamesModel);
                    }
                    adapterRVPopular.AddItems(games);
                }
                else
                {
                    Toast.makeText(getContext(),"Connection Problem",Toast.LENGTH_LONG);
                }
//                pbarpaging.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Games>> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                AlertDialog dialog;
                builder.setMessage("Connection Problem").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();
            }
        });
    }
}
