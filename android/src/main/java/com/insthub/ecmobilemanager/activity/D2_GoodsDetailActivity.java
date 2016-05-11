package com.insthub.ecmobilemanager.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.external.maxwin.view.XListView;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.ecmobilemanager.EcmobileApp;
import com.insthub.ecmobilemanager.EcmobileManager;
import com.insthub.ecmobilemanager.R;
import com.insthub.ecmobilemanager.protocol.GOODS;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

import java.util.Timer;

public class D2_GoodsDetailActivity extends BaseActivity
{
    private TextView goodBriefTextView;
    private TextView goodPromotePriceTextView;
    private TextView goodMarketPriceTextView;
    private TextView goodPropertyTextView;
    private ImageView goodsImageView;

    protected ImageLoader imageLoader = ImageLoader.getInstance();

    private TextView title;
    private ImageView back;

    private View headView;
    private XListView xlistView;

    private SharedPreferences shared;
	private SharedPreferences.Editor editor;
    private Timer timer;
    private  Boolean isFresh=true;//是否选择的属性

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.d2_goods_detail);

        shared = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		editor = shared.edit();

        xlistView = (XListView) findViewById(R.id.good_detail_list);
        headView = LayoutInflater.from(this).inflate(R.layout.d2_goods_detail_head, null);

        xlistView.addHeaderView(headView);
        xlistView.setPullLoadEnable(false);
        xlistView.setAdapter(null);

        final Resources resource = (Resources) getBaseContext().getResources();

        title = (TextView) findViewById(R.id.top_view_text);
        String det=resource.getString(R.string.gooddetail_product);
        title.setText(det);
        back = (ImageView) findViewById(R.id.top_view_back);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        goodBriefTextView = (TextView)headView.findViewById(R.id.good_brief);
        goodPromotePriceTextView = (TextView)headView.findViewById(R.id.promote_price);
        goodMarketPriceTextView = (TextView)headView.findViewById(R.id.market_price);
        goodMarketPriceTextView.getPaint().setAntiAlias(true);
        goodMarketPriceTextView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        goodPropertyTextView = (TextView)headView.findViewById(R.id.good_property);
        goodsImageView = (ImageView)headView.findViewById(R.id.goods_image);


        GOODS goods = (GOODS)getIntent().getSerializableExtra("goods");

        goodBriefTextView.setText(goods.name );
        String brp = resource.getString(R.string.formerprice);
        String marketStr = resource.getString(R.string.market_price);
        goodPromotePriceTextView.setText("￥" + goods.shop_price);
        goodMarketPriceTextView.setText(marketStr + "￥" + goods.market_price);

        imageLoader.displayImage(goods.goods_img, goodsImageView, EcmobileApp.options);

        String contentString = "";
        String brs = resource.getString(R.string.brstock);
        String stor = resource.getString(R.string.store_price);
        contentString += "<br>" + brs + goods.goods_sn+"</br>";
        contentString += "<br>" + stor + goods.shop_price+"</br>";
        contentString += "<br>" + marketStr + goods.market_price+"</br>";
        contentString += "<br>" + "Weight : " + goods.goods_weight+" kg</br>";
        contentString += "<br>" + "Stock : " + goods.stock+"</br>";
        contentString += "<br>" + "Purchases : " + goods.purchase_amount+"</br>";
        contentString += "<br>" + "Category : " + goods.category+"</br>";
        contentString += "<br>" + "Brand : " + goods.brand+"</br>";
        contentString += "<br>" + "Is Hot : " + (goods.is_hot ? "YES" : "NO") +"</br>";
        contentString += "<br>" + "Is Best : " + (goods.is_best ? "YES" : "NO") +"</br>";
        contentString += "<br>" + "Is New : " + (goods.is_new ? "YES" : "NO") +"</br>";

        Spanned htmlString = Html.fromHtml(contentString);

        goodPropertyTextView.setText(htmlString);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
                finish();
                overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
                return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(EcmobileManager.getUmengKey(this)!=null){
            MobclickAgent.onPageStart("GoodDetail");
            MobclickAgent.onResume(this, EcmobileManager.getUmengKey(this),"");
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {		
		super.onActivityResult(requestCode, resultCode, data);
	}

    @Override
    protected void onPause() {
        super.onPause();
        if(EcmobileManager.getUmengKey(this)!=null){
            MobclickAgent.onPageEnd("GoodDetail");
            MobclickAgent.onPause(this);
        }
    }
}
