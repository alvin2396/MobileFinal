package skripsigame.skripsi.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Ram implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("ram_size")
    private String ram_size;

    @SerializedName("ram_score")
    private String ram_score;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRam_size() {
        return ram_size;
    }

    public void setRam_size(String ram_size) {
        this.ram_size = ram_size;
    }

    public String getRam_score() {
        return ram_score;
    }

    public void setRam_score(String ram_score) {
        this.ram_score = ram_score;
    }
}
