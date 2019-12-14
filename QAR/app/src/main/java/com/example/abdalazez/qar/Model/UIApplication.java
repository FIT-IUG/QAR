package com.example.abdalazez.qar.Model;

import android.app.Application;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by ABD ALAZEZ on 27/04/2018.
 */

public class UIApplication extends Application {

    public static UIApplication anInstance = null;
    RequestQueue requestQueue;
    @Override
    public void onCreate() {
        super.onCreate();
        anInstance = this;
        requestQueue = Volley.newRequestQueue(this);
    }

    public synchronized void addToRequestQueue(Request request){
        getRequestQueue().add(request);
    }

    public void cancel(String tag){
        requestQueue.cancelAll(tag);
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public static UIApplication getAnInstance(){
        return anInstance;
    }

}
