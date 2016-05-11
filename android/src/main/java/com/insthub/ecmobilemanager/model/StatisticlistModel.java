package com.insthub.ecmobilemanager.model;


import android.content.Context;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BaseModel;
import com.insthub.BeeFramework.model.BeeCallback;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.ecmobilemanager.protocol.ApiInterface;
import com.insthub.ecmobilemanager.protocol.MANAGER_DATA;
import com.insthub.ecmobilemanager.protocol.STATUS;
import com.insthub.ecmobilemanager.protocol.managerdataRequest;
import com.insthub.ecmobilemanager.protocol.managerdataRespons;
import android.content.SharedPreferences;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Administrator on 2016/5/7 0007.
 */
public class StatisticlistModel extends BaseModel {

    public MANAGER_DATA data =new MANAGER_DATA();
    private SharedPreferences shared;
    private SharedPreferences.Editor editor;
    public STATUS responseStatus;

    public StatisticlistModel(Context context) {
        super(context);
        shared = context.getSharedPreferences("userInfo", 0);
        editor = shared.edit();
    }
    public void getManagerData()
    {
        managerdataRequest request  =new managerdataRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
                    @Override
                    public void callback(String url, JSONObject jo, AjaxStatus status) {

                        StatisticlistModel.this.callback(url, jo, status);
                        try {
                            managerdataRespons response = new managerdataRespons();
                            response.fromJson(jo);
                            if (response.status.succeed == 1) {
                                MANAGER_DATA responsedata = response.data;
                                if (responsedata!=null)
                                {
                                    StatisticlistModel.this.data =responsedata;
                                    StatisticlistModel.this.OnMessageResponse(url, jo, status);
                                }

                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    }

        };
        cb.url(ApiInterface.MANAGER_HOME).type(JSONObject.class);
        aq.ajax(cb);
    }
    public  void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException
    {
        for (BusinessResponse iterable_element : businessResponseArrayList)
        {
            iterable_element.OnMessageResponse(url,jo,status);
        }
    }




}
