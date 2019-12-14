package com.example.abdalazez.qar.Control.Notice;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.abdalazez.qar.Control.VolleyRequests;
import com.example.abdalazez.qar.Model.Notification;
import com.example.abdalazez.qar.R;

import java.util.ArrayList;
import java.util.List;

public class LogNotification extends AppCompatActivity {

    ArrayList<Notification> dataArray;
    ArrayList<Integer> dataArrayID;
    private ListView listView;
    private NotifiAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_notification);

        SharedPreferences pref = getSharedPreferences("UserIsRegister",MODE_PRIVATE);
        int id = pref.getInt("UserID",-1);

        dataArrayID = new ArrayList<>();

        listView = findViewById(R.id.listNotice);

        new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
            @Override
            public void onDataReceivedA(ArrayList o) {
                dataArray = o;
                if (o != null) {
                    mAdapter = new NotifiAdapter(LogNotification.this,dataArray);
                    listView.setAdapter(mAdapter);
                    for (int i = 0; i < dataArray.size(); i++) {
                        dataArrayID.add(dataArray.get(i).getId());
                    }

                    new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
                        @Override
                        public void onDataReceivedA(ArrayList o) {
                            dataArray = o;
                            if (o != null) {
                                mAdapter = new NotifiAdapter(LogNotification.this,dataArray);
                                listView.setAdapter(mAdapter);
                            }
                        }
                    }).doRequestForSeenNotification(dataArrayID,"");

                }
            }
        }).doRequestForMyNotification(""+id);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancelAll();

    }

    public class NotifiAdapter extends ArrayAdapter<Notification> {

        private Context mContext;
        private List<Notification> moviesList = new ArrayList<>();

        public NotifiAdapter(@NonNull Context context, ArrayList<Notification> list) {
            super(context, 0 , list);
            mContext = context;
            moviesList = list;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listItem = convertView;
            if(listItem == null)
                listItem = LayoutInflater.from(mContext).inflate(R.layout.list_notice,parent,false);

            Notification currentMovie = moviesList.get(position);

            TextView from = (TextView) listItem.findViewById(R.id.fromN);
            from.setText(currentMovie.getName());

            TextView type = (TextView) listItem.findViewById(R.id.typeN);
            type.setText(currentMovie.getNotificationType());

            TextView date = (TextView) listItem.findViewById(R.id.dateN);
            date.setText(currentMovie.getCreated_at());

            TextView content = (TextView) listItem.findViewById(R.id.contentN);
            content.setText(currentMovie.getContent());

            return listItem;
        }
    }
}
