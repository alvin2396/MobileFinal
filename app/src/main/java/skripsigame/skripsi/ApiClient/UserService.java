package skripsigame.skripsi.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import skripsigame.skripsi.Model.Processor;
import skripsigame.skripsi.Model.Ram;
import skripsigame.skripsi.Model.User;
import skripsigame.skripsi.Model.UserGenre;
import skripsigame.skripsi.Model.Vga;

public interface UserService {
    @POST("masuk")
    Call<User> login(@Body User user);

    @POST("user/UserprofileMobile")
    @FormUrlEncoded
    Call<User> find(@Header("Authorization") String authToken, @Field("email") String email);

    @POST("user/Userprocessor")
    @FormUrlEncoded
    Call<Processor> findprocessor(@Header("Authorization") String authToken, @Field("email") String email);

    @POST("user/Uservga")
    @FormUrlEncoded
    Call<Vga> findvga(@Header("Authorization") String authToken, @Field("email") String email);

    @POST("user/Userram")
    @FormUrlEncoded
    Call<Ram> findram(@Header("Authorization") String authToken, @Field("email") String email);

    @POST("user/Usergenre")
    @FormUrlEncoded
    Call<List<UserGenre>> findgenre(@Header("Authorization") String authToken, @Field("email") String email);

    @POST("user/updateprofileMobile/")
    @FormUrlEncoded
    Call<Void> update(@Header("Authorization") String authToken,
                      @Field("id") String id,
                      @Field("nama") String nama,
                      @Field("alamat") String alamat,
                      @Field("genre") ArrayList<String> genre
    );
}
