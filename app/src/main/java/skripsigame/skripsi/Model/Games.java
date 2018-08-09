package skripsigame.skripsi.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Games implements Serializable {

    @SerializedName("game_name")
    private String game_name;

    @SerializedName("release_date")
    private String release_date;

    @SerializedName("harga")
    private String harga;

    @SerializedName("photo_url")
    private String photo_url;

    @SerializedName("rating")
    private String rating;

    @SerializedName("id")
    private String id;

    @SerializedName("screenshot1_url")
    private String screenshot1_url;

    @SerializedName("screenshot2_url")
    private String screenshot2_url;

    @SerializedName("screenshot3_url")
    private String screenshot3_url;

    @SerializedName("video_url")
    private String video_url;

    @SerializedName("OS")
    private String OS;

    @SerializedName("HDD_space")
    private String HDD_space;

    @SerializedName("publisher")
    private String publisher;

    @SerializedName("description")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getScreenshot1_url() {
        return screenshot1_url;
    }

    public void setScreenshot1_url(String screenshot1_url) {
        this.screenshot1_url = screenshot1_url;
    }

    public String getScreenshot2_url() {
        return screenshot2_url;
    }

    public void setScreenshot2_url(String screenshot2_url) {
        this.screenshot2_url = screenshot2_url;
    }

    public String getScreenshot3_url() {
        return screenshot3_url;
    }

    public void setScreenshot3_url(String screenshot3_url) {
        this.screenshot3_url = screenshot3_url;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getOS() {
        return OS;
    }

    public void setOS(String OS) {
        this.OS = OS;
    }

    public String getHDD_space() {
        return HDD_space;
    }

    public void setHDD_space(String HDD_space) {
        this.HDD_space = HDD_space;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    private String token;
    private String email;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
