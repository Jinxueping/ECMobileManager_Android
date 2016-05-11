
package com.insthub.ecmobilemanager.protocol;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import java.io.Serializable;

@Table(name = "GOODS")
public class GOODS extends Model implements Serializable
{
    @Column(name = "SIMPLEGOODS_id",unique = true)
    public String id;

    @Column(name = "goods_id")
    public String goods_id;

    @Column(name = "goods_name")
    public String name;

    @Column(name = "number")
    public String number;

    @Column(name = "goods_img")
    public String goods_img;

    @Column(name = "goods_sn")
    public String goods_sn;

    @Column(name = "shop_price")
    public String shop_price;

    @Column(name = "market_price")
    public String market_price;

    @Column(name = "is_on_sale")
    public boolean is_on_sale;

    @Column(name = "is_best")
    public boolean is_best;

    @Column(name = "is_new")
    public boolean is_new;

    @Column(name = "is_hot")
    public boolean is_hot;

    @Column(name = "category")
    public int category;

    @Column(name = "brand")
    public int brand;

    @Column(name = "purchase_amount")
    public long purchase_amount;

    @Column(name = "stock")
    public long stock;

    @Column(name = "goods_weight")
    public String goods_weight;


     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
         if(null == jsonObject)
         {
            return ;
         }

         JSONArray subItemArray;

         this.id = jsonObject.optString("id");
         this.goods_id = jsonObject.optString("goods_id");
         this.name = jsonObject.optString("goods_name");
         this.number = jsonObject.optString("number");
         this.goods_img = jsonObject.optString("goods_img");
         this.goods_sn = jsonObject.optString("goods_sn");
         this.shop_price = jsonObject.optString("shop_price");
         this.market_price = jsonObject.optString("market_price");
         this.is_on_sale = (jsonObject.optInt("is_on_sale") == 1);
         this.is_best = (jsonObject.optInt("is_best") == 1);
         this.is_new = (jsonObject.optInt("is_new") == 1);
         this.is_hot = (jsonObject.optInt("is_hot") == 1);
         this.category = jsonObject.optInt("category");
         this.brand = jsonObject.optInt("brand");
         this.purchase_amount = jsonObject.optLong("purchase_amount");
         this.stock = jsonObject.optLong("stock");
         this.goods_weight = jsonObject.optString("goods_weight");

         return ;
     }

     public JSONObject  toJson() throws JSONException
     {
         JSONObject localItemObject = new JSONObject();
         JSONArray itemJSONArray = new JSONArray();
         localItemObject.put("id", id);
         localItemObject.put("goods_name", name);
         localItemObject.put("number", number);
         localItemObject.put("goods_id", goods_id);
         localItemObject.put("goods_img", goods_img);
         localItemObject.put("goods_sn", goods_sn);
         localItemObject.put("shop_price", shop_price);
         localItemObject.put("market_price", market_price);
         localItemObject.put("is_on_sale", is_on_sale ? 1 : 0);
         localItemObject.put("is_best", is_best ? 1 : 0);
         localItemObject.put("is_new", is_new ? 1 : 0);
         localItemObject.put("is_hot", is_hot ? 1 : 0);
         localItemObject.put("category", category);
         localItemObject.put("brand", brand);
         localItemObject.put("purchase_amount", purchase_amount);
         localItemObject.put("stock", stock);
         localItemObject.put("goods_weight", goods_weight);

         return localItemObject;
     }
}
