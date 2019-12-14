package com.example.abdalazez.qar.Control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.abdalazez.qar.Model.Exams;
import com.example.abdalazez.qar.Model.Notification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ABD ALAZEZ on 27/04/2018.
 */

public class SQL_Operations extends SQLiteOpenHelper {

    Context context;
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "QAR";

    // Notes table name
    private static final String TABLE_Attendance = "Attendance";
    private static final String TABLE_Exams = "Exams";
    private static final String TABLE_Requests = "Requests";
    private static final String TABLE_Notification = "Notification";
    private static final String TABLE_Broadcast = "Broadcast";

    // Notes Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_Std_id = "Std_id";
    private static final String KEY_exam_id = "exam_id";
    private static final String KEY_Std_name = "Std_name";
    private static final String KEY_Std_picture = "Std_picture";
    private static final String KEY_Department = "Department";
    private static final String KEY_Faculty = "Faculty";
    private static final String KEY_created_at = "created_at";

    ///////////////////////////////////////////////////////////////////////////////////////
    //private static final String KEY_exam_id = "exam_id";
    private static final String KEY_room_id = "room_id";
    private static final String KEY_user_id = "user_id";
    //private static final String KEY_created_at = "created_at";
    private static final String KEY_course_id = "course_id";
    private static final String KEY_teacher_id = "teacher_id";
    private static final String KEY_section = "section";
    private static final String KEY_semester = "semester";
    private static final String KEY_date = "date";
    private static final String KEY_duration = "duration";
    private static final String KEY_time = "time";
    //private static final String KEY_id = "id";
    private static final String KEY_coursename = "coursename";
    private static final String KEY_courseID = "courseID";
    private static final String KEY_examfinished = "examfinished";
    ///////////////////////////////////////////////////////////////////////////////////////
    private static final String KEY_sender_id = "sender_id";
    private static final String KEY_receiver_id = "receiver_id";
    private static final String KEY_notificationType = "notificationType";
    private static final String KEY_content = "content";
    private static final String KEY_hide = "hide";
    private static final String KEY_seen = "seen";
    private static final String KEY_hideadmin = "hideadmin";
    private static final String KEY_state = "state";
    //private static final String KEY_created_at = "created_at";
    private static final String KEY_name = "name";
    private static final String KEY_mobile = "mobile";
    private static final String KEY_type = "type";
    private static final String KEY_image = "image";
    private static final String KEY_remember_token = "remember_token";

