package skripsigame.skripsi.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class UserGenreResult implements Serializable {

    @SerializedName("usergenre")
    private List<UserGenre> usergenre;

    public List<UserGenre> getUsergenre() {
        return usergenre;
    }
}
