package com.insthub.ecmobilemanager.model;

import com.external.activeandroid.Model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/5/7 0007.
 */
public class Products_Statistics_Model extends Model {

    public String total;
    public String stockwarning;
    public String new_goods;
    public String hot;
    public String best;
    public String promotion;

    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if (null == jsonObject) {
            return;
            }
        this.total = jsonObject.optString("total");
        this.stockwarning  = jsonObject.optString("stockwarning");
        this.new_goods      =  jsonObject.optString("new_goods");
        this.hot      =  jsonObject.optString("hot");
        this.best     = jsonObject.optString("best");
        this.promotion = jsonObject.optString("promotion");
        return ;
    }
    public JSONObject toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        localItemObject.put("total", total);
        localItemObject.put("stockwarning", stockwarning);
        localItemObject.put("new_goods",new_goods);
        localItemObject.put("hot", hot);
        localItemObject.put("best",best);
        localItemObject.put("promotion",promotion);
        return localItemObject;
    }
}
