package skripsigame.skripsi.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserGenre implements Serializable {

    @SerializedName("nama_genre")
    private String nama_genre;

    @SerializedName("id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama_genre() {
        return nama_genre;
    }

    public void setNama_genre(String nama_genre) {
        this.nama_genre = nama_genre;
    }
}
