package com.example.abdalazez.qar.Model;

/**
 * Created by ABD ALAZEZ on 02/06/2018.
 */

public class SettingsItem {
    private String name;
    private int image;

    public SettingsItem(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
