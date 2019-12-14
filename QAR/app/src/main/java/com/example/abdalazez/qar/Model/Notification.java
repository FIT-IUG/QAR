package com.example.abdalazez.qar.Model;

import java.io.Serializable;

/**
 * Created by ABD ALAZEZ on 08/05/2018.
 */

public class Notification implements Serializable{

    public int id;
    public String sender_id;
    public String receiver_id;
    public String notificationType;
    public String content;
    public String hide;
    public String seen;
    public String hideadmin;
    public String state;
    public String created_at;
    public String updated_at;
    public String name;
    public String mobile;
    public String type;
    public String image;
    public String remember_token;

    public Notification() {}

    public Notification(String notificationType, String content, String created_at, String name) {
        this.notificationType = notificationType;
        this.content = content;
        this.created_at = created_at;
        this.name = name;

    }

    public Notification(String notificationType, String content, String created_at,int id) {
        this.notificationType = notificationType;
        this.content = content;
        this.created_at = created_at;
        this.id = id;
    }

    public Notification(int id, String sender_id, String receiver_id, String notificationType, String content, String hide, String seen, String hideadmin, String state, String created_at, String updated_at, String name, String mobile, String type, String image, String remember_token) {
        this.id = id;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.notificationType = notificationType;
        this.content = content;
        this.hide = hide;
        this.seen = seen;
        this.hideadmin = hideadmin;
        this.state = state;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.name = name;
        this.mobile = mobile;
        this.type = type;
        this.image = image;
        this.remember_token = remember_token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHide() {
        return hide;
    }

    public void setHide(String hide) {
        this.hide = hide;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public String getHideadmin() {
        return hideadmin;
    }

    public void setHideadmin(String hideadmin) {
        this.hideadmin = hideadmin;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRemember_token() {
        return remember_token;
    }

    public void setRemember_token(String remember_token) {
        this.remember_token = remember_token;
    }
}