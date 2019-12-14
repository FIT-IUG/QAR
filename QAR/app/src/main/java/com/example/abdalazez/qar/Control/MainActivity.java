package com.example.abdalazez.qar.Control;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.abdalazez.qar.Control.NFC.NFCinterface;
import com.example.abdalazez.qar.Control.NFC.PillowNfcManager;
import com.example.abdalazez.qar.Control.NFC.WriteTagHelper;
import com.example.abdalazez.qar.Control.Notice.MyService;
import com.example.abdalazez.qar.Fragment.Admin.BroadcastAdmin;
import com.example.abdalazez.qar.Fragment.Controller.BroadcastController;
import com.example.abdalazez.qar.R;
import com.example.abdalazez.qar.Sidemenu.SettingsApp;

import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private int[] tabUserIcons = {R.drawable.ic_nfc_check,R.drawable.ic_tables,R.drawable.ic_requests};
    private int[] tabAdminIcons = {R.drawable.ic_notifications,R.drawable.ic_settings};
    private String[] tabUser = { "Registration", "Table" , "Requests" };
    private String[] tabAdmin = { "Notifications", "Settings"};
    private int count;
    String typeUser;
    boolean isconn = true;
    private static NFCinterface mNFCinterface;
    ////////////////////////////////////////////////
    PillowNfcManager nfcManager;
    WriteTagHelper writeHelper;
    String s = "";

    public static void setNFCInterfaceListner(NFCinterface mmNFCinterface){
        mNFCinterface = mmNFCinterface;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Make to run your application only in portrait mode
        setContentView(R.layout.activity_main2);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
        }else {
            final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
            //Asking request Permissions
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 9);
        }
//////////////////////////////////////////////////////////////////////////////////////

        SharedPreferences pref = getSharedPreferences("UserIsRegister",MODE_PRIVATE);
        String image = pref.getString("UserImage","");
        String name = pref.getString("UserNmae","");
        typeUser = pref.getString("UserRType","");
        int id = pref.getInt("UserID",-1);
        boolean notifi = pref.getBoolean("UserRNotification",true);

////Service Notification
        if(notifi) {
            Intent intent = new Intent(MainActivity.this, MyService.class);
            intent.putExtra("ReqUserID", id);
            startService(intent);
            Calendar cal = Calendar.getInstance();
            PendingIntent pintent = PendingIntent.getService(MainActivity.this, 0, intent, 0);
            AlarmManager alarm = (AlarmManager) getSystemService(this.ALARM_SERVICE);
            alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 3000, pintent);
        }

