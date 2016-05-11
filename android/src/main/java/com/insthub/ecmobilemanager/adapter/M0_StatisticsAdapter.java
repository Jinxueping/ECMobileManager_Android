package com.insthub.ecmobilemanager.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.insthub.ecmobilemanager.R;
import com.insthub.ecmobilemanager.protocol.MANAGER_DATA;

import java.util.List;


public class M0_StatisticsAdapter extends ArrayAdapter<String> {
    private MANAGER_DATA dataModel;
    private final Activity context;
   public M0_StatisticsAdapter(Activity context,MANAGER_DATA dataModel)
   {
       super(context,R.layout.m0_list_view, (List<String>)dataModel);
       this.dataModel = dataModel;
       this.context = context;
   }


    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View cellView = inflater.inflate(R.layout.m0_list_view, null, true);
       /* TextView m_title = (TextView) cellView.findViewById(R.id.s_title_text);
        m_title.setText("Products");
        ListView m_SList = (ListView) cellView.findViewById(R.id.s0_listView);
        m_SList.setAdapter((ListAdapter) dataModel.productstatisticsList);
        TextView m_ordertitle = (TextView) cellView.findViewById(R.id.s_title_text);
        m_title.setText("Orders");
        ListView m_OList = (ListView) cellView.findViewById(R.id.order_listView);
        m_OList.setAdapter((ListAdapter) dataModel.orderList);
        TextView visittitle = (TextView) cellView.findViewById(R.id.visit_text);
        visittitle.setText("Visit");
        ListView m_VList = (ListView) cellView.findViewById(R.id.visit_listView);
        m_VList.setAdapter((ListAdapter) dataModel.visitList);*/
        return cellView;
    }
}
