package skripsigame.skripsi.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import skripsigame.skripsi.Model.Feature;
import skripsigame.skripsi.Model.Games;
import skripsigame.skripsi.Model.Processor;
import skripsigame.skripsi.Model.Ram;
import skripsigame.skripsi.Model.Spesifikasi;
import skripsigame.skripsi.Model.UserGenre;
import skripsigame.skripsi.Model.Vga;

public interface GameService {

    @POST("games/populargameMobile")
    @FormUrlEncoded
    Call<List<Games>> listgame(@Header("Authorization") String authToken,
                               @Field("email") String email,
                               @Field("page_number") int page_number,
                               @Field("item_count") int item_count);

    @POST("games/newgameMobile")
    @FormUrlEncoded
    Call<List<Games>> newgame(@Header("Authorization") String authToken,
                               @Field("email") String email,
                               @Field("page_number") int page_number,
                               @Field("item_count") int item_count);

    @POST("rekomendasi/mobilerekomendasi")
    @FormUrlEncoded
    Call<List<Games>> rekomen(@Header("Authorization") String authToken,
                              @Field("email") String email,
                              @Field("page_number") int page_number,
                              @Field("item_count") int item_count);

    @POST("games/detailGameMobile/{id}")
    Call<Games> detailgame(@Header("Authorization") String authToken, @Path("id") String id);

    @POST("feature/getfeature")
    @FormUrlEncoded
    Call<Feature> getfeature(@Header("Authorization") String authToken,
                          @Field("game_id") String game_id);

    @POST("games/detailmobilegenre")
    @FormUrlEncoded
    Call<List<UserGenre>> getdetailgenre(@Header("Authorization") String authToken,
                               @Field("game_id") String game_id);

    @POST("games/gamesspesifikasi")
    @FormUrlEncoded
    Call<Spesifikasi> getspesifikasi(@Header("Authorization") String authToken,
                                     @Field("id") String id,
                                     @Field("status") String status);

    @POST("ram/findram")
    @FormUrlEncoded
    Call<Ram> getram(@Header("Authorization") String authToken,
                             @Field("ram_id") String ram_id);

    @POST("processor/findproc")
    @FormUrlEncoded
    Call<Processor> getproc(@Header("Authorization") String authToken,
                            @Field("proc_id") String proc_id);

    @POST("vga/findvga")
    @FormUrlEncoded
    Call<Vga> getvga(@Header("Authorization") String authToken,
                      @Field("vga_id") String vga_id);



}
