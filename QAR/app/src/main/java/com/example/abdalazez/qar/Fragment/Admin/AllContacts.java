package com.example.abdalazez.qar.Fragment.Admin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdalazez.qar.Control.VolleyRequests;
import com.example.abdalazez.qar.Model.Contacts;
import com.example.abdalazez.qar.R;

import java.util.ArrayList;

public class AllContacts extends AppCompatActivity {
    private LinearLayout llContainer;
    private EditText etSearch;
    private ListView lvProducts;

    private ArrayList<Contacts> mProductArrayList = new ArrayList<Contacts>();
    private MyAdapter adapter1;
    boolean isselect = false;
    String selectReq ="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_contacts);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAllContacts);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle("All Contacts");


        etSearch = (EditText) findViewById(R.id.SearchName);
        lvProducts = (ListView) findViewById(R.id.searchMobile);

        new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
            @Override
            public void onDataReceivedA(ArrayList o) {
                mProductArrayList = o;
                if (o != null) {
                    Toast.makeText(AllContacts.this, ""+mProductArrayList.get(0).getMobile(), Toast.LENGTH_SHORT).show();
                    //System.out.println("FFFFFFFFFFF"+mProductArrayList.get(0).getMobile());
                    adapter1 = new MyAdapter(AllContacts.this, mProductArrayList);
                    lvProducts.setAdapter(adapter1);
                }
            }
        }).doRequestForAllContacts();
        /*
        mProductArrayList.add(new Contacts("a", "0599745755"));
        mProductArrayList.add(new Contacts("b", "0599745755"));
        mProductArrayList.add(new Contacts("c", "0599745755"));
        mProductArrayList.add(new Contacts("d", "0599745755"));
        mProductArrayList.add(new Contacts("e", "0599745755"));
        mProductArrayList.add(new Contacts("f", "0599745755"));
        mProductArrayList.add(new Contacts("g", "0599745755"));
        mProductArrayList.add(new Contacts("h", "0599745755"));
        mProductArrayList.add(new Contacts("i", "0599745755"));
        mProductArrayList.add(new Contacts("j", "0599745755"));
        mProductArrayList.add(new Contacts("k", "0599745755"));
        mProductArrayList.add(new Contacts("l", "0599745755"));
*/
        //adapter1 = new MyAdapter(AllContacts.this, mProductArrayList);
        //lvProducts.setAdapter(adapter1);

        // Add Text Change Listener to EditText
        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
                if (adapter1 != null) {
                    adapter1.getFilter().filter(s.toString());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }


    // Adapter Class
    public class MyAdapter extends BaseAdapter implements Filterable {

        private ArrayList<Contacts> mOriginalValues; // Original Values
        private ArrayList<Contacts> mDisplayedValues;    // Values to be displayed
        LayoutInflater inflater;
        Context context;
        public MyAdapter(Context context, ArrayList<Contacts> mProductArrayList) {
            this.mOriginalValues = mProductArrayList;
            this.mDisplayedValues = mProductArrayList;
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mDisplayedValues.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder {
            LinearLayout llContainer;
            TextView tvName, tvPrice;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if (convertView == null) {

                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.row_contacts, null);
                holder.llContainer = (LinearLayout) convertView.findViewById(R.id.llContainer);
                holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
                holder.tvPrice = (TextView) convertView.findViewById(R.id.tvMobile);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvName.setText(mDisplayedValues.get(position).getName());
            holder.tvPrice.setText(mDisplayedValues.get(position).getMobile() + "");

            holder.llContainer.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    /////////////////////////////////////////////////////////////////////////////
                    //View vewInflater = LayoutInflater.from(AllContacts.this).inflate(R.layout.dialog_requests, (ViewGroup) getView(), false);
                    //LayoutInflater inflater =(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = inflater.inflate(R.layout.dialog_call, null);
                    RadioGroup radioGroup = view.findViewById(R.id.radioGroupCall);
                    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @SuppressLint("ResourceType")
                        @Override
                        public void onCheckedChanged(RadioGroup radioGroup, int i) {
                            isselect = true;
                            switch (i){
                                case R.id.radioButtonCall :
                                    selectReq = "Call";
                                    break;
                                case R.id.radioButtonSms :
                                    selectReq = "Sms";
                                    break;
                            }
                        }
                    });
                    new AlertDialog.Builder(AllContacts.this)
                            .setIcon(R.drawable.ic_menu_send)
                            .setTitle("Send Requests")
                            .setView(view)
                            .setCancelable(false)
                            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(final DialogInterface dialogInterface, int i) {
                                    if(isselect && !selectReq.isEmpty()) {
                                        if(selectReq.equalsIgnoreCase("Call")){
                                            Toast.makeText(AllContacts.this, "Done", Toast.LENGTH_LONG).show();
                                            isselect =false;
                                            Toast.makeText(context, mDisplayedValues.get(position).getName(), Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(Intent.ACTION_CALL);
                                            intent.setData(Uri.parse("tel:" + mDisplayedValues.get(position).getMobile()));
                                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                Toast.makeText(context,"Don't have permission for Calling", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            context.startActivity(intent);
                                        }else {
                                            ///////////////////////////////////////////////////////////
                                            //startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms",mDisplayedValues.get(position).getMobile(), null)));
                                            //SmsManager smsManager =     SmsManager.getDefault();
                                            //smsManager.sendTextMessage("Phone Number", null, "Message", null, null);

                                            //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + mDisplayedValues.get(position).getMobile()));
                                            //intent.putExtra("sms_body", "Hello");
                                            //startActivity(intent);

                                            Intent intentSendMsg = new Intent(AllContacts.this,SmsAction.class);
                                            intentSendMsg.putExtra("NumberPhoneForSms",mDisplayedValues.get(position).getMobile());
                                            intentSendMsg.putExtra("NumberPhoneForSms",mDisplayedValues.get(position).getMobile());
                                            startActivity(intentSendMsg);
                                            ///////////////////////////////////////////////////////////
                                        }
                                        Toast.makeText(AllContacts.this, "Done", Toast.LENGTH_LONG).show();
                                        isselect =false;
                                    }else{
                                        Toast.makeText(AllContacts.this, "Select one case", Toast.LENGTH_LONG).show();
                                    }
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                }
            });

            return convertView;
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {

                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint,FilterResults results) {

                    mDisplayedValues = (ArrayList<Contacts>) results.values; // has the filtered values
                    notifyDataSetChanged();  // notifies the data with new filtered values
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                    ArrayList<Contacts> FilteredArrList = new ArrayList<Contacts>();

                    if (mOriginalValues == null) {
                        mOriginalValues = new ArrayList<Contacts>(mDisplayedValues); // saves the original data in mOriginalValues
                    }

                    /********
                     *
                     *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                     *  else does the Filtering and returns FilteredArrList(Filtered)
                     *
                     ********/
                    if (constraint == null || constraint.length() == 0) {

                        // set the Original result to return
                        results.count = mOriginalValues.size();
                        results.values = mOriginalValues;
                    } else {
                        constraint = constraint.toString().toLowerCase();
                        for (int i = 0; i < mOriginalValues.size(); i++) {
                            String data = mOriginalValues.get(i).getName();
                            if (data.toLowerCase().startsWith(constraint.toString())) {
                                FilteredArrList.add(new Contacts(mOriginalValues.get(i).getName(),mOriginalValues.get(i).getMobile()));
                            }
                        }
                        // set the Filtered result to return
                        results.count = FilteredArrList.size();
                        results.values = FilteredArrList;
                    }
                    return results;
                }
            };
            return filter;
        }
    }
}