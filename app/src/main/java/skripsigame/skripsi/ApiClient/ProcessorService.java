package skripsigame.skripsi.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import skripsigame.skripsi.Model.Processor;

public interface ProcessorService {
    @POST("processor/getprocesssor")
    @FormUrlEncoded
    Call<List<Processor>> listprocessor(@Header("Authorization") String authToken,
                                        @Field("email") String email);

}
