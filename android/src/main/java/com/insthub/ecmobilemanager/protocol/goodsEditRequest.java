
package com.insthub.ecmobilemanager.protocol;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "goodsEditRequest")
public class goodsEditRequest extends Model
{
     @Column(name = "goods")
     public GOODS goods;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
         if(null == jsonObject){
            return ;
         }
         JSONArray subItemArray;

         GOODS tmpGoods = new GOODS();
         tmpGoods.fromJson(jsonObject.optJSONObject("goods"));
         this.goods = tmpGoods;
         return;
     }

     public JSONObject toJson() throws JSONException
     {
          JSONObject localItemObject = new JSONObject();
          if (this.goods != null)
              localItemObject.put("goods", goods.toJson());
          return localItemObject;
     }
}
