package skripsigame.skripsi.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import skripsigame.skripsi.Model.Processor;
import skripsigame.skripsi.Model.Ram;

public interface RamService {
    @POST("ram/getram")
    @FormUrlEncoded
    Call<List<Ram>> listram(@Header("Authorization") String authToken,
                            @Field("email") String email);
}
