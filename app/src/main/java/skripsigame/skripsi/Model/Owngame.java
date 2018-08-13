package skripsigame.skripsi.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Owngame implements Serializable {

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("game_id")
    private String game_id;

    @SerializedName("id")
    private String id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
