package skripsigame.skripsi.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Cart implements Serializable {

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("photo_url")
    private String photo_url;

    @SerializedName("nama_game")
    private String nama_game;

    @SerializedName("id_game")
    private String id_game;

    @SerializedName("harga")
    private String harga;

    @SerializedName("id")
    private String id;

    private String token;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getNama_game() {
        return nama_game;
    }

    public void setNama_game(String nama_game) {
        this.nama_game = nama_game;
    }

    public String getId_game() {
        return id_game;
    }

    public void setId_game(String id_game) {
        this.id_game = id_game;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
