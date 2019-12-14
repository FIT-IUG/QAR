package com.example.abdalazez.qar.Fragment.Admin;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.abdalazez.qar.Model.AttendanceStudant;
import com.example.abdalazez.qar.R;

import java.util.ArrayList;

/**
 * Created by ABD ALAZEZ on 24/06/2018.
 */

public class ShowStudentsAdapter extends RecyclerView.Adapter<ShowStudentsAdapter.ViewHolder> {

    private ArrayList<AttendanceStudant> dataItem;
    private ArrayList<AttendanceStudant> AllStudents;
    public ArrayList<AttendanceStudant> itemSelect = new ArrayList<>();
    Context context;
    private ActionMode mActionmode;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageViewShowStu;
        public TextView textShowStu;
        public LinearLayout colorShowStudents;

        public ViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            imageViewShowStu = itemLayoutView.findViewById(R.id.imageViewShowStu);
            textShowStu = itemLayoutView.findViewById(R.id.textShowStu);
            colorShowStudents = itemLayoutView.findViewById(R.id.colorShowStudents);

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

    public ShowStudentsAdapter(ArrayList<AttendanceStudant> dataItem) {
        this.dataItem = dataItem;
    }

    public ShowStudentsAdapter(ArrayList<AttendanceStudant> dataItem,ArrayList<AttendanceStudant> AllStudents ,Context context) {
        this.dataItem = dataItem;
        this.AllStudents = AllStudents;
        this.context = context;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ShowStudentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                ShowStudentsAdapter.ViewHolder viewHolder;
                View itemLayoutView1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_show_students, null);
                viewHolder = new ShowStudentsAdapter.ViewHolder(itemLayoutView1);
                return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ShowStudentsAdapter.ViewHolder holder,  int position) {

        for (int i = 0; i < dataItem.size(); i++) {
            if (dataItem.get(i).getStd_id().equalsIgnoreCase(AllStudents.get(position).getStd_id())) {
                holder.colorShowStudents.setBackgroundResource(R.color.Green);
                break;
            }else {
                holder.colorShowStudents.setBackgroundResource(R.color.Red);
            }
        }
            Glide.with(context)
                    .load(AllStudents.get(position).getStd_picture())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(holder.imageViewShowStu);
            holder.textShowStu.setText(AllStudents.get(position).getStd_name());
    }

    @Override
    public int getItemCount() {
        return AllStudents.size();
    }

    public ArrayList<AttendanceStudant> getItemSelect() {
        return itemSelect;
    }
}
