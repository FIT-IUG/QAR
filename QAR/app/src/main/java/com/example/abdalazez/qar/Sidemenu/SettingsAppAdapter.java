package com.example.abdalazez.qar.Sidemenu;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdalazez.qar.Control.Notice.MyService;
import com.example.abdalazez.qar.Model.SettingsItem;
import com.example.abdalazez.qar.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ABD ALAZEZ on 02/06/2018.
 */

public class SettingsAppAdapter extends RecyclerView.Adapter<SettingsAppAdapter.ViewHolder> {

    private ArrayList<SettingsItem> dataItem;
    public ArrayList<SettingsItem> itemSelect = new ArrayList<>();
    Context context;
    private ActionMode mActionmode;
    //List<View> itemViewList = new ArrayList<>();
    List<Integer> itemViewSelect = new ArrayList<>();
    public SettingsAppAdapter.ViewHolder viewHolder;
    public View itemLayoutView;
    int idUserID;
    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView name;


        public ViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            imageView = itemLayoutView.findViewById(R.id.imageSettings);
            name = itemLayoutView.findViewById(R.id.nameSettings);

            //Long Press
            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch(getAdapterPosition()) {
                        case 0:
                       /////////////////////////////////////

                            SharedPreferences pref = context.getSharedPreferences("UserIsRegister",context.MODE_PRIVATE);
                            idUserID = pref.getInt("UserID",-1);
                            boolean notifi = pref.getBoolean("UserRNotification",true);
                            final int isChecked;
                            if(notifi){
                                isChecked=0;
                            }else {
                                isChecked=1;
                            }
                            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.select_dialog_singlechoice);
                            arrayAdapter.add("On");
                            arrayAdapter.add("Off");
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Notifications State")
                                    .setIcon(R.drawable.ic_notifications)
                                    .setSingleChoiceItems(arrayAdapter, isChecked, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            //Toast.makeText(context, "Selected index: " + arg1, Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    // Set the action buttons
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {
                                        int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
                                        if(selectedPosition==0) {
                                            Toast.makeText(context, "Notification is On", Toast.LENGTH_SHORT).show();
                                            SharedPreferences pref = context.getSharedPreferences("UserIsRegister", context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = pref.edit();
                                            editor.putBoolean("UserRNotification", true);
                                            editor.commit();
                                            Intent intent = new Intent(context, MyService.class);
                                            intent.putExtra("ReqUserID", idUserID);
                                            context.startService(intent);
                                            Calendar cal = Calendar.getInstance();
                                            PendingIntent pintent = PendingIntent.getService(context, 0, intent, 0);
                                            AlarmManager alarm = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
                                            alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 3000, pintent);
                                        }else if(selectedPosition==1){
                                            Toast.makeText(context,"Notification is Off", Toast.LENGTH_SHORT).show();
                                            SharedPreferences pref = context.getSharedPreferences("UserIsRegister", context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = pref.edit();
                                            editor.putBoolean("UserRNotification",false);
                                            editor.commit();
                                            Intent intent = new Intent(context, MyService.class);
                                            intent.putExtra("ReqUserID", idUserID);
                                            context.stopService(intent);
                                        }
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {
                                            // removes the dialog from the screen
                                        }
                                    })
                                    .show();
                            ////////////////////////////////////////
                            break;
                        case 1:
                        //    setContentView(R.layout.xml1);
                            break;
                        default:
                    }

                }
            });
        }
    }

    public SettingsAppAdapter(ArrayList<SettingsItem> dataItem) {
        this.dataItem = dataItem;
    }

    public SettingsAppAdapter(ArrayList<SettingsItem> dataItem, Context context) {
        this.dataItem = dataItem;
        this.context = context;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public SettingsAppAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_settings, null);
        viewHolder = new SettingsAppAdapter.ViewHolder(itemLayoutView);
        //itemViewList.add(itemLayoutView); //to add all the 'list row item' views
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final SettingsAppAdapter.ViewHolder holder, final int position) {
        holder.imageView.setBackgroundResource(dataItem.get(position).getImage());
        holder.name.setText(dataItem.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return dataItem.size();
    }

    public ArrayList<SettingsItem> getItemSelect() {
        return itemSelect;
    }
}
