package com.insthub.ecmobilemanager.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.insthub.BeeFramework.fragment.BaseFragment;
import com.insthub.ecmobilemanager.R;

/**
 * Created by Administrator on 2016/5/8 0008.
 */
public class U0_ListFragment extends BaseFragment  {
    private View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.u0_user_listview, null);
        return view;
    }
}
