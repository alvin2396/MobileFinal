package skripsigame.skripsi.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Transaksi implements Serializable {

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("total")
    private String total;

    @SerializedName("status")
    private String status;

    @SerializedName("confirmation_code")
    private String confirmation_code;

    @SerializedName("typetransaction")
    private String typetransaction;

    @SerializedName("id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConfirmation_code() {
        return confirmation_code;
    }

    public void setConfirmation_code(String confirmation_code) {
        this.confirmation_code = confirmation_code;
    }

    public String getTypetransaction() {
        return typetransaction;
    }

    public void setTypetransaction(String typetransaction) {
        this.typetransaction = typetransaction;
    }
}
