package com.insthub.ecmobilemanager.model;

import com.external.activeandroid.Model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/5/7 0007.
 */
public class Visits_Model extends Model {
    public String today;
    public String current;
    public String messages;
    public String comments;

    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if (null == jsonObject) {
            return;
        }
        this.today = jsonObject.optString("today");
        this.current= jsonObject.optString("current");

        return ;
    }
    public JSONObject toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        localItemObject.put("today", today);
        localItemObject.put("current", current);
        return localItemObject;
    }
}
