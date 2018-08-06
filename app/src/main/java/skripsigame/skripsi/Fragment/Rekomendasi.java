package skripsigame.skripsi.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import skripsigame.skripsi.Activity.GameDetail;
import skripsigame.skripsi.Adapter.AdapterRVNew;
import skripsigame.skripsi.Adapter.AdapterRVPopular;
import skripsigame.skripsi.Adapter.AdapterRVRekomen;
import skripsigame.skripsi.ApiClient.ApiClient;
import skripsigame.skripsi.ApiClient.GameService;
import skripsigame.skripsi.Model.Games;
import skripsigame.skripsi.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Rekomendasi.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Rekomendasi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Rekomendasi extends Fragment {
    List<Games> games;
    AdapterRVRekomen adapterRVRekomen;
    Games gamesModel = new Games();
    RecyclerView rv;
    TextView jdulrecom,ratingrecom,release_daterecom;
    ImageView gambarnew;
    LinearLayoutManager layoutManager;

    private int page_number =1;
    private int item_count =5;
    private int pasVisibleitem,Visibelitemcount,totalitemcount,previouseitemcount =0;
    private int view_Tresholl =10;
    private boolean isloading =true;

    public Rekomendasi() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View _view =inflater.inflate(R.layout.fragment_rekomendasi, container, false);
        rv = (RecyclerView)_view.findViewById(R.id.rviewRekomendasi);
        jdulrecom = (TextView) _view.findViewById(R.id.judulgamerecom);
        release_daterecom = (TextView) _view.findViewById(R.id.tanggalgamerecom);
        gambarnew =(ImageView)_view.findViewById(R.id.gambarrecom);
        ratingrecom = (TextView)_view.findViewById(R.id.ratingangkarecom);
        layoutManager= new LinearLayoutManager(getContext());
        rv.setHasFixedSize(true);
        rv.setLayoutManager(layoutManager);
        AmbilDataRec();

        return _view;


    }


    private void AmbilDataRec()
    {
        final String tokens =getArguments().getString(  "tokens");
        final String email =getArguments().getString(  "email");
        ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(getContext()).build();
        ImageLoader.getInstance().init(config);
        games= new ArrayList<>();

        GameService gameService = ApiClient.getClient().create(GameService.class);
        Call<List<Games>> call = gameService.rekomen("Bearer "+tokens,email,page_number,item_count);
        call.enqueue(new Callback<List<Games>>() {
            @Override
            public void onResponse(Call<List<Games>> call, Response<List<Games>> response) {
                final List<Games> dataGame = response.body();
                gamesModel= new Games();
                for (int i =0 ;i<dataGame.size();i++){
                    gamesModel = new Games();
                    final String rating = dataGame.get(i).getRating();
                    final String release_date = dataGame.get(i).getRelease_date();
                    final String name = dataGame.get(i).getGame_name();
                    final String photo_url = dataGame.get(i).getPhoto_url();
                    final String harga = dataGame.get(i).getHarga();

//                    final String image = "http://10.0.2.2:1337/images/user/"+dataGame.get(i).getPhoto_url();
                    gamesModel.setGame_name(name);
                    gamesModel.setPhoto_url(photo_url);
                    gamesModel.setHarga(harga);
                    gamesModel.setRelease_date(release_date);
                    gamesModel.setRating(rating);

//                    gamesModel.setPhoto_url(image);
                    games.add(gamesModel);
                }
                adapterRVRekomen = new AdapterRVRekomen(games,ImageLoader.getInstance());
                rv.setAdapter(adapterRVRekomen);
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
                        LoaddataGameRec();
                        isloading =true;
                    }
                }
            }
        });

    }
    private void LoaddataGameRec()
    {
        ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(getContext()).build();
        ImageLoader.getInstance().init(config);
        games= new ArrayList<>();
        final String tokens =getArguments().getString(  "tokens");
        final String email =getArguments().getString(  "email");
        GameService gameService = ApiClient.getClient().create(GameService.class);
        Call<List<Games>> call = gameService.rekomen("Bearer "+tokens,email,page_number,item_count);
        call.enqueue(new Callback<List<Games>>() {
            @Override
            public void onResponse(Call<List<Games>> call, Response<List<Games>> response) {
                if(response.isSuccessful())
                {

                    final List<Games> loadagain = response.body();
                    for (int i =0 ;i<loadagain.size();i++){
                        gamesModel = new Games();
                        final String name = loadagain.get(i).getGame_name();
                        final String hargagame = loadagain.get(i).getHarga();
                        final String release_date = loadagain.get(i).getRelease_date();
                        final String rating = loadagain.get(i).getRating();
                        final String photo_url = loadagain.get(i).getPhoto_url();
                        final String image = loadagain.get(i).getPhoto_url();
                        gamesModel.setPhoto_url(photo_url);
                        gamesModel.setGame_name(name);
                        gamesModel.setHarga(hargagame);
                        gamesModel.setRelease_date(release_date);
                        gamesModel.setRating(rating);
                        games.add(gamesModel);
                    }
                    adapterRVRekomen.AddItems(games);
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
