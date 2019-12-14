package com.example.abdalazez.qar.Control;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.abdalazez.qar.Fragment.Admin.NotificationFragment;
import com.example.abdalazez.qar.Fragment.Controller.RegistrationFragment;
import com.example.abdalazez.qar.Fragment.Controller.RequestsFragment;
import com.example.abdalazez.qar.Fragment.Admin.SettingsFragment;
import com.example.abdalazez.qar.Fragment.Controller.TablesFragment;

/**
 * Created by ABD ALAZEZ on 04/04/2018.
 */

public class MyPagerAdapter extends FragmentStatePagerAdapter {

    private final int FRAGMENT_Registration = 0;
    private final int FRAGMENT_Tables = 1;
    private final int FRAGMENT_Requests = 2;

    private final int Fragment_Notification = 0;
    private final int FRAGMENT_Settings = 1;

    private int COUNT;
    private final Context context;

    public MyPagerAdapter(FragmentManager fm, Context context , int count) {
        super(fm);
        this.context = context;
        this.COUNT = count;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if(COUNT == 3){
            switch (position){
            case FRAGMENT_Registration:
                fragment = new RegistrationFragment();
                break;
            case FRAGMENT_Tables :
                fragment = new TablesFragment();
                break;
            case FRAGMENT_Requests :
                fragment = new RequestsFragment();
                break;
        }
        }else if (COUNT == 2){
            switch (position) {
                case Fragment_Notification:
                    fragment = new NotificationFragment();
                    break;
                case FRAGMENT_Settings:
                    fragment = new SettingsFragment();
                    break;
            }
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return COUNT;
    }


/*
    @Override
    public CharSequence getPageTitle(int position) {

        String fragment = null;
        if(COUNT == 3){
            switch (position){
                case FRAGMENT_Registration:
                    fragment = context.getString(R.string.student_Registration);
                    break;
                case FRAGMENT_Tables :
                    fragment = context.getString(R.string.controller_Tables);
                    break;
                case FRAGMENT_Requests :
                fragment = context.getString(R.string.controller_Requests);
                break;
            }
        }else if (COUNT == 2){
            switch (position){
                case Fragment_Notification:
                    fragment = context.getString(R.string.admin_Notification);
                    break;
                case FRAGMENT_Settings :
                    fragment = context.getString(R.string.admin_Settings);
                    break;
                //case FRAGMENT_PROFILE :
                //fragment = context.getString(R.string.User_Profile);
                //break;
            }
        }
        return fragment;
    }
*/

}
