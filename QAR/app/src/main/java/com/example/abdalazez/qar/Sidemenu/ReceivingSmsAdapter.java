package com.example.abdalazez.qar.Sidemenu;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdalazez.qar.Control.VolleyRequests;
import com.example.abdalazez.qar.Model.Sms;
import com.example.abdalazez.qar.R;

import java.util.ArrayList;

/**
 * Created by ABD ALAZEZ on 02/06/2018.
 */

public class ReceivingSmsAdapter extends RecyclerView.Adapter<ReceivingSmsAdapter.ViewHolder> {

    private ArrayList<Sms> dataItem;
    public  ArrayList<Sms> itemSelect = new ArrayList<>();
    Context context;
    String typeUser;
    //String smsReq = "";
    private ActionMode mActionmode;
    //List<View> itemViewList = new ArrayList<>();
    //List<Integer> itemViewSelect = new ArrayList<>();
    public ReceivingSmsAdapter.ViewHolder viewHolder;
    public View itemLayoutView;
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView date;
        public TextView msg;
        public CardView cardView;

        public ViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            name = itemLayoutView.findViewById(R.id.nameSms);
            date = itemLayoutView.findViewById(R.id.dateSms);
            msg = itemLayoutView.findViewById(R.id.contentSms);
            cardView = itemLayoutView.findViewById(R.id.cardView);
            //itemViewSelect.add(0, -1);

            //Long Press
            itemLayoutView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(v.getContext(), "Position LongClick is " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    //itemViewSelect.add(0,getAdapterPosition());
                    SharedPreferences pref = context.getSharedPreferences("itemSelectnotyID", context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("itemSelectnotyID",getAdapterPosition());
                    editor.commit();
                    mActionmode = ((Activity) context).startActionMode(mActionModeCallback);
                    notifyDataSetChanged();
                    return false;
                }
            });

        }


        private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                ((Activity) context).getMenuInflater().inflate(R.menu.menu_action, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }
            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    /*case R.id.edit:
                        Toast.makeText(context, "No Action", Toast.LENGTH_SHORT).show();
                        mode.finish();
                        break;*/
                    case R.id.delete:
                        Toast.makeText(context, "Num:"+getAdapterPosition(), Toast.LENGTH_LONG).show();
                        SharedPreferences pref = context.getSharedPreferences("UserIsRegister",context.MODE_PRIVATE);
                        String typeUser = pref.getString("UserRType","");
                        int id = pref.getInt("UserID",-1);
                        new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
                            @Override
                            public void onDataReceivedA(ArrayList o) {
                            }
                        }).doRequestForRemoveSMSAdmin(typeUser,dataItem.get((dataItem.size()-1)-(getAdapterPosition()+1)).getId(),"");
                        /*
                        if(typeUser.equalsIgnoreCase("teacher")){
                            smsReq = ""+id;
                        }else {
                            smsReq ="";
                        }
                        new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
                            @Override
                            public void onDataReceivedA(ArrayList o) {
                                dataItem =  o;

                            }
                        }).doRequestForSMS(smsReq);
                        */
                        Toast.makeText(context, "Remove Successfull", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();                        mode.finish();
                        break;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                mActionmode = null;
                notifyDataSetChanged();
            }
        };

    }

    public ReceivingSmsAdapter(ArrayList<Sms> dataItem) {
        this.dataItem = dataItem;
    }

    public ReceivingSmsAdapter(ArrayList<Sms> dataItem, Context context,String typeUser) {
        this.dataItem = dataItem;
        this.context = context;
        this.typeUser = typeUser;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ReceivingSmsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_sms, null);
        viewHolder = new ReceivingSmsAdapter.ViewHolder(itemLayoutView);
        //itemViewList.add(itemLayoutView); //to add all the 'list row item' views
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ReceivingSmsAdapter.ViewHolder holder, final int position) {
        holder.name.setText(dataItem.get((dataItem.size()-1)-position).getName());
        holder.date.setText(dataItem.get((dataItem.size()-1)-position).getCreated_at());
        holder.msg.setText(dataItem.get((dataItem.size()-1)-position).getContent());


        SharedPreferences pref = context.getSharedPreferences("itemSelectnotyID",context.MODE_PRIVATE);
        int id = pref.getInt("itemSelectnotyID",-1);

        if (id == holder.getAdapterPosition()) {
            holder.cardView.setBackgroundColor(Color.LTGRAY);
            SharedPreferences preff = context.getSharedPreferences("itemSelectnotyID", context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preff.edit();
            editor.putInt("itemSelectnotyID",-1);
            editor.commit();
        } else {
            holder.cardView.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        int x=0;
        if(typeUser.equalsIgnoreCase("teacher")){
            for (int i = 0; i < dataItem.size(); i++) {
                if(dataItem.get(i).getHide() == "0"){
                    x++;
                }else{
                    dataItem.remove(i);
                }
            }
            return x;
        }else {
            return dataItem.size();
        }
    }

    public ArrayList<Sms> getItemSelect() {
        return itemSelect;
    }
}