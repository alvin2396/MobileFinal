package skripsigame.skripsi.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import skripsigame.skripsi.Model.Games;

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
}
