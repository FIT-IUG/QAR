package com.example.abdalazez.qar.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ABD ALAZEZ on 02/05/2018.
 */

public class Exams {

    @SerializedName("exam_id")
    @Expose
    private int exam_id;
    @SerializedName("room_id")
    @Expose
    private String room_id;
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("created_at")
    @Expose
    private String created_at;
    @SerializedName("updated_at")
    @Expose
    private String updated_at;
    @SerializedName("course_id")
    @Expose
    private String course_id;
    @SerializedName("teacher_id")
    @Expose
    private String teacher_id;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("semester")
    @Expose
    private String semester;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("coursename")
    @Expose
    private String coursename;
    @SerializedName("courseID")
    @Expose
    private String courseID;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("examfinished")
    @Expose
    private String examfinished;
    //private ArrayList<Exams>  examsarr =new ArrayList<>();
/*
    @SerializedName("text")
    @Expose
    private List<String> text = new ArrayList<String>();
*/

    public Exams() {
    }

    public Exams(int exam_id, String coursename) {
        this.exam_id = exam_id;
        this.coursename = coursename;
    }

    public int getExam_id() {
        return exam_id;
    }

    public void setExam_id(int exam_id) {
        this.exam_id = exam_id;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExamfinished() {
        return examfinished;
    }

    public void setExamfinished(String examfinished) {
        this.examfinished = examfinished;
    }
}
