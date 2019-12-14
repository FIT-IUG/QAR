package com.example.abdalazez.qar.Control;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.abdalazez.qar.Model.AllUser;
import com.example.abdalazez.qar.Model.AttendanceStudant;
import com.example.abdalazez.qar.Model.Contacts;
import com.example.abdalazez.qar.Model.Exams;
import com.example.abdalazez.qar.Model.MLogin;
import com.example.abdalazez.qar.Model.Notification;
import com.example.abdalazez.qar.Model.Sms;
import com.example.abdalazez.qar.Model.UIApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

/**
 * Created by ABD ALAZEZ on 27/04/2018.
 */

public class VolleyRequests extends Observable {

    public interface IReceiveData {
        void onDataReceived(Object o);
    }

    public interface IReceiveDataA {
        void onDataReceivedA(ArrayList o);
    }

    private IReceiveData iReceiveData;
    private IReceiveDataA iReceiveDataA;

    public IReceiveData getIReceiveData() {
        return iReceiveData;
    }

    public IReceiveDataA getiReceiveDataA() {
        return iReceiveDataA;
    }

    public VolleyRequests setIReceiveData(IReceiveData iReceiveData) {
        this.iReceiveData = iReceiveData;
        return this;
    }

    public VolleyRequests setIReceiveDataA(IReceiveDataA iReceiveDataA) {
        this.iReceiveDataA = iReceiveDataA;
        return this;
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void doRequestForLogin(final String username, final String password) {

        StringRequest postRequest = new StringRequest(Request.Method.POST, "https://qarqar2018.000webhostapp.com/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        JsonParser jsonParser = new JsonParser();
                        JsonElement jsonElement = jsonParser.parse(response).getAsJsonObject().get("success");
                        System.out.printf("zozoweb-" + response);
                        GsonBuilder builder = new GsonBuilder();
                        Gson g = builder.create();
                        Type type = new TypeToken<MLogin>() {
                        }.getType();
                        MLogin tran = g.fromJson(jsonElement, MLogin.class);
                        //System.out.println("zozo-------"+tran.getMobile());

                        if (iReceiveData != null) {
                            iReceiveData.onDataReceived(tran);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile", username); //+6976537537864
                params.put("password", password); //123123


                return params;
            }
        };
        UIApplication.getAnInstance().addToRequestQueue(postRequest);

    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void doRequestForTable(String id, String typeUser) {

        String url = "";
        if (typeUser.equalsIgnoreCase("teacher")) {
            url = "https://qarqar2018.000webhostapp.com/myexam/";
        } else {
            url = "https://qarqar2018.000webhostapp.com/allExams/";
        }
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (url+id, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println("Response: " + response.toString());
                        GsonBuilder builder = new GsonBuilder();
                        Gson g = builder.create();
                        //Type type = new TypeToken<Exams>(){}.getType();
                        Exams tran = g.fromJson(response.toString(), Exams.class);
                        ArrayList<Exams> exams = new ArrayList<>();
                        JSONObject obj = response;
                        try {
                            JSONArray arr = obj.getJSONArray("exams");
                            for (int i = 0; i < arr.length(); i++) {
                                tran = g.fromJson(arr.getJSONObject(i).toString(), Exams.class);
                                exams.add(tran);
                                //String post_id = arr.getJSONObject(i).toString();//.getString("coursename");
                                //System.out.println("zozoweb---"+tran.getCoursename());
                            }
                            //tran.setExamsarr(exams);
                            if (iReceiveDataA != null) {
                                iReceiveDataA.onDataReceivedA(exams);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        System.out.println("Response: No");
                    }
                });

        UIApplication.getAnInstance().addToRequestQueue(jsObjRequest);

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void doRequestForRequests(String id) {
        String idNumber;
        if (id.equalsIgnoreCase("")) {
            idNumber = "https://qarqar2018.000webhostapp.com/allNotificaitons/";
        } else {
            idNumber = "https://qarqar2018.000webhostapp.com/mySendNotification/";
        }
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (idNumber + id, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println("Response: " + response.toString());
                        GsonBuilder builder = new GsonBuilder();
                        Gson g = builder.create();
                        Type type = new TypeToken<Notification>() {
                        }.getType();
                        Notification tran; //= g.fromJson(response.toString(),Notification.class);
                        ArrayList<Notification> notification = new ArrayList<>();
                        JSONObject obj = response;
                        try {
                            JSONArray arr = obj.getJSONArray("notifictions");
                            for (int i = 0; i < arr.length(); i++) {
                                tran = g.fromJson(arr.getJSONObject(i).toString(), Notification.class);
                                notification.add(tran);
                                //String post_id = arr.getJSONObject(i).toString();//.getString("coursename");
                                //System.out.println("zozoweb---"+tran.getCoursename());
                            }
                            //tran.setExamsarr(exams);
                            if (iReceiveDataA != null) {
                                iReceiveDataA.onDataReceivedA(notification);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                    }
                });

        UIApplication.getAnInstance().addToRequestQueue(jsObjRequest);

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void doRequestForMyNotification(String id) {
        String idNumber;
        idNumber = "https://qarqar2018.000webhostapp.com/myNotification/";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (idNumber + id, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println("Response ReqUserID: " + response.toString());
                        GsonBuilder builder = new GsonBuilder();
                        Gson g = builder.create();
                        Type type = new TypeToken<Notification>() {
                        }.getType();
                        Notification tran; //= g.fromJson(response.toString(),Notification.class);
                        ArrayList<Notification> notification = new ArrayList<>();
                        JSONObject obj = response;
                        try {
                            JSONArray arr = obj.getJSONArray("notifictions");
                            for (int i = 0; i < arr.length(); i++) {
                                tran = g.fromJson(arr.getJSONObject(i).toString(), Notification.class);
                                notification.add(tran);
                            }
                            if (iReceiveDataA != null) {
                                iReceiveDataA.onDataReceivedA(notification);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                    }
                });
        UIApplication.getAnInstance().addToRequestQueue(jsObjRequest);
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void doRequestForSMS(String id) {
        String idNumber;
        if (id.equalsIgnoreCase("")) {
            idNumber = "https://qarqar2018.000webhostapp.com/allSMS";
        } else {
            idNumber = "https://qarqar2018.000webhostapp.com/mySMS/";
        }
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (idNumber + id, null, new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println("Response: " + response.toString());
                        GsonBuilder builder = new GsonBuilder();
                        Gson g = builder.create();
                        //Type type = new TypeToken<Sms>(){}.getType();
                        Sms tran;//= g.fromJson(response.toString(),Sms.class);
                        ArrayList<Sms> sms = new ArrayList<>();
                        JSONObject obj = response;
                        try {
                            JSONArray arr = obj.getJSONArray("SMS");
                            for (int i = 0; i < arr.length(); i++) {
                                tran = g.fromJson(arr.getJSONObject(i).toString(), Sms.class);
                                sms.add(tran);
                                //String post_id = arr.getJSONObject(i).toString();//.getString("coursename");
                                //System.out.println("zozoweb---"+tran.getCoursename());
                            }
                            //tran.setExamsarr(exams);
                            if (iReceiveDataA != null) {
                                iReceiveDataA.onDataReceivedA(sms);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                    }
                });

        UIApplication.getAnInstance().addToRequestQueue(jsObjRequest);

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void doRequestForSendRequests(final String receiver, final String content, final String notificationType, final String token) {

        StringRequest postRequest = new StringRequest(Request.Method.POST, "https://qarqar2018.000webhostapp.com/sendNotification",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        System.out.printf(".............Response" + response);

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                        System.out.printf("Error.......Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("receiver", receiver);//"18"
                params.put("content", content);//"Hello"
                params.put("notificationType", notificationType);//+"public"

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                System.out.println("MLogin.getRemember_token:.................." + token);
                HashMap<String, String> headers = new HashMap();
                headers.put("Authorization", "Bearer" + " " + token);
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        UIApplication.getAnInstance().addToRequestQueue(postRequest);

    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void doRequestForSendSMS(final String receiver, final String content, final String token) {

        StringRequest postRequest = new StringRequest(Request.Method.POST, "https://qarqar2018.000webhostapp.com/sendSMS",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        System.out.printf(".............Response" + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                        System.out.printf("Error.......Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("receiver", receiver);//18
                params.put("content", content);//hello
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap();
                headers.put("Authorization", "Bearer" + " " + token);
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        UIApplication.getAnInstance().addToRequestQueue(postRequest);

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void doRequestForaddAttendanceStudant(final String studantID, final String examID, final String token) {

        final StringRequest postRequest = new StringRequest(Request.Method.POST, "https://qarqar2018.000webhostapp.com/addAttendanceStudant",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        System.out.printf("BBBResponse" + response);
                        ArrayList<String> arrayList = new ArrayList<>();
                        String phone = response;
                        String[] output = phone.split("&&&");
                        System.out.println(output[0]);
                        arrayList.add(0,output[0]);
                        System.out.println("TTTTTTT"+output[0]);
                        if (iReceiveDataA != null) {
                            iReceiveDataA.onDataReceivedA(arrayList);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        System.out.printf("Error.......Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("studantID", studantID);//69323768
                params.put("ExamID", examID);//5
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap();
                headers.put("Authorization", "Bearer" + " " + token);
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        UIApplication.getAnInstance().addToRequestQueue(postRequest);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void doRequestForattendanceStudant(String examID) {
//https://qarqar2018.000webhostapp.com/attendanceStudant
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                ("https://qarqar2018.000webhostapp.com/StudansInExam/" + examID, null, new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("ResponseattendanceStudant: " + response.toString());
                        GsonBuilder builder = new GsonBuilder();
                        Gson g = builder.create();
                        //Type type = new TypeToken<Exams>(){}.getType();
                        AttendanceStudant tran;// = g.fromJson(response.toString(),Exams.class);
                        ArrayList<AttendanceStudant> attendanceatudant = new ArrayList<>();
                        JSONObject obj = response;
                        try {
                            JSONArray arr = obj.getJSONArray("studants");
                            for (int i = 0; i < arr.length(); i++) {
                                tran = g.fromJson(arr.getJSONObject(i).toString(), AttendanceStudant.class);
                                attendanceatudant.add(tran);
                            }
                            if (iReceiveDataA != null) {
                                iReceiveDataA.onDataReceivedA(attendanceatudant);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        System.out.println("Response: No");
                    }
                });

        UIApplication.getAnInstance().addToRequestQueue(jsObjRequest);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void doRequestForRemoveNotificaitonAdmin(final int notificationsID, final String token) {
        final ArrayList<Integer> arr = new ArrayList<>();
        arr.add(notificationsID);
        final StringRequest postRequest = new StringRequest(Request.Method.POST, "https://qarqar2018.000webhostapp.com/removeNotificaitonAdmin",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        System.out.printf("RemoveDone" + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                        System.out.printf("RemoveDone.......Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("notifications", arr.toString());
                //System.out.println("/*/*/*/*/*/*/*"+arr.toString());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap();
                headers.put("Authorization", "Bearer" + " " + token);
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        UIApplication.getAnInstance().addToRequestQueue(postRequest);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void doRequestForRemoveSMSAdmin(String typeUset, final int smsID, final String token) {
        String api = "";
        if (typeUset.equalsIgnoreCase("teacher")) {
            api = "removeSMSUser";
        } else {
            api = "removeSMSAdmin";
        }
        final ArrayList<Integer> arr = new ArrayList<>();
        arr.add(smsID);
        final StringRequest postRequest = new StringRequest(Request.Method.POST, "https://qarqar2018.000webhostapp.com/" + api,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        System.out.printf("BBBResponse" + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                        System.out.printf("Error.......Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("sms_id", arr.toString());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap();
                headers.put("Authorization", "Bearer" + " " + token);
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        UIApplication.getAnInstance().addToRequestQueue(postRequest);
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void doRequestForRemoveNotificaitonUser(final int notificationsID, final String token) {
        final ArrayList<Integer> arr = new ArrayList<>();
        arr.add(notificationsID);
        final StringRequest postRequest = new StringRequest(Request.Method.POST, "https://qarqar2018.000webhostapp.com/removeNotificaitonUser",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        System.out.printf("BBBResponse" + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                        System.out.printf("Error.......Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("notifications", arr.toString());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap();
                headers.put("Authorization", "Bearer" + " " + token);
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        UIApplication.getAnInstance().addToRequestQueue(postRequest);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void doRequestForSeenNotification(final ArrayList<Integer> notificationsID, final String token) {

        final StringRequest postRequest = new StringRequest(Request.Method.POST, "https://qarqar2018.000webhostapp.com/seenNotification",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        System.out.printf("SeenNotification " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                        System.out.printf("Error.......Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("notifcation_id", notificationsID.toString());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap();
                headers.put("Authorization", "Bearer" + " " + token);
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        UIApplication.getAnInstance().addToRequestQueue(postRequest);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    public void doRequestForAllContacts() {

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                ("https://qarqar2018.000webhostapp.com/allContacts", null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println("Response: " + response.toString());
                        GsonBuilder builder = new GsonBuilder();
                        Gson g = builder.create();
                        Contacts tran;
                        ArrayList<Contacts> contacts = new ArrayList<>();
                        JSONObject obj = response;
                        try {
                            JSONArray arr = obj.getJSONArray("Contacts");
                            for (int i = 0; i < arr.length(); i++) {
                                tran = g.fromJson(arr.getJSONObject(i).toString(), Contacts.class);
                                contacts.add(tran);
                            }
                            if (iReceiveDataA != null) {
                                iReceiveDataA.onDataReceivedA(contacts);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        System.out.println("Response: No");
                    }
                });

        UIApplication.getAnInstance().addToRequestQueue(jsObjRequest);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void doRequestForAllUser() {

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                ("https://qarqar2018.000webhostapp.com/allContactsNames", null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println("Response: " + response.toString());
                        GsonBuilder builder = new GsonBuilder();
                        Gson g = builder.create();
                        AllUser tran;
                        ArrayList<AllUser> alluser = new ArrayList<>();
                        JSONObject obj = response;
                        try {
                            JSONArray arr = obj.getJSONArray("Contacts");
                            for (int i = 0; i < arr.length(); i++) {
                                tran = g.fromJson(arr.getJSONObject(i).toString(), AllUser.class);
                                alluser.add(tran);
                            }
                            if (iReceiveDataA != null) {
                                iReceiveDataA.onDataReceivedA(alluser);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        System.out.println("Response: No");
                    }
                });

        UIApplication.getAnInstance().addToRequestQueue(jsObjRequest);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void doRequestForShowStudent(String id_exam) {

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                ("https://qarqar2018.000webhostapp.com/attendanceStudant/"+id_exam, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println("Response: " + response.toString());
                        GsonBuilder builder = new GsonBuilder();
                        Gson g = builder.create();
                        AttendanceStudant tran;
                        ArrayList<AttendanceStudant> showstud = new ArrayList<>();
                        JSONObject obj = response;
                        try {
                            JSONArray arr = obj.getJSONArray("AttStudant");
                            for (int i = 0; i < arr.length(); i++) {
                                tran = g.fromJson(arr.getJSONObject(i).toString(), AttendanceStudant.class);
                                showstud.add(tran);
                            }
                            if (iReceiveDataA != null) {
                                iReceiveDataA.onDataReceivedA(showstud);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        System.out.println("Response: No");
                    }
                });

        UIApplication.getAnInstance().addToRequestQueue(jsObjRequest);
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void doRequestForModifiyUser(final String exam_id, final String old_user_id, final String new_user_id) {

        final StringRequest postRequest = new StringRequest(Request.Method.POST, "https://qarqar2018.000webhostapp.com/ModifiyUser",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        System.out.printf("**ModifiyUser**" + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                        System.out.printf("Error.......Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("exam_id",exam_id );
                params.put("old_user_id", old_user_id);
                params.put("new_user_id", new_user_id);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap();
                headers.put("Authorization", "Bearer" + " ");
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        UIApplication.getAnInstance().addToRequestQueue(postRequest);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void doRequestForEnterReply(final String request_id, final String reply) {

        final StringRequest postRequest = new StringRequest(Request.Method.POST, "https://qarqar2018.000webhostapp.com/addStateNotification",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        System.out.printf("**EnterReply**" + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                        System.out.printf("Error.......Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("notifcation_id",request_id );
                params.put("state", reply);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap();
                headers.put("Authorization", "Bearer" + " ");
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        UIApplication.getAnInstance().addToRequestQueue(postRequest);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void doRequestForbroadcastUsers(final String content , final String token) {
        final StringRequest postRequest = new StringRequest(Request.Method.POST, "https://qarqar2018.000webhostapp.com/broadcastUsers",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        System.out.printf("**EnterReply**" + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                        System.out.printf("Error.......Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("content", content);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap();
                headers.put("Authorization", "Bearer" + " " + token);
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        UIApplication.getAnInstance().addToRequestQueue(postRequest);
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public void doRequestForbroadcastExam(final String exam_id, final String content , final String token) {
    final StringRequest postRequest = new StringRequest(Request.Method.POST, "https://qarqar2018.000webhostapp.com/broadcastExam",
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // response
                    System.out.printf("**EnterReply**" + response);
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // error
                    Log.d("Error.Response", error.toString());
                    System.out.printf("Error.......Response", error.toString());
                }
            }
    ) {
        @Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<String, String>();
            params.put("exam_id", exam_id);
            params.put("content", content);
            return params;
        }

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap();
            headers.put("Authorization", "Bearer" + " " + token);
            headers.put("Accept", "application/json");
            return headers;
        }
    };

    UIApplication.getAnInstance().addToRequestQueue(postRequest);
}
}
