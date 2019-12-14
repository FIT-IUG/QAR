package com.example.abdalazez.qar.Fragment.Admin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.abdalazez.qar.Model.SettingsItem;
import com.example.abdalazez.qar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ABD ALAZEZ on 04/06/2018.
 */

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {

    private ArrayList<SettingsItem> dataItem;
    public ArrayList<SettingsItem> itemSelect = new ArrayList<>();
    Context context;
    private ActionMode mActionmode;
    //List<View> itemViewList = new ArrayList<>();
    List<Integer> itemViewSelect = new ArrayList<>();
    public SettingsAdapter.ViewHolder viewHolder;
    public View itemLayoutView;

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
                            Intent intentAllExams = new Intent(context,AllExams.class);
                            context.startActivity(intentAllExams);
                            ////////////////////////////////////////
                            break;
                        case 1:
                            //    setContentView(R.layout.xml1);
                            Intent intentAllContacts = new Intent(context,SwapController.class);
                            context.startActivity(intentAllContacts);
                            break;
                        case 2:
                            //    setContentView(R.layout.xml1);
                            Intent intentAllContactss = new Intent(context,AllContacts.class);
                            context.startActivity(intentAllContactss);
                            break;
                        case 3:
                            //    setContentView(R.layout.xml1);
                            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.select_dialog_singlechoice);
                            arrayAdapter.add("On");
                            arrayAdapter.add("Off");
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Notifications State")
                                    .setIcon(R.drawable.ic_notifications)
                                    .setSingleChoiceItems(arrayAdapter, 0, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            Toast.makeText(context, "Selected index: " + arg1, Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    // Set the action buttons
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {
                                            int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
                                            Toast.makeText(context,"selected Position: " + selectedPosition, Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {
                                            // removes the dialog from the screen
                                        }
                                    })
                                    .show();
                            break;
                        default:
                    }

                }
            });
        }
    }

    public SettingsAdapter(ArrayList<SettingsItem> dataItem) {
        this.dataItem = dataItem;
    }

    public SettingsAdapter(ArrayList<SettingsItem> dataItem, Context context) {
        this.dataItem = dataItem;
        this.context = context;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public SettingsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_settings, null);
        viewHolder = new SettingsAdapter.ViewHolder(itemLayoutView);
        //itemViewList.add(itemLayoutView); //to add all the 'list row item' views
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final SettingsAdapter.ViewHolder holder, final int position) {
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
