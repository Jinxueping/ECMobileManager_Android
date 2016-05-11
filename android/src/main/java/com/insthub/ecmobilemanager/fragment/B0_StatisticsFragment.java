package com.insthub.ecmobilemanager.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.insthub.BeeFramework.fragment.BaseFragment;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.MyListView;
import com.insthub.ecmobilemanager.EcmobileManager.RegisterApp;
import com.insthub.ecmobilemanager.R;
import com.insthub.ecmobilemanager.adapter.M0_StatisticsAdapter;
import com.insthub.ecmobilemanager.model.StatisticlistModel;
import com.insthub.ecmobilemanager.protocol.ApiInterface;
import com.insthub.ecmobilemanager.protocol.MANAGER_DATA;
import com.insthub.ecmobilemanager.protocol.managerdataRespons;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/5/7 0007.
 */
public class B0_StatisticsFragment extends BaseFragment implements BusinessResponse,XListView.IXListViewListener,RegisterApp
{
    //topview
    private ImageView back;
    private TextView title;
    private LinearLayout title_right_button;
    private TextView headUnreadTextView;
    private MyListView mListView;
    MANAGER_DATA manager_data;
    private StatisticlistModel manager_model;


    private M0_StatisticsAdapter listAdapter;
    //
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public ListView m_SList;
    public ListView m_OList;
    public ListView m_VList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        View mainView = inflater.inflate(R.layout.m0_index,null);
        back = (ImageView) mainView.findViewById(R.id.top_view_back);
        back.setVisibility(View.GONE);
        title = (TextView) mainView.findViewById(R.id.top_view_text);
        Resources resource = this.getResources();
        String ecmobileStr="ECShop_Statistics";
        title.setText(ecmobileStr);
        manager_model = new StatisticlistModel(getActivity());
        manager_model.getManagerData();
        manager_model.addResponseListener(this);

        m_SList = (ListView) mainView.findViewById(R.id.s0_listView);
        m_OList = (ListView) mainView.findViewById(R.id.order_listView);
        m_VList = (ListView) mainView.findViewById(R.id.visit_listView);

        //manager_data=manager_model.data;

       /* mListView = (MyListView)mainView.findViewById(R.id.manager_listview);
        mListView.setPullLoadEnable(false);
        mListView.setPullRefreshEnable(true);
        mListView.setXListViewListener(this, 0);
        mListView.setRefreshTime();*/
        //MangerSetAdapter();
        return mainView;
    }
    /*public void MangerSetAdapter()
    {
         //M0_StatisticsAdapter listAdapter = new M0_StatisticsAdapter(getActivity(), manager_data);
         mListView.setAdapter((ListAdapter)manager_data);
    }*/
    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {

            if (url.endsWith(ApiInterface.MANAGER_HOME))
            {

                managerdataRespons response = new managerdataRespons();
                response.fromJson(jo);
                if (response.status.succeed == 1) {
                    manager_data = response.data;
                    //manager_data.toJson();
                    if (manager_data !=null)
                    {
                        //m_SList.setAdapter((ListAdapter)() manager_data.productstatistics);
                        //m_VList.setAdapter((ListAdapter) manager_data.visit);
                        //m_OList.setAdapter((ListAdapter) manager_data.order);
                    }

                }
                /*if (null == listAdapter)
                {
                    M0_StatisticsAdapter listAdapter = new M0_StatisticsAdapter(getActivity(), manager_data);
                }
                mListView.setAdapter(listAdapter);
                */

            }


    }


        @Override
    public void onRefresh(int id) {

    }

    @Override
    public void onLoadMore(int id) {

    }

    @Override
    public void onRegisterResponse(boolean success) {

    }
}