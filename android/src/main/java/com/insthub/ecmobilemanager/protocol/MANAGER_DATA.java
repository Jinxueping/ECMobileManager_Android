package com.insthub.ecmobilemanager.protocol;

import com.external.activeandroid.Model;
import com.insthub.ecmobilemanager.model.Orders_Statistics_Model;
import com.insthub.ecmobilemanager.model.Products_Statistics_Model;
import com.insthub.ecmobilemanager.model.Visits_Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/5/7 0007.
 */

public class MANAGER_DATA extends Model {
    public Products_Statistics_Model productstatistics= new Products_Statistics_Model();
    public Orders_Statistics_Model  order = new Orders_Statistics_Model();
    public Visits_Model visit = new Visits_Model();
    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }
        Products_Statistics_Model SItem = new Products_Statistics_Model();
        JSONObject ItemObject = jsonObject.optJSONObject("products");
        SItem.fromJson(ItemObject);
        this.productstatistics =SItem;
        Orders_Statistics_Model OItem = new Orders_Statistics_Model();
        ItemObject = jsonObject.optJSONObject("orders");
        OItem.fromJson(ItemObject);
        this.order =OItem ;
        Visits_Model VItem = new Visits_Model();
        ItemObject = jsonObject.optJSONObject("visits");
        VItem.fromJson(jsonObject);
        this.visit =VItem;
        return ;
    }

    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();

        localItemObject.put("products", productstatistics.toJson());
        localItemObject.put("order", order.toJson());
        localItemObject.put("visits", visit.toJson());
        return localItemObject;
    }
}
