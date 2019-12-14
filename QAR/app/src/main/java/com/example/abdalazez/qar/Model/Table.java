package com.example.abdalazez.qar.Model;

/**
 * Created by ABD ALAZEZ on 19/04/2018.
 */

public class Table {

    public String courseId;
    public String courseName;
    public String divisionNum;
    public String examDate;
    public String examTime;
    public String roomNum;

    public Table(String courseId, String courseName, String divisionNum, String examDate, String examTime, String roomNum) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.divisionNum = divisionNum;
        this.examDate = examDate;
        this.examTime = examTime;
        this.roomNum = roomNum;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDivisionNum() {
        return divisionNum;
    }

    public void setDivisionNum(String divisionNum) {
        this.divisionNum = divisionNum;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public String getExamTime() {
        return examTime;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }
}