    public SQL_Operations(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_Attendance_TABLE = "CREATE TABLE " + TABLE_Attendance + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_Std_id + " TEXT," + KEY_exam_id + " TEXT," + KEY_Std_name + " TEXT," + KEY_Std_picture + " TEXT," + KEY_Department + " TEXT," + KEY_Faculty + " TEXT,"
                + KEY_created_at + " TEXT" + ")";
        db.execSQL(CREATE_Attendance_TABLE);

        String CREATE_Exams_TABLE = "CREATE TABLE " + TABLE_Exams + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_exam_id + " TEXT," + KEY_room_id + " TEXT," + KEY_user_id + " TEXT," + KEY_created_at + " TEXT," + KEY_course_id + " TEXT," + KEY_teacher_id + " TEXT," + KEY_section + " TEXT,"  + KEY_semester + " TEXT," + KEY_date + " TEXT," + KEY_duration + " TEXT," + KEY_time + " TEXT," + KEY_coursename + " TEXT," +KEY_courseID + " TEXT," +KEY_name + " TEXT,"
                + KEY_examfinished + " TEXT" + ")";
        db.execSQL(CREATE_Exams_TABLE);

        String CREATE_Requests_TABLE = "CREATE TABLE " + TABLE_Requests + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_sender_id + " TEXT," + KEY_receiver_id + " TEXT," + KEY_notificationType + " TEXT," + KEY_content + " TEXT," + KEY_hide + " TEXT," + KEY_seen + " TEXT,"  + KEY_hideadmin + " TEXT,"  + KEY_state + " TEXT,"  + KEY_created_at + " TEXT," + KEY_name + " TEXT," + KEY_mobile + " TEXT," + KEY_type + " TEXT," + KEY_image + " TEXT,"
                + KEY_remember_token + " TEXT" + ")";
        db.execSQL(CREATE_Requests_TABLE);

        String CREATE_Notification_TABLE = "CREATE TABLE " + TABLE_Notification + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_sender_id + " TEXT," + KEY_receiver_id + " TEXT," + KEY_notificationType + " TEXT," + KEY_content + " TEXT," + KEY_hide + " TEXT," + KEY_seen + " TEXT," + KEY_hideadmin + " TEXT,"  + KEY_state + " TEXT," + KEY_created_at + " TEXT," + KEY_name + " TEXT," + KEY_mobile + " TEXT," + KEY_type + " TEXT," + KEY_image + " TEXT,"
                + KEY_remember_token + " TEXT" + ")";
        db.execSQL(CREATE_Notification_TABLE);

        String CREATE_Broadcast_TABLE = "CREATE TABLE " + TABLE_Broadcast + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_sender_id + " TEXT," + KEY_receiver_id + " TEXT," + KEY_notificationType + " TEXT," + KEY_content + " TEXT," + KEY_hide + " TEXT," + KEY_seen + " TEXT," + KEY_hideadmin + " TEXT,"  + KEY_state + " TEXT," + KEY_created_at + " TEXT," + KEY_name + " TEXT," + KEY_mobile + " TEXT," + KEY_type + " TEXT," + KEY_image + " TEXT,"
                + KEY_remember_token + " TEXT" + ")";
        db.execSQL(CREATE_Broadcast_TABLE);


    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Attendance);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Exams);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Notification);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Requests);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Broadcast);

        // Create tables again
        onCreate(db);
    }

    // Adding new Call
    public void addExams(ArrayList<Exams> exams,String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        if(type.equalsIgnoreCase("0")) {
            for (int i = 0; i < exams.size(); i++) {
                ContentValues values = new ContentValues();
                values.put(KEY_exam_id, exams.get(i).getExam_id());
                values.put(KEY_room_id, exams.get(i).getRoom_id());
                values.put(KEY_user_id, exams.get(i).getUser_id());
                values.put(KEY_created_at, exams.get(i).getCreated_at());
                values.put(KEY_course_id, exams.get(i).getCourse_id());
                values.put(KEY_teacher_id, exams.get(i).getTeacher_id());
                values.put(KEY_section, exams.get(i).getSection());
                values.put(KEY_semester, exams.get(i).getSemester());
                values.put(KEY_date, exams.get(i).getDate());
                values.put(KEY_duration, exams.get(i).getDuration());
                values.put(KEY_time, exams.get(i).getTime());
                //values.put(KEY_id, exams.get(i).getId());
                values.put(KEY_coursename, exams.get(i).getCoursename());
                values.put(KEY_courseID, exams.get(i).getCourseID());
                values.put(KEY_examfinished, exams.get(i).getExamfinished());
                // Inserting Row
                db.insert(TABLE_Exams, null, values);
            }
        }else if(type.equalsIgnoreCase("1")){
            for (int i = 0; i < exams.size(); i++) {
                ContentValues values = new ContentValues();
                values.put(KEY_exam_id, exams.get(i).getExam_id());
                values.put(KEY_room_id, exams.get(i).getRoom_id());
                values.put(KEY_user_id, exams.get(i).getUser_id());
                values.put(KEY_created_at, exams.get(i).getCreated_at());
                values.put(KEY_course_id, exams.get(i).getCourse_id());
                values.put(KEY_teacher_id, exams.get(i).getTeacher_id());
                values.put(KEY_section, exams.get(i).getSection());
                values.put(KEY_semester, exams.get(i).getSemester());
                values.put(KEY_date, exams.get(i).getDate());
                values.put(KEY_duration, exams.get(i).getDuration());
                values.put(KEY_time, exams.get(i).getTime());
                //values.put(KEY_id, exams.get(i).getId());
                values.put(KEY_coursename, exams.get(i).getCoursename());
                values.put(KEY_courseID, exams.get(i).getCourseID());
                values.put(KEY_name, exams.get(i).getName());
                values.put(KEY_examfinished, exams.get(i).getExamfinished());
                // Inserting Row
                db.insert(TABLE_Exams, null, values);
            }
        }
        db.close(); // Closing database connection
    }

    // Getting All Call
    public List<Exams> getAllExams(String type) {
        List<Exams> contactList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_Exams;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                if(type.equalsIgnoreCase("0")) {
                    Exams note = new Exams();
                    note.setId(cursor.getString(0));
                    note.setExam_id(Integer.parseInt(cursor.getString(1)));
                    note.setRoom_id(cursor.getString(2));
                    note.setUser_id(cursor.getString(3));
                    note.setCreated_at(cursor.getString(4));
                    note.setCourse_id(cursor.getString(5));
                    note.setTeacher_id(cursor.getString(6));
                    note.setSection(cursor.getString(7));
                    note.setSemester(cursor.getString(8));
                    note.setDate(cursor.getString(9));
                    note.setDuration(cursor.getString(10));
                    note.setTime(cursor.getString(11));
                    //note.setId(cursor.getString(10));
                    note.setCoursename(cursor.getString(12));
                    note.setCourseID(cursor.getString(13));
                    note.setExamfinished(cursor.getString(14));
                    // Adding Call to list
                    contactList.add(note);
                }else if(type.equalsIgnoreCase("1")){
                    Exams note = new Exams();
                    note.setId(cursor.getString(0));
                    note.setExam_id(Integer.parseInt(cursor.getString(1)));
                    note.setRoom_id(cursor.getString(2));
                    note.setUser_id(cursor.getString(3));
                    note.setCreated_at(cursor.getString(4));
                    note.setCourse_id(cursor.getString(5));
                    note.setTeacher_id(cursor.getString(6));
                    note.setSection(cursor.getString(7));
                    note.setSemester(cursor.getString(8));
                    note.setDate(cursor.getString(9));
                    note.setDuration(cursor.getString(10));
                    note.setTime(cursor.getString(11));
                    //note.setId(cursor.getString(10));
                    note.setCoursename(cursor.getString(12));
                    note.setCourseID(cursor.getString(13));
                    note.setName(cursor.getString(14));
                    //note.setExamfinished(cursor.getString(15));
                    // Adding Call to list
                    contactList.add(note);
                }
            } while (cursor.moveToNext());
        }
        // return Call list
        return contactList;
    }

    // Adding new Call
    public void addRequests(ArrayList<Notification> notification) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (int i = 0; i < notification.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(KEY_sender_id, notification.get(i).getSender_id());
            values.put(KEY_receiver_id, notification.get(i).getReceiver_id());
            values.put(KEY_notificationType, notification.get(i).getNotificationType());
            values.put(KEY_content, notification.get(i).getContent());
            values.put(KEY_hide, notification.get(i).getHide());
            values.put(KEY_seen, notification.get(i).getSeen());
            values.put(KEY_hideadmin, notification.get(i).getHideadmin());
            values.put(KEY_state, notification.get(i).getState());
            values.put(KEY_created_at, notification.get(i).getCreated_at());
            values.put(KEY_name, notification.get(i).getName());
            values.put(KEY_mobile, notification.get(i).getMobile());
            values.put(KEY_type, notification.get(i).getType());
            values.put(KEY_image, notification.get(i).getImage());
            values.put(KEY_remember_token, notification.get(i).getRemember_token());
            // Inserting Row
            db.insert(TABLE_Requests, null, values);
        }
        db.close(); // Closing database connection
    }

    // Getting All Call
    public List<Notification> getAllRequests() {
        List<Notification> contactList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_Requests;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to listn
        if (cursor.moveToFirst()) {
            do {
                Notification note = new Notification();
                note.setId(Integer.parseInt(cursor.getString(0)));
                note.setSender_id(cursor.getString(1));
                note.setReceiver_id(cursor.getString(2));
                note.setNotificationType(cursor.getString(3));
                note.setContent(cursor.getString(4));
                note.setHide(cursor.getString(5));
                note.setSeen(cursor.getString(6));
                note.setHideadmin(cursor.getString(7));
                note.setState(cursor.getString(8));
                note.setCreated_at(cursor.getString(9));
                note.setName(cursor.getString(10));
                note.setMobile(cursor.getString(11));
                note.setType(cursor.getString(12));
                note.setImage(cursor.getString(13));
                note.setRemember_token(cursor.getString(14));
                // Adding Call to list
                contactList.add(note);
            } while (cursor.moveToNext());
        }
        // return Call list
        return contactList;
    }

    // Adding new Call
    public void addNotification(ArrayList<Notification> notification) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (int i = 0; i <notification.size() ; i++) {
            ContentValues values = new ContentValues();
            values.put(KEY_sender_id, notification.get(i).getSender_id());
            values.put(KEY_receiver_id, notification.get(i).getReceiver_id());
            values.put(KEY_notificationType, notification.get(i).getNotificationType());
            values.put(KEY_content, notification.get(i).getContent());
            values.put(KEY_hide, notification.get(i).getHide());
            values.put(KEY_seen, notification.get(i).getSeen());
            values.put(KEY_hideadmin, notification.get(i).getHideadmin());
            values.put(KEY_state, notification.get(i).getState());
            values.put(KEY_created_at, notification.get(i).getCreated_at());
            values.put(KEY_name, notification.get(i).getName());
            values.put(KEY_mobile, notification.get(i).getMobile());
            values.put(KEY_type, notification.get(i).getType());
            values.put(KEY_image, notification.get(i).getImage());
            values.put(KEY_remember_token, notification.get(i).getRemember_token());
            // Inserting Row
            db.insert(TABLE_Notification, null, values);
        }
        db.close(); // Closing database connection
    }

    // Getting All Call
    public List<Notification> getAllNotification() {
        List<Notification> contactList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_Notification;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to listn
        if (cursor.moveToFirst()) {
            do {
                Notification note = new Notification();
                note.setId(Integer.parseInt(cursor.getString(0)));
                note.setSender_id(cursor.getString(1));
                note.setReceiver_id(cursor.getString(2));
                note.setNotificationType(cursor.getString(3));
                note.setContent(cursor.getString(4));
                note.setHide(cursor.getString(5));
                note.setSeen(cursor.getString(6));
                note.setHideadmin(cursor.getString(7));
                note.setState(cursor.getString(8));
                note.setCreated_at(cursor.getString(9));
                note.setName(cursor.getString(10));
                note.setMobile(cursor.getString(11));
                note.setType(cursor.getString(12));
                note.setImage(cursor.getString(13));
                note.setRemember_token(cursor.getString(14));
                // Adding Call to list
                contactList.add(note);
            } while (cursor.moveToNext());
        }
        // return Call list
        return contactList;
    }

    // Adding new Call
    public void addBroadcast(ArrayList<Notification> notification) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (int i = 0; i <notification.size() ; i++) {
            ContentValues values = new ContentValues();
            values.put(KEY_sender_id, notification.get(i).getSender_id());
            values.put(KEY_receiver_id, notification.get(i).getReceiver_id());
            values.put(KEY_notificationType, notification.get(i).getNotificationType());
            values.put(KEY_content, notification.get(i).getContent());
            values.put(KEY_hide, notification.get(i).getHide());
            values.put(KEY_seen, notification.get(i).getSeen());
            values.put(KEY_hideadmin, notification.get(i).getHideadmin());
            values.put(KEY_state, notification.get(i).getState());
            values.put(KEY_created_at, notification.get(i).getCreated_at());
            values.put(KEY_name, notification.get(i).getName());
            values.put(KEY_mobile, notification.get(i).getMobile());
            values.put(KEY_type, notification.get(i).getType());
            values.put(KEY_image, notification.get(i).getImage());
            values.put(KEY_remember_token, notification.get(i).getRemember_token());
            // Inserting Row
            db.insert(TABLE_Broadcast, null, values);
        }
        db.close(); // Closing database connection
    }

    // Getting All Call
    public List<Notification> getAllBroadcast() {
        List<Notification> contactList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_Broadcast;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to listn
        if (cursor.moveToFirst()) {
            do {
                Notification note = new Notification();
                note.setId(Integer.parseInt(cursor.getString(0)));
                note.setSender_id(cursor.getString(1));
                note.setReceiver_id(cursor.getString(2));
                note.setNotificationType(cursor.getString(3));
                note.setContent(cursor.getString(4));
                note.setHide(cursor.getString(5));
                note.setSeen(cursor.getString(6));
                note.setHideadmin(cursor.getString(7));
                note.setState(cursor.getString(8));
                note.setCreated_at(cursor.getString(9));
                note.setName(cursor.getString(10));
                note.setMobile(cursor.getString(11));
                note.setType(cursor.getString(12));
                note.setImage(cursor.getString(13));
                note.setRemember_token(cursor.getString(14));
                // Adding Call to list
                contactList.add(note);
            } while (cursor.moveToNext());
        }
        // return Call list
        return contactList;
    }

/*
    // Deleting single Call
    public void deleteNote(User note) {
        Toast.makeText(context,""+note.getType(),Toast.LENGTH_SHORT).show();
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Notes, KEY_ID + " = ?",
                new String[] { String.valueOf(note.getType()) });
        db.close();
    }
*/
}
