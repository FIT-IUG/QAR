package com.example.abdalazez.qar.Sidemenu;

import android.support.v7.app.AppCompatActivity;

public class ReceivingSms extends AppCompatActivity {
    /*
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    LovelyProgressDialog waiting;
    boolean isconn =true;
    String smsReq;
    String typeUser;
    ArrayList<Sms> dataArray;
    ArrayList<Sms> dataLoad = new ArrayList<>();
    SQL_Operations dbSms;
    SQLiteDatabase sdbSms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiving_sms);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSms);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle("SMS");

        mRecyclerView = findViewById(R.id.sms_RecyclerView);

        SharedPreferences pref = this.getSharedPreferences("UserIsRegister", this.MODE_PRIVATE);
        final boolean value = pref.getBoolean("UserIsRegister", false);
        int id = pref.getInt("UserID",-1);
        typeUser = pref.getString("UserRType","-1");
        if(typeUser.equalsIgnoreCase("teacher")){
            smsReq = ""+id;
        }else{
            smsReq = "";
        }

        dbSms = new SQL_Operations(ReceivingSms.this);
        sdbSms = dbSms.getWritableDatabase();

        if (dbSms.getAllSms() != null) {
            dataLoad = (ArrayList<Sms>) dbSms.getAllSms();
            mRecyclerView.setLayoutManager(new LinearLayoutManager(ReceivingSms.this));
            mAdapter = new ReceivingSmsAdapter(dataLoad, ReceivingSms.this,typeUser);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        }

        getDataNotification();

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_containerSMS);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //if(value==true) {
                isconn = true;
                Toast.makeText(ReceivingSms.this, "Refresh Done", Toast.LENGTH_LONG).show();
                final Thread thread = new Thread() {
                    @Override
                    public void run() {
                        while (isconn) {
                            try {
                                if(value==true) {
                                    boolean co = getDataNotification();
                                    if (co == true) {
                                        isconn = false;
                                    }
                                }else{
                                    Toast.makeText(ReceivingSms.this, "No id", Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                };
                thread.start();
                mSwipeRefreshLayout.setRefreshing(false);
                mSwipeRefreshLayout.stopNestedScroll();
            }
        });
        /*
        new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
            @Override
            public void onDataReceivedA(ArrayList o) {
                dataArray =  o;
                //Toast.makeText(getActivity(), "No"+dataArray.size(), Toast.LENGTH_LONG).show();
                mRecyclerView.setLayoutManager(new LinearLayoutManager(ReceivingSms.this));
                mAdapter = new ReceivingSmsAdapter(dataArray, ReceivingSms.this);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            }
        }).doRequestForSMS(smsReq);

    }
    public boolean getDataNotification(){
        new VolleyRequests().setIReceiveDataA(new VolleyRequests.IReceiveDataA() {
            @Override
            public void onDataReceivedA(ArrayList o) {
                if (o != null) {
                    dataArray = o;
                    dbSms = new SQL_Operations(ReceivingSms.this);
                    sdbSms = dbSms.getWritableDatabase();
                    sdbSms.execSQL("delete from "+ "Sms");
                    dbSms.addSms(o);
                    //Toast.makeText(getActivity(), "No"+dataArray.size(), Toast.LENGTH_LONG).show();
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(ReceivingSms.this));
                    mAdapter = new ReceivingSmsAdapter(dataArray, ReceivingSms.this,typeUser);
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                }
            }
        }).doRequestForSMS(smsReq);
        return dataArray!=null;
    }
*/
}
