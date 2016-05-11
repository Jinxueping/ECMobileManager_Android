package com.insthub.ecmobilemanager.model;
//
//                       __
//                      /\ \   _
//    ____    ____   ___\ \ \_/ \           _____    ___     ___
//   / _  \  / __ \ / __ \ \    <     __   /\__  \  / __ \  / __ \
//  /\ \_\ \/\  __//\  __/\ \ \\ \   /\_\  \/_/  / /\ \_\ \/\ \_\ \
//  \ \____ \ \____\ \____\\ \_\\_\  \/_/   /\____\\ \____/\ \____/
//   \/____\ \/____/\/____/ \/_//_/         \/____/ \/___/  \/___/
//     /\____/
//     \/___/
//
//  Powered by BeeFramework
//

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.insthub.BeeFramework.view.MyProgressDialog;
import com.insthub.ecmobilemanager.R;
import com.insthub.ecmobilemanager.protocol.*;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BaseModel;
import com.insthub.BeeFramework.model.BeeCallback;

public class GoodsListModel extends BaseModel {

    public ArrayList<GOODS> goodsList = new ArrayList<GOODS>();

    public static String PRICE_DESC = "price_desc";
    public static String PRICE_ASC = "price_asc";
    public static String IS_HOT = "is_hot";

    public static final int PAGE_COUNT = 6;

    public GoodsListModel(Context context)
    {
        super(context);
    }

    public void fetchPreSearch(FILTER filter) {
        searchRequest request = new searchRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>()
        {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status)
            {
                GoodsListModel.this.callback(url, jo, status);
                try {
                    searchResponse response = new searchResponse();
                    response.fromJson(jo);
                    if (jo != null)
                    {
                        PAGINATED paginated = response.paginated;
                        if (response.status.succeed == 1)
                        {
                            ArrayList<GOODS> data = response.data;

                            goodsList.clear();
                            if (null != data && data.size() > 0)
                            {
                                goodsList.clear();
                                goodsList.addAll(data);
                            }
                            GoodsListModel.this.OnMessageResponse(url, jo, status);
                        }
                    }

                }
                catch (JSONException e)
                {
                    // TODO: handle exception
                }
            }
        };

        PAGINATION pagination = new PAGINATION();
        pagination.page = 1;
        pagination.count = PAGE_COUNT;

        request.filter = filter;
        request.pagination = pagination;
        Map<String, String> params = new HashMap<String, String>();
        try
        {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cb.url(ApiInterface.GOODS_LIST).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);
    }

    public void fetchPreSearchMore(FILTER filter)
    {
        searchRequest request = new searchRequest();

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>()
        {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status)
            {
                GoodsListModel.this.callback(url, jo, status);
                try {
                    searchResponse response = new searchResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        PAGINATED paginated = response.paginated;
                        if (response.status.succeed == 1)
                        {
                            ArrayList<GOODS> data = response.data;

                            if (null != data && data.size() > 0) {
                                goodsList.addAll(data);
                            }

                            GoodsListModel.this.OnMessageResponse(url, jo, status);
                        }
                    }
                }
                catch (JSONException e)
                {
                    // TODO: handle exception
                }
            }
        };

        PAGINATION pagination = new PAGINATION();
        pagination.page = (int) Math.ceil((double) goodsList.size() * 1.0 / PAGE_COUNT) + 1;
        pagination.count = PAGE_COUNT;

        request.filter = filter;
        request.pagination = pagination;
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cb.url(ApiInterface.GOODS_LIST).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);
    }
}
