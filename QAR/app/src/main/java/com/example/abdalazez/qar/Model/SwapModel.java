package com.example.abdalazez.qar.Model;

/**
 * Created by ABD ALAZEZ on 05/06/2018.
 */

public class SwapModel {
    int id;
    String nameUser;
    String nameExam;

    public SwapModel() {
    }

    public SwapModel(int id, String nameUser, String nameExam) {
        this.id = id;
        this.nameUser = nameUser;
        this.nameExam = nameExam;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getNameExam() {
        return nameExam;
    }

    public void setNameExam(String nameExam) {
        this.nameExam = nameExam;
    }
}
