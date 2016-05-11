package com.insthub.ecmobilemanager.model;

import com.external.activeandroid.Model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/5/7 0007.
 */
public class Orders_Statistics_Model extends Model {

    public String notshipping;
    public String unconfirmed;
    public String unpaid;
    public String finished;
    public String booking;
    public String refund;
    public String partsdelivered;
    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if (null == jsonObject) {
            return;
        }
        this.notshipping = jsonObject.optString("notshipping");
        this.unconfirmed= jsonObject.optString("unconfirmed");
        this.unpaid=  jsonObject.optString("unpaid");
        this.finished  = jsonObject.optString("finished");
        this.partsdelivered = jsonObject.optString("partsdelivered");
        return ;
    }
    public JSONObject toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        localItemObject.put("notshipping", notshipping);
        localItemObject.put("unconfirmed", unconfirmed);
        localItemObject.put("unpaid",unpaid);
        localItemObject.put("finished", finished);
        localItemObject.put("partsdelivered",partsdelivered);
        return localItemObject;
    }
}