/////////////////////////////////////////////////////////////////////////////////////////
        try {
            isconn = isInternetAvailable();
            System.out.println("Connnnn is:"+isconn);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while(isconn) {
                        try {
                            isconn = isInternetAvailable();
                            System.out.println("Connnnn is:"+isconn);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
*/

        count = getIntent().getIntExtra("typeUser",-1);
        //Toast.makeText(this,"count"+count,Toast.LENGTH_LONG).show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        ViewPager pager = (ViewPager)findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),this , count));
        tabLayout.setupWithViewPager(pager);

        for (int i = 0; i<count ; i++) {
            //tabLayout.addTab(tabLayout.newTab().setText(tabText[i]).setIcon(getResources().getDrawable(tabIcons[i]))
            if (count == 3) {
                tabLayout.getTabAt(i).setIcon(getResources().getDrawable(tabUserIcons[i]));
                MainActivity.this.getSupportActionBar().setTitle(tabUser[0]);

            }else{
                tabLayout.getTabAt(i).setIcon(getResources().getDrawable(tabAdminIcons[i]));
                MainActivity.this.getSupportActionBar().setTitle(tabAdmin[0]);
            }
        }

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Toast.makeText(MainActivity.this,"P1",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPageSelected(int position) {
                //Toast.makeText(MainActivity.this,"P"+position,Toast.LENGTH_LONG).show();
                for (int i = 0; i<count ; i++) {
                    //tabLayout.addTab(tabLayout.newTab().setText(tabText[i]).setIcon(getResources().getDrawable(tabIcons[i]))
                    if (count == 3) {
                        MainActivity.this.getSupportActionBar().setTitle(tabUser[position]);
                    }else{
                        MainActivity.this.getSupportActionBar().setTitle(tabAdmin[position]);
                        //((AppCompatActivity)context).getSupportActionBar().setTitle(tabAdmin[position]);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Toast.makeText(MainActivity.this,"P3",Toast.LENGTH_LONG).show();
            }
        });
        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);

        ImageView imageuser = view.findViewById(R.id.imageuser);
        TextView nameuser = view.findViewById(R.id.nameUser);
        TextView userid = view.findViewById(R.id.userId);

        if(typeUser.equalsIgnoreCase("admin")){
            navigationView.getMenu().findItem(R.id.navbar_sendSms).setTitle("Broadcast notices");

        }else if(typeUser.equalsIgnoreCase("teacher")){
            navigationView.getMenu().findItem(R.id.navbar_sendSms).setTitle("Notifications");
        }
        //menu.getItem(R.id.navbar_sendSms).setTitle(""+typeUser);

        //Toast.makeText(MainActivity.this,"///"+image,Toast.LENGTH_LONG).show();
        Glide.with(this)
                .load(image)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageuser);
        nameuser.setText("Name: "+name);
        userid.setText("ID: "+id);

        /////////////////////////////////////////////////////////////////////////////////////////////////

        int permission1 = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.NFC);
        if (permission1 == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.NFC}, 1);
        }
        nfcManager = new PillowNfcManager(MainActivity.this);
        nfcManager.onActivityCreate();

        nfcManager.setOnTagReadListener(new PillowNfcManager.TagReadListener() {
            @Override
            public void onTagRead(String tagRead) {
                mNFCinterface.OnIDRecived(tagRead);
                Toast.makeText(MainActivity.this, "tag read QAR:"+tagRead, Toast.LENGTH_LONG).show();
                //textStudentID.setText(tagRead);
            }
        });

        //writeHelper= new WriteTagHelper(MainActivity.this, nfcManager);
        //nfcManager.setOnTagWriteErrorListener(writeHelper);
        //nfcManager.setOnTagWriteListener(writeHelper);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sync) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
/*

        if (id == R.id.navbar_Sms) {
            // Handle the camera action
            Intent intent = new Intent(MainActivity.this, ReceivingSms.class);
            startActivity(intent);
*/
          if (id == R.id.navbar_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsApp.class);
            startActivity(intent);
        } else if (id == R.id.navbar_logout) {

            SharedPreferences pref = getSharedPreferences("UserIsRegister", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.commit();

            stopService(new Intent(MainActivity.this, MyService.class));

            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.navbar_sendSms) {

              if(typeUser.equalsIgnoreCase("admin")){
////////////////////////////////////////////////////////////////////////////////////////////////
                  Intent intentBroadcastAdmin = new Intent(MainActivity.this, BroadcastAdmin.class);
                  startActivity(intentBroadcastAdmin);
////////////////////////////////////////////////////////////////////////////////////////////////
              }else if(typeUser.equalsIgnoreCase("teacher")){
////////////////////////////////////////////////////////////////////////////////////////////////
                  Intent intentNotifications = new Intent(MainActivity.this, BroadcastController.class);
                  startActivity(intentNotifications);
////////////////////////////////////////////////////////////////////////////////////////////////
              }
        } else if (id == R.id.navbar_about) {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle("Quick Attendee Registration");
            builder1.setMessage("\nBy\n\nAbd Alazez Alswaisi\nAhmed Al-Hessi\nHazem Hussain\n\nSupervisor\nDr. Motaz saad \n\n\n@2014-2018");
            builder1.setIcon(R.drawable.logo);
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean isInternetAvailable() throws InterruptedException, IOException {
            String command = "ping -c 1 google.com";
            //Toast.makeText(this,"Internt is :"+(Runtime.getRuntime().exec (command).waitFor() == 0),Toast.LENGTH_LONG).show();
            return (Runtime.getRuntime().exec (command).waitFor() == 0);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Override
public void onResume() {
    super.onResume();
    nfcManager.onActivityResume();
}

    @Override
    public void onPause() {
        nfcManager.onActivityPause();
        super.onPause();
    }

    @Override
    public void onNewIntent(Intent intent){
        nfcManager.onActivityNewIntent(intent);
    }

}
