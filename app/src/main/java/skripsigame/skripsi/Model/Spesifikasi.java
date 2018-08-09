package skripsigame.skripsi.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Spesifikasi implements Serializable {

    @SerializedName("ram_id")
    private String ram_id;

    @SerializedName("processor_id")
    private String processor_id;

    @SerializedName("vga_id")
    private String vga_id;

    public String getRam_id() {
        return ram_id;
    }

    public void setRam_id(String ram_id) {
        this.ram_id = ram_id;
    }

    public String getProcessor_id() {
        return processor_id;
    }

    public void setProcessor_id(String processor_id) {
        this.processor_id = processor_id;
    }

    public String getVga_id() {
        return vga_id;
    }

    public void setVga_id(String vga_id) {
        this.vga_id = vga_id;
    }
}
