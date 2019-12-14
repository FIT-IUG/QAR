package com.example.abdalazez.qar.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ABD ALAZEZ on 02/05/2018.
 */

public class AbsenteStudant {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("Std_id")
    @Expose
    private String Std_id;
    @SerializedName("exam_id")
    @Expose
    private String exam_id;
    @SerializedName("Std_name")
    @Expose
    private String Std_name;
    @SerializedName("Std_picture")
    @Expose
    private String Std_picture;
    @SerializedName("Department")
    @Expose
    private String Department;
    @SerializedName("Faculty")
    @Expose
    private String Faculty;
    @SerializedName("created_at")
    @Expose
    private String created_at;
    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStd_id() {
        return Std_id;
    }

    public void setStd_id(String std_id) {
        Std_id = std_id;
    }

    public String getExam_id() {
        return exam_id;
    }

    public void setExam_id(String exam_id) {
        this.exam_id = exam_id;
    }

    public String getStd_name() {
        return Std_name;
    }

    public void setStd_name(String std_name) {
        Std_name = std_name;
    }

    public String getStd_picture() {
        return Std_picture;
    }

    public void setStd_picture(String std_picture) {
        Std_picture = std_picture;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getFaculty() {
        return Faculty;
    }

    public void setFaculty(String faculty) {
        Faculty = faculty;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
