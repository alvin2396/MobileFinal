package skripsigame.skripsi.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Processor implements Serializable {

    @SerializedName("processor_id")
    private String processor_id;

    @SerializedName("processor_name")
    private String processor_name;

    @SerializedName("processor_score")
    private String processor_score;

    @SerializedName("id")
    private String id;

    public String getProcessor_name() {
        return processor_name;
    }

    public void setProcessor_name(String processor_name) {
        this.processor_name = processor_name;
    }

    public String getProcessor_score() {
        return processor_score;
    }

    public void setProcessor_score(String processor_score) {
        this.processor_score = processor_score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcessor_id() {
        return processor_id;
    }

    public void setProcessor_id(String processor_id) {
        this.processor_id = processor_id;
    }


}
