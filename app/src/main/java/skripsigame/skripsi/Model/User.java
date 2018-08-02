package skripsigame.skripsi.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    @SerializedName("token")
    private String token;

    @SerializedName("nama")
    private String nama;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("id")
    private String id;

    @SerializedName("wallet")
    private String wallet;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

//    @SerializedName("genre")
//    private String genre;

//    @SerializedName("processor_id")
//    private String processor_id;
//
//    @SerializedName("vga_id")
//    private String vga_id;
//
//    @SerializedName("ram_id")
//    private String ram_id;

//    public String getProcessor_id() {
//        return processor_id;
//    }
//
//    public void setProcessor_id(String processor_id) {
//        this.processor_id = processor_id;
//    }
//
//    public String getRam_id() {
//        return ram_id;
//    }
//
//    public void setRam_id(String ram_id) {
//        this.ram_id = ram_id;
//    }
//
//    public String getVga_id() {
//        return vga_id;
//    }
//
//    public void setVga_id(String vga_id) {
//        this.vga_id = vga_id;
//    }

//    public String getGenre() {
//        return genre;
//    }
//
//    public void setGenre(String genre) {
//        this.genre = genre;
//    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
