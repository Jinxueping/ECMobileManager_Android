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

import android.content.Context;
import android.content.SharedPreferences;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BaseModel;
import com.insthub.BeeFramework.model.BeeCallback;
import com.insthub.BeeFramework.view.MyProgressDialog;
import com.insthub.ecmobilemanager.R;
import com.insthub.ecmobilemanager.protocol.ApiInterface;
import com.insthub.ecmobilemanager.protocol.GOODS;
import com.insthub.ecmobilemanager.protocol.STATUS;
import com.insthub.ecmobilemanager.protocol.goodsEditRequest;
import com.insthub.ecmobilemanager.protocol.goodsEditResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class GoodsModel extends BaseModel
{
    private SharedPreferences shared;
    private SharedPreferences.Editor editor;

    public STATUS responseStatus;

    public GoodsModel(Context context)
    {
        super(context);
        shared = context.getSharedPreferences("userInfo", 0);
        editor = shared.edit();
    }

    public void addnew(GOODS goods, File goods_img)
    {
        goodsEditRequest request = new goodsEditRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>()
        {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status)
            {
                GoodsModel.this.callback(url, jo, status);
                try
                {
                    goodsEditResponse response = new goodsEditResponse();
                    response.fromJson(jo);
                    responseStatus = response.status;
                    if (jo != null)
                    {
                        GoodsModel.this.OnMessageResponse(url, jo, status);
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        };

        request.goods = goods;

        Map<String, Object> params = new HashMap<String, Object>();
        try
        {
            params.put("json", request.toJson().toString());
            if (goods_img != null)
                params.put("goods_img", goods_img);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        cb.url(ApiInterface.GOODS_EDIT).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext,mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);
    }
}
