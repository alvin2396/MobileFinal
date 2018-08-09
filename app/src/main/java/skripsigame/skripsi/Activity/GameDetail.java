package skripsigame.skripsi.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import skripsigame.skripsi.ApiClient.ApiClient;
import skripsigame.skripsi.ApiClient.GameService;
import skripsigame.skripsi.ApiClient.UserService;
import skripsigame.skripsi.MainActivity;
import skripsigame.skripsi.Model.Feature;
import skripsigame.skripsi.Model.Games;
import skripsigame.skripsi.Model.Processor;
import skripsigame.skripsi.Model.Ram;
import skripsigame.skripsi.Model.Spesifikasi;
import skripsigame.skripsi.Model.UserGenre;
import skripsigame.skripsi.Model.Vga;
import skripsigame.skripsi.R;

public class GameDetail extends AppCompatActivity {

    private VideoView videoView;
    List<UserGenre> userGenreList;
    UserGenre userGenre = new UserGenre();
    private int position = 0;
    String gamegenre;
    String recproc,recvga,recram;
    private String userprocscore,userramscore,uservgascore;
    private String processorreq;
    private MediaController mediaController;
    ViewPager viewPager;
    TextView gameplay,graphic,music,story,controls,detailgenre;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> dataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Game Detail");
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        SliderAdapter viewPagerAdapter = new SliderAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
        videoView = (VideoView)findViewById(R.id.videodetail);
        Timer timer = new Timer();
        gamegenre = "";
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);





        dataSet = new ArrayList<>();
        initDataset();

        rvView = (RecyclerView) findViewById(R.id.review);
        rvView.setHasFixedSize(true);

        /**
         * Kita menggunakan LinearLayoutManager untuk list standar
         * yang hanya berisi daftar item
         * disusun dari atas ke bawah
         */
        layoutManager = new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);

        adapter = new ReviewAdapter(dataSet);
        rvView.setAdapter(adapter);
        initcomponent();
        initfeature();
        initdetailgenre();
        initminspek();
        checkspek();

    }

    private void initcomponent(){
        final ProgressDialog progressDialog = new ProgressDialog(GameDetail.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
//
//        Thread thread = new Thread(){
//            @Override
//            public void run(){
//                try {
//                    sleep(5000);
//                    progressDialog.dismiss();
//                }catch (InterruptedException e){
//                    e.printStackTrace();
//                }
//            }
//        };
//        thread.start();
        final Intent intent = getIntent();
        final String token = intent.getStringExtra("token");
        final String id = intent.getStringExtra("idgame");
        final String email = intent.getStringExtra("email");
        ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
        GameService gameService = ApiClient.getClient().create(GameService.class);
        Call<Games> call = gameService.detailgame("Bearer"+token,id);
        call.enqueue(new Callback<Games>() {
            @Override
            public void onResponse(Call<Games> call, Response<Games> response) {
                if(response.isSuccessful()){
                    try {
                        Games datadetail = response.body();
                        progressDialog.dismiss();
                        TextView tvgamename = (TextView)findViewById(R.id.judulgamedetail);
                        tvgamename.setText(datadetail.getGame_name());
                        TextView tvharga = (TextView)findViewById(R.id.price);
                        tvharga.setText(datadetail.getHarga());
                        TextView tvdeskripsi = (TextView)findViewById(R.id.description);
                        tvdeskripsi.setText(datadetail.getDescription());
                        TextView tvrating = (TextView)findViewById(R.id.detail_rating);
                        tvrating.setText(datadetail.getRating());
                        TextView releasedate = (TextView)findViewById(R.id.detail_releasedate);
                        releasedate.setText(datadetail.getRelease_date());
                        TextView publisher = (TextView)findViewById(R.id.publisher);
                        publisher.setText(datadetail.getPublisher());
                        TextView osmin = (TextView)findViewById(R.id.osmin);
                        osmin.setText(datadetail.getOS());
                        TextView storagemin = (TextView)findViewById(R.id.storagemin);
                        storagemin.setText(datadetail.getHDD_space());
                        TextView osrec = (TextView)findViewById(R.id.osreq);
                        osrec.setText(datadetail.getOS());
                        TextView storagerec = (TextView)findViewById(R.id.storagereq);
                        storagerec.setText(datadetail.getHDD_space());

                        // Set the media controller buttons
                        if (mediaController == null) {
                            mediaController = new MediaController(GameDetail.this);

                            // Set the videoView that acts as the anchor for the MediaController.
                            mediaController.setAnchorView(videoView);


                            // Set MediaController for VideoView
                            videoView.setMediaController(mediaController);
                        }


                        try {
                            // ID of video file.
                            Uri uri = Uri.parse(datadetail.getVideo_url().toString());
                            videoView.setVideoURI(uri);

                        } catch (Exception e) {
                            Log.e("Error", e.getMessage());
                            e.printStackTrace();
                        }

                        videoView.requestFocus();


                        // When the video file ready for playback.
                        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                            public void onPrepared(MediaPlayer mediaPlayer) {


                                videoView.seekTo(position);
                                if (position == 1) {
                                }

                                // When video Screen change size.
                                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                                    @Override
                                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

                                        // Re-Set the videoView that acts as the anchor for the MediaController
                                        mediaController.setAnchorView(videoView);
                                    }
                                });
                            }
                        });


                    }catch (Exception e){
                        progressDialog.dismiss();
                        Toast.makeText(GameDetail.this, "Response fail", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(GameDetail.this, "Fail To Call API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Games> call, Throwable t) {
                Toast.makeText(GameDetail.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initfeature(){
        final Intent intent = getIntent();
        final String token = intent.getStringExtra("token");
        final String game_id = intent.getStringExtra("idgame");
        GameService gameService = ApiClient.getClient().create(GameService.class);
        Call<Feature> call = gameService.getfeature("Bearer"+token,game_id );
        call.enqueue(new Callback<Feature>() {
            @Override
            public void onResponse(Call<Feature> call, Response<Feature> response) {
                try {
                    if(response.isSuccessful()){
                        Feature feature = response.body();
                        gameplay = (TextView)findViewById(R.id.ratinggameplay);
                        gameplay.setText(feature.getGameplay());
                        gameplay.setMaxLines(4);
                        graphic = (TextView)findViewById(R.id.ratinggraphics);
                        graphic.setText(feature.getGraphic());
                        music = (TextView)findViewById(R.id.ratingmusic);
                        music.setText(feature.getMusic());
                        story = (TextView)findViewById(R.id.ratingstory);
                        story.setText(feature.getStory());
                        controls = (TextView)findViewById(R.id.ratingcontrols);
                        controls.setText(feature.getControls());
                    }
                    else {
                        Toast.makeText(GameDetail.this, "Response failed", Toast.LENGTH_SHORT).show();

                    }
                }catch (Exception e){
                    Toast.makeText(GameDetail.this, "Try failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Feature> call, Throwable t) {
                Toast.makeText(GameDetail.this, "Fail to Load Feature data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initminspek(){
        final Intent intent = getIntent();
        final String token = intent.getStringExtra("token");
        final String game_id = intent.getStringExtra("idgame");
        final String status = "minimum";

        GameService gameService = ApiClient.getClient().create(GameService.class);
        Call<Spesifikasi> call = gameService.getspesifikasi("Bearer"+token,game_id,status);
        call.enqueue(new Callback<Spesifikasi>() {
            @Override
            public void onResponse(Call<Spesifikasi> call, Response<Spesifikasi> response) {
                if(response.isSuccessful()){
                    Spesifikasi spesifikasi = response.body();
                   final String minproc = spesifikasi.getProcessor_id();
                    final String minram = spesifikasi.getRam_id();
                    final String minvga = spesifikasi.getVga_id();
                    final GameService gameServices = ApiClient.getClient().create(GameService.class);
                    Call<Processor> processorCall = gameServices.getproc("Bearer"+token,minproc);
                    processorCall.enqueue(new Callback<Processor>() {
                        @Override
                        public void onResponse(Call<Processor> call, Response<Processor> response) {
                            if(response.isSuccessful()){
                                Processor processor = response.body();
                                TextView tvminproc = (TextView)findViewById(R.id.processormin);
                                processorreq = processor.getProcessor_score();
                                tvminproc.setText(processor.getProcessor_name());

                                Call<Vga> vgaCall = gameServices.getvga("Bearer"+token,minvga);
                                vgaCall.enqueue(new Callback<Vga>() {
                                    @Override
                                    public void onResponse(Call<Vga> call, Response<Vga> response) {
                                        if(response.isSuccessful()){
                                            Vga vga = response.body();
                                            TextView tvminvga = (TextView)findViewById(R.id.graphicsmin);
                                            tvminvga.setText(vga.getVga_name());
                                            Call<Ram> ramCall = gameServices.getram("Bearer"+token, minram);
                                            ramCall.enqueue(new Callback<Ram>() {
                                                @Override
                                                public void onResponse(Call<Ram> call, Response<Ram> response) {
                                                    if(response.isSuccessful()){
                                                        Ram ram = response.body();
                                                        TextView tvminram = (TextView)findViewById(R.id.memorymin);
                                                        tvminram.setText(ram.getRam_size()+" GB");
                                                        checkspek();
                                                    }
                                                    else {
                                                        Toast.makeText(GameDetail.this, "Response Fail", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<Ram> call, Throwable t) {
                                                    Toast.makeText(GameDetail.this, "Fail to Load Specification", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                        else {
                                            Toast.makeText(GameDetail.this, "Response Fail", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Vga> call, Throwable t) {
                                        Toast.makeText(GameDetail.this, "Fail to Load Specification", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else {
                                Toast.makeText(GameDetail.this, "Response Fail", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Processor> call, Throwable t) {
                            Toast.makeText(GameDetail.this, "Fail to Load Specification", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Toast.makeText(GameDetail.this, "Specification Load Fail", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<Spesifikasi> call, Throwable t) {
                Toast.makeText(GameDetail.this, "Fail to Load Specification API", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void checkspek(){
        final Intent intent = getIntent();
        final String token = intent.getStringExtra("token");
        final String game_id = intent.getStringExtra("idgame");
        final String email =intent.getStringExtra("email");
        Toast.makeText(this, processorreq, Toast.LENGTH_SHORT).show();
        final UserService userService = ApiClient.getClient().create(UserService.class);
        Call<Processor> call = userService.findprocessor("Bearer"+token,email);
        call.enqueue(new Callback<Processor>() {
            @Override
            public void onResponse(Call<Processor> call, Response<Processor> response) {
                if(response.isSuccessful()){
                    Processor userproc = response.body();
                    final String cek = userproc.getProcessor_score();
                    userprocscore = cek;

                    Call<Ram> ramCall = userService.findram("Bearer"+token,email);
                    ramCall.enqueue(new Callback<Ram>() {
                        @Override
                        public void onResponse(Call<Ram> call, Response<Ram> response) {
                            if(response.isSuccessful()){
                                Ram userram = response.body();
                                final String ramscore = userram.getRam_score();
                                userramscore = ramscore;

                                Call<Vga> vgaCall = userService.findvga("Bearer"+token,email);
                                vgaCall.enqueue(new Callback<Vga>() {
                                    @Override
                                    public void onResponse(Call<Vga> call, Response<Vga> response) {
                                        if(response.isSuccessful()){
                                            Vga uservga = response.body();
                                            final String vgascore = uservga.getVga_score();
                                            uservgascore = vgascore;
                                            checktoast();
                                        }
                                        else {
                                            Toast.makeText(GameDetail.this, "Fail to load user vga", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Vga> call, Throwable t) {
                                        Toast.makeText(GameDetail.this, "Failed to load API VGA", Toast.LENGTH_SHORT).show();
                                    }

                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<Ram> call, Throwable t) {

                        }
                    });

                }
                else {
                    Toast.makeText(GameDetail.this, "Check Spec Response fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Processor> call, Throwable t) {
                Toast.makeText(GameDetail.this, "Fail to Check Spec Data", Toast.LENGTH_SHORT).show();
            }

        });



    }

    private void checktoast(){
        final String check = userprocscore+" "+userramscore+" "+uservgascore;

        boolean proc = true;
        boolean ram = true;
        boolean vga = true;

        if(Integer.parseInt(userprocscore) < 500){
            proc = false;
        }
        if(Integer.parseInt(userramscore) < 5){
            ram = false;
        }
        if(Integer.parseInt(uservgascore) < 80){
            vga = false;
        }

        if(proc && ram && vga){
            Toast.makeText(this, "Playable", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Not Playable", Toast.LENGTH_SHORT).show();
        }
    }

    private void initrecspek(){
        final Intent intent = getIntent();
        final String token = intent.getStringExtra("token");
        final String game_id = intent.getStringExtra("idgame");
        final String status = "recommend";
        GameService gameService = ApiClient.getClient().create(GameService.class);
        Call<Spesifikasi> call = gameService.getspesifikasi("Bearer"+token,game_id,status);
        call.enqueue(new Callback<Spesifikasi>() {
            @Override
            public void onResponse(Call<Spesifikasi> call, Response<Spesifikasi> response) {
                if(response.isSuccessful()){
                    Spesifikasi spesifikasi = response.body();
                    final String recproc = spesifikasi.getProcessor_id();
                    final String recram = spesifikasi.getRam_id();
                    final String recvga = spesifikasi.getVga_id();
                    final GameService gameServices = ApiClient.getClient().create(GameService.class);
                    Call<Processor> processorCall = gameServices.getproc("Bearer"+token,recproc);
                    processorCall.enqueue(new Callback<Processor>() {
                        @Override
                        public void onResponse(Call<Processor> call, Response<Processor> response) {
                            if(response.isSuccessful()){
                                Processor processor = response.body();
                                TextView tvrecproc = (TextView)findViewById(R.id.processorreq);
                                tvrecproc.setText(processor.getProcessor_name());

                            }
                            else {
                                Toast.makeText(GameDetail.this, "Response Fail", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Processor> call, Throwable t) {
                            Toast.makeText(GameDetail.this, "Fail to Load Specification", Toast.LENGTH_SHORT).show();
                        }
                    });

                    Call<Vga> vgaCall = gameServices.getvga("Bearer"+token,recvga);
                    vgaCall.enqueue(new Callback<Vga>() {
                        @Override
                        public void onResponse(Call<Vga> call, Response<Vga> response) {
                            if(response.isSuccessful()){
                                Vga vga = response.body();
                                TextView tvmrecvga = (TextView)findViewById(R.id.graphicsreq);
                                tvmrecvga.setText(vga.getVga_name());
                            }
                            else {
                                Toast.makeText(GameDetail.this, "Response Fail", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Vga> call, Throwable t) {
                            Toast.makeText(GameDetail.this, "Fail to Load Specification", Toast.LENGTH_SHORT).show();
                        }
                    });

                    Call<Ram> ramCall = gameServices.getram("Bearer"+token, recram);
                    ramCall.enqueue(new Callback<Ram>() {
                        @Override
                        public void onResponse(Call<Ram> call, Response<Ram> response) {
                            if(response.isSuccessful()){
                                Ram ram = response.body();
                                TextView tvrecram = (TextView)findViewById(R.id.memoryreq);
                                tvrecram.setText(ram.getRam_size().toString()+" GB");
                            }
                            else {
                                Toast.makeText(GameDetail.this, "Response Fail", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Ram> call, Throwable t) {
                            Toast.makeText(GameDetail.this, "Fail to Load Specification", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else {
                    Toast.makeText(GameDetail.this, "Specification Load Fail", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<Spesifikasi> call, Throwable t) {
                Toast.makeText(GameDetail.this, "Fail to Load Specification API", Toast.LENGTH_SHORT).show();
            }
        });


    }





    private  void initdetailgenre(){
        final Intent intent = getIntent();
        final String token = intent.getStringExtra("token");
        final String game_id = intent.getStringExtra("idgame");
        userGenreList = new ArrayList<>();
        GameService gameService = ApiClient.getClient().create(GameService.class);
        Call<List<UserGenre>> call = gameService.getdetailgenre("Bearer"+token,game_id);
        call.enqueue(new Callback<List<UserGenre>>() {
            @Override
            public void onResponse(Call<List<UserGenre>> call, Response<List<UserGenre>> response) {
                if(response.isSuccessful()){
                    try {
                        final List<UserGenre> userGenres = response.body();
                        for(int i =0;i<userGenres.size();i++){
                            final String nama_genre = userGenres.get(i).getNama_genre();
                            userGenre = new UserGenre();
                            userGenre.setNama_genre(nama_genre);
                            userGenreList.add(userGenre);
                        }

                        for(int i =0;i<userGenreList.size();i++){
                            gamegenre = gamegenre +" "+userGenreList.get(i).getNama_genre().toString();
                        }
                        detailgenre = (TextView)findViewById(R.id.detailgenre);
                        detailgenre.setText(gamegenre);

                    }catch (Exception e){
                        Toast.makeText(GameDetail.this, "Response Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<UserGenre>> call, Throwable t) {
                Toast.makeText(GameDetail.this, "Fail to Load API", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void initDataset(){

        /**
         * Tambahkan item ke dataset
         * dalam prakteknya bisa bermacam2
         * tidak hanya String seperti di kasus ini
         */
        dataSet.add("User 1");
        dataSet.add("User 2");
        dataSet.add("User 3");

    }

    // Find ID corresponding to the name of the resource (in the directory raw).
    public int getRawResIdByName(String resName) {
        String pkgName = this.getPackageName();
        // Return 0 if not found.
        int resID = this.getResources().getIdentifier(resName, "raw", pkgName);
        Log.i("AndroidVideoView", "Res Name: " + resName + "==> Res ID = " + resID);
        return resID;
    }


    // When you change direction of phone, this method will be called.
    // It store the state of video (Current position)
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Store current position.
        savedInstanceState.putInt("CurrentPosition", videoView.getCurrentPosition());
        videoView.pause();
    }


    // After rotating the phone. This method is called.
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Get saved position.
        position = savedInstanceState.getInt("CurrentPosition");
        videoView.seekTo(position);
    }

    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            GameDetail.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(1);
                    } else if (viewPager.getCurrentItem() == 1) {
                        viewPager.setCurrentItem(2);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

    public boolean onSupportNavigateUp() {
        Intent intc = getIntent();
        String tokens  =intc.getStringExtra("token");
        String emails = intc.getStringExtra("email");
        Intent intens = new Intent(GameDetail.this, Home.class);
        intens.putExtra("email",emails);
        intens.putExtra("token",tokens);
        startActivity(intens);
        finish();
        return true;
    }
}
