package skripsigame.skripsi.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Vga implements Serializable {

    @SerializedName("vga_name")
    private String vga_name;

    @SerializedName("vga_score")
    private String vga_score;

    @SerializedName("id")
    private String id;

    public String getVga_name() {
        return vga_name;
    }

    public void setVga_name(String vga_name) {
        this.vga_name = vga_name;
    }

    public String getVga_score() {
        return vga_score;
    }

    public void setVga_score(String vga_score) {
        this.vga_score = vga_score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
