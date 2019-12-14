package com.example.abdalazez.qar.Fragment.Admin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.example.abdalazez.qar.Model.Notification;
import com.example.abdalazez.qar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ABD ALAZEZ on 18/04/2018.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private ArrayList<Notification> dataItem;
    public  ArrayList<Notification> itemSelect = new ArrayList<>();
    Context context;
    private ActionMode mActionmode;
    //List<View> itemViewList = new ArrayList<>();
    List<Integer> itemViewSelect = new ArrayList<>();
    public NotificationAdapter.ViewHolder viewHolder;
    public View itemLayoutView;
    int PositionNumber;
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView from;
        public TextView type;
        public TextView date;
        public CardView cardView;

        public ViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            from = itemLayoutView.findViewById(R.id.fromNoti);
            type = itemLayoutView.findViewById(R.id.typeNoti);
            date = itemLayoutView.findViewById(R.id.dateNoti);
            cardView = itemLayoutView.findViewById(R.id.cardView);
            itemViewSelect.add(0, -1);

           //Long Press
            itemLayoutView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    PositionNumber = getAdapterPosition();
                    Toast.makeText(v.getContext(), "Position LongClick is " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    itemViewSelect.add(0,getAdapterPosition());
                    SharedPreferences pref = context.getSharedPreferences("itemSelectnotyID", context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("itemSelectnotyID",getAdapterPosition());
                    editor.commit();
                    mActionmode = ((Activity) context).startActionMode(mActionModeCallback);
                    notifyDataSetChanged();
                    return false;
                }
                });

            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemSelect.add(0,dataItem.get((dataItem.size()-1)-getAdapterPosition()));
                    Intent intent = new Intent(context,NotificationAction.class);
                    intent.putExtra("itemSelectNotifi",itemSelect.get(0));
                    context.startActivity(intent);
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
                        //Toast.makeText(context, "Num"+dataItem.get((dataItem.size()-1)-(getAdapterPosition()+1)).getId(), Toast.LENGTH_LONG).show();
                        new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
                            @Override
                            public void onDataReceivedA(ArrayList o) {
                            }
                        }).doRequestForRemoveNotificaitonAdmin(dataItem.get((dataItem.size()-1)-(PositionNumber)).getId(),"");
                        dataItem.remove(dataItem.get((dataItem.size()-1)-(PositionNumber)));
                        Toast.makeText(context, "Position LongClick is " + PositionNumber, Toast.LENGTH_SHORT).show();
                        //System.out.println(""+dataItem.size()+":::"+(dataItem.size()-1)+":::"+(getAdapterPosition()+2));
                        //Toast.makeText(context, "# :"+dataItem.get((dataItem.size()-1)-(getAdapterPosition())).getNotificationType(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(context, "Remove Successfull", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                        mode.finish();
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

    public NotificationAdapter(ArrayList<Notification> dataItem) {
        this.dataItem = dataItem;
    }

    public NotificationAdapter(ArrayList<Notification> dataItem, Context context) {
        this.dataItem = dataItem;
        this.context = context;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_notification, null);
        viewHolder = new NotificationAdapter.ViewHolder(itemLayoutView);
        //itemViewList.add(itemLayoutView); //to add all the 'list row item' views
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final NotificationAdapter.ViewHolder holder, final int position) {
            holder.from.setText(dataItem.get((dataItem.size() - 1) - position).getName());
            holder.type.setText(dataItem.get((dataItem.size() - 1) - position).getNotificationType());
            holder.date.setText(dataItem.get((dataItem.size() - 1) - position).getCreated_at());

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
        return dataItem.size();
    }

    public ArrayList<Notification> getItemSelect() {
        return itemSelect;
    }
}