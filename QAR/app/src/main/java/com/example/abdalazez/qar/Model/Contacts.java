package com.example.abdalazez.qar.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ABD ALAZEZ on 04/06/2018.
 */

public class Contacts {

    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("mobile")
    @Expose
    String mobile;

    public Contacts(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
