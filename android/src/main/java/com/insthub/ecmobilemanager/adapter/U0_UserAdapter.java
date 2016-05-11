package com.insthub.ecmobilemanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.insthub.ecmobilemanager.R;

/**
 * Created by Administrator on 2016/5/8 0008.
 */
public class U0_UserAdapter extends ArrayAdapter<String> {

    private final String[] web;
    protected Context mContext;
    public U0_UserAdapter(Context context,
                               String[] web) {
        super(context, R.layout.u0_user, web);
        this.web = web;

    }
    public int imageNotifcation=0;
    //public android.support.v7.widget.SwitchCompat imag;
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View rowView =LayoutInflater.from(mContext).inflate(R.layout.u0_user, null);



        return rowView;
    }
}
