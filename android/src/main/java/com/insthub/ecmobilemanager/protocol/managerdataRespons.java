package com.insthub.ecmobilemanager.protocol;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/5/7 0007.
 */
public class managerdataRespons extends Model
{
    @Column(name = "status")
    public STATUS   status;

    @Column(name = "data")
    public MANAGER_DATA   data;
    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        JSONArray subItemArray;
        STATUS  status = new STATUS();
        status.fromJson(jsonObject.optJSONObject("status"));
        this.status = status;
        MANAGER_DATA manager_data = new MANAGER_DATA();
        manager_data.fromJson(jsonObject.optJSONObject("data"));
        this.data = manager_data;
        return ;
    }

    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        if(null != status)
        {
            localItemObject.put("status", status.toJson());
        }
        if(null != data)
        {
            localItemObject.put("data", data.toJson());
        }
        return localItemObject;
    }
}
