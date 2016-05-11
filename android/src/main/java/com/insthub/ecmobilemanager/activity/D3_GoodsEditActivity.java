package com.insthub.ecmobilemanager.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobilemanager.R;
import com.insthub.ecmobilemanager.model.GoodsModel;
import com.insthub.ecmobilemanager.model.ProtocolConst;
import com.insthub.ecmobilemanager.protocol.GOODS;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/5/10 0010.
 */
public class D3_GoodsEditActivity extends BaseActivity implements View.OnClickListener, BusinessResponse
{
    private File file;

    private SharedPreferences shared;
    private SharedPreferences.Editor editor;

    private View headView;
    private XListView xlistView;

    private ImageView btnBack;

    private EditText txtGoodsName;

    private ImageView imgGoods;
    private ImageView imgCameraBtn;

    private boolean isImageSet;
    private EditText txtGoodsNumber;
    private EditText txtShopPrice;
    private EditText txtMarketPrice;
    private EditText txtWeight;
    private EditText txtStock;
    private CheckBox chkBest;
    private CheckBox chkHot;
    private CheckBox chkNew;

    private Button btnEditOK;

    private GoodsModel dataModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d3_goods_edit);

        shared = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        editor = shared.edit();

        btnBack = (ImageView) findViewById(R.id.top_view_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        xlistView = (XListView) findViewById(R.id.goods_edit_detail_list);
        headView = LayoutInflater.from(this).inflate(R.layout.d3_goods_edit_detail, null);

        xlistView.addHeaderView(headView);
        xlistView.setPullLoadEnable(false);
        xlistView.setAdapter(null);

        txtGoodsName = (EditText) headView.findViewById(R.id.txt_goods_name);
        imgGoods = (ImageView) headView.findViewById(R.id.goods_photo);
        imgCameraBtn = (ImageView) headView.findViewById(R.id.goods_camera);
        txtGoodsNumber = (EditText) headView.findViewById(R.id.txt_goods_number);
        txtShopPrice = (EditText) headView.findViewById(R.id.txt_shop_price);
        txtMarketPrice = (EditText) headView.findViewById(R.id.txt_market_price);
        txtWeight = (EditText) headView.findViewById(R.id.txt_weight);
        txtStock = (EditText) headView.findViewById(R.id.txt_stock);
        chkBest = (CheckBox) headView.findViewById(R.id.chk_is_best);
        chkHot = (CheckBox) headView.findViewById(R.id.chk_is_hot);
        chkNew = (CheckBox) headView.findViewById(R.id.chk_is_new);
        btnEditOK = (Button) headView.findViewById(R.id.btn_edit_ok);

        isImageSet = false;
        imgCameraBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

            }
        });

        btnEditOK.setOnClickListener(this);

        file = new File(ProtocolConst.FILEPATH + "/temp.jpg");
        if (!file.exists())
            file.delete();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {
            if(requestCode == 1)
            {
                String sdStatus = Environment.getExternalStorageState();
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");

                if (file == null)
                {
                    file = new File(ProtocolConst.FILEPATH);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                }
                FileOutputStream b = null;
                //String fileName = getCacheDir()+"/ECMobile/cache" + "/temp.jpg";
                String fileName = ProtocolConst.FILEPATH + "/temp.jpg";
                try
                {
                    b = new FileOutputStream(fileName);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    try {
                        b.flush();
                        b.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                imgGoods.setImageBitmap(bitmap);
                isImageSet = true;
            }
        }
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.btn_edit_ok)
        {
            dataModel = new GoodsModel(D3_GoodsEditActivity.this);
            dataModel.addResponseListener(this);
            GOODS goods = new GOODS();
            goods.name = txtGoodsName.getText().toString();
            if ("".equals(goods.name))
            {
                new ToastView(this, "Must input the name of goods").show();
                return;
            }

            goods.number = txtGoodsNumber.getText().toString();
            goods.is_on_sale = true;
            goods.is_best = chkBest.isChecked();
            goods.is_hot = chkHot.isChecked();
            goods.is_new = chkNew.isChecked();
            goods.shop_price = txtShopPrice.getText().toString();
            goods.market_price = txtMarketPrice.getText().toString();
            goods.goods_weight = txtWeight.getText().toString();
            goods.stock = Integer.getInteger(txtStock.getText().toString(), 1);
            //goods.brand =
            //goods.category =

            if (!isImageSet)
            {
                new ToastView(this, "Must pick a image of goods.").show();
                return;
            }

            String fileName = ProtocolConst.FILEPATH + "/temp.jpg";
            file = new File(fileName);
            if (!file.exists())
                file = null;

            dataModel.addnew(goods, file);
            dataModel.addResponseListener(this);
        }
    }


    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException
    {
        if(dataModel.responseStatus.succeed == 1)
        {
            // if(url.endsWith(ApiInterface.USER_SIGNIN)) {
            Intent intent = new Intent();
            intent.putExtra("goods_edit", true);
            setResult(Activity.RESULT_OK, intent);
            finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }
    }
}
