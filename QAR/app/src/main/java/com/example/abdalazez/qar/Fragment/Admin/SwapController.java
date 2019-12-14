package com.example.abdalazez.qar.Fragment.Admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdalazez.qar.Control.VolleyRequests;
import com.example.abdalazez.qar.Model.AllUser;
import com.example.abdalazez.qar.R;

import java.util.ArrayList;

public class SwapController extends AppCompatActivity {
    private LinearLayout llContainer;
    private EditText etSearch;
    private ListView lvProducts;

    private ArrayList<AllUser> mProductArrayList = new ArrayList<>();
    private SwapController.MyAdapter adapter1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swap_controller);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSelectCont);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle("Select Controller");

        etSearch = (EditText) findViewById(R.id.SearchNameSwap);
        lvProducts = (ListView) findViewById(R.id.searchSwap);

        new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
            @Override
            public void onDataReceivedA(ArrayList o) {
                mProductArrayList = o;
                if (o != null) {
                    Toast.makeText(SwapController.this, ""+mProductArrayList.get(0).getName(), Toast.LENGTH_SHORT).show();
                    adapter1 = new SwapController.MyAdapter(SwapController.this, mProductArrayList);
                    lvProducts.setAdapter(adapter1);
                }
            }
        }).doRequestForAllUser();

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

        private ArrayList<AllUser> mOriginalValues; // Original Values
        private ArrayList<AllUser> mDisplayedValues;    // Values to be displayed
        LayoutInflater inflater;
        Context context;
        public MyAdapter(Context context, ArrayList<AllUser> mProductArrayList) {
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

            SwapController.MyAdapter.ViewHolder holder = null;

            if (convertView == null) {

                holder = new SwapController.MyAdapter.ViewHolder();
                convertView = inflater.inflate(R.layout.row_contacts, null);
                holder.llContainer = (LinearLayout) convertView.findViewById(R.id.llContainer);
                holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
                holder.tvPrice = (TextView) convertView.findViewById(R.id.tvMobile);
                convertView.setTag(holder);
            } else {
                holder = (SwapController.MyAdapter.ViewHolder) convertView.getTag();
            }
            holder.tvName.setText(mDisplayedValues.get(position).getName());
            holder.tvPrice.setText(mDisplayedValues.get(position).getId());

            holder.llContainer.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    Toast.makeText(context, mDisplayedValues.get(position).getName()+"||"+mDisplayedValues.get(position).getId(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context,Swap.class);
                    intent.putExtra("UserIdForExam",mDisplayedValues.get(position).getId());
                    context.startActivity(intent);
                    finish();
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

                    mDisplayedValues = (ArrayList<AllUser>) results.values; // has the filtered values
                    notifyDataSetChanged();  // notifies the data with new filtered values
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                    ArrayList<AllUser> FilteredArrList = new ArrayList<AllUser>();

                    if (mOriginalValues == null) {
                        mOriginalValues = new ArrayList<AllUser>(mDisplayedValues); // saves the original data in mOriginalValues
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
                                FilteredArrList.add(new AllUser(mOriginalValues.get(i).getId(),mOriginalValues.get(i).getName()));
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