package com.example.abdalazez.qar.Fragment.Controller;

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

import com.example.abdalazez.qar.Model.Notification;
import com.example.abdalazez.qar.R;

import java.util.ArrayList;

/**
 * Created by ABD ALAZEZ on 21/06/2018.
 */

public class BroadcastControllerAdapter extends RecyclerView.Adapter<BroadcastControllerAdapter.ViewHolder> {

    private ArrayList<Notification> dataItem;
    public  ArrayList<Notification> itemSelect = new ArrayList<>();
    //List<Integer> itemViewSelect = new ArrayList<>();
    Context context;
    private ActionMode mActionmode;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView type;
        public TextView content;
        public TextView date;
        //public TextView state;
        public CardView cardView;

        public ViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            type = itemLayoutView.findViewById(R.id.typeReq);
            content = itemLayoutView.findViewById(R.id.contentReq);
            date = itemLayoutView.findViewById(R.id.dateReq);
            //state = itemLayoutView.findViewById(R.id.stateReq);
            cardView = itemLayoutView.findViewById(R.id.cardViewR);
            //itemViewSelect.add(0, -1);

            itemLayoutView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(v.getContext(), "Position LongClick is " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    //itemViewSelect.add(0,getAdapterPosition());
                    SharedPreferences pref = context.getSharedPreferences("itemSelectnotyIDR", context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("itemSelectnotyIDR",getAdapterPosition());
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
//Toast.makeText(context, "Num"+dataItem.get((dataItem.size()-1)-(getAdapterPosition()+1)).getId(), Toast.LENGTH_LONG).show();
                       /* new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
                            @Override
                            public void onDataReceivedA(ArrayList o) {
                            }
                        }).doRequestForRemoveNotificaitonUser(dataItem.get((dataItem.size()-1)-(getAdapterPosition()+1)).getId(),"");*/
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

    public BroadcastControllerAdapter(ArrayList<Notification> dataItem) {
        this.dataItem = dataItem;
    }

    public BroadcastControllerAdapter(ArrayList<Notification> dataItem, Context context) {

        this.dataItem = dataItem;
        this.context = context;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public BroadcastControllerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_broadcast, null);
        BroadcastControllerAdapter.ViewHolder viewHolder = new BroadcastControllerAdapter.ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final BroadcastControllerAdapter.ViewHolder holder, final int position) {

        holder.type.setText(dataItem.get((dataItem.size() - 1) - position).getNotificationType());
        holder.content.setText(dataItem.get((dataItem.size() - 1) - position).getContent());
        holder.date.setText(dataItem.get((dataItem.size() - 1) - position).getCreated_at());
        //holder.state.setText(dataItem.get((dataItem.size() - 1) - position).getState());

        SharedPreferences pref = context.getSharedPreferences("itemSelectnotyIDR",context.MODE_PRIVATE);
        int id = pref.getInt("itemSelectnotyIDR",-1);

        if (id == holder.getAdapterPosition()) {
            holder.cardView.setBackgroundColor(Color.LTGRAY);
            SharedPreferences preff = context.getSharedPreferences("itemSelectnotyIDR", context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preff.edit();
            editor.putInt("itemSelectnotyIDR",-1);
            editor.commit();
        } else {
            holder.cardView.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        int x=0;
        for (int i = 0; i < dataItem.size(); i++) {
            if(dataItem.get(i).getHide().equalsIgnoreCase("0")){
                x++;
            }else{
                dataItem.remove(i);
            }
        }
        return x;
    }

    public ArrayList<Notification> getItemSelect() {
        return itemSelect;
    }
}