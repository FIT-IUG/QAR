package com.example.abdalazez.qar.Fragment.Controller;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdalazez.qar.Model.Exams;
import com.example.abdalazez.qar.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ABD ALAZEZ on 18/04/2018.
 */

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> {

    private ArrayList<Exams> dataItem;
    public ArrayList<Exams> itemSelect = new ArrayList<>();
    Context context;
    private ActionMode mActionmode;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView courseId;
        public TextView courseName;
        public TextView divisionNum;
        public TextView examDate;
        public TextView examTime;
        public TextView roomNum;
        public TextView numberRow;

        public ViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            courseId = itemLayoutView.findViewById(R.id.courseId);
            courseName = itemLayoutView.findViewById(R.id.courseName);
            divisionNum = itemLayoutView.findViewById(R.id.divisionNum);
            examDate = itemLayoutView.findViewById(R.id.examDate);
            examTime = itemLayoutView.findViewById(R.id.examTime);
            roomNum = itemLayoutView.findViewById(R.id.roomNum);
            numberRow = itemLayoutView.findViewById(R.id.numberRow);

            //Long Press
            itemLayoutView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(v.getContext(), "Position LongClick is " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    mActionmode = ((Activity) context).startActionMode(mActionModeCallback);
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
                        Toast.makeText(context, "No Action", Toast.LENGTH_SHORT).show();
                        mode.finish();
                        break;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                mActionmode = null;
            }
        };

    }

    public TableAdapter(ArrayList<Exams> dataItem) {
        this.dataItem = dataItem;
    }

    public TableAdapter(ArrayList<Exams> dataItem, Context context) {
        this.dataItem = dataItem;
        this.context = context;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public TableAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TableAdapter.ViewHolder viewHolder;
        switch (viewType) {
            case 0:
                View itemLayoutView1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_table_name, null);
                viewHolder = new TableAdapter.ViewHolder(itemLayoutView1);
                return viewHolder;
            case 1:
                View itemLayoutView2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_table, null);
                viewHolder = new TableAdapter.ViewHolder(itemLayoutView2);
                return viewHolder;
        }
        return null;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(TableAdapter.ViewHolder holder,  int position) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        //String dateday ="1993-10-01";
        System.out.println("TimeDateToday"+dateFormat.format(date));
        if(position>0) {
    int num = position-1;
    holder.courseId.setText("" + dataItem.get(num).getCourse_id());
    holder.courseName.setText(dataItem.get(num).getCoursename());
    holder.divisionNum.setText("" + dataItem.get(num).getDuration());
    holder.examDate.setText(dataItem.get(num).getDate());
    holder.examTime.setText(dataItem.get(num).getTime());
    holder.roomNum.setText("" + dataItem.get(num).getRoom_id());
    if(dateFormat.format(date).equalsIgnoreCase(dataItem.get(num).getDate())){
        holder.numberRow.setText("" + num);
        holder.numberRow.setBackgroundResource(R.color.Red);
    }
    holder.numberRow.setText("" + num);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return dataItem.size()+1;
    }

    public ArrayList<Exams> getItemSelect() {
        return itemSelect;
    }
}
