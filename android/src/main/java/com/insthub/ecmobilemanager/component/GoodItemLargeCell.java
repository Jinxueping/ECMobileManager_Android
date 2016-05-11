package com.insthub.ecmobilemanager.component;

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
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.insthub.ecmobilemanager.EcmobileApp;
import com.insthub.ecmobilemanager.R;
import com.insthub.ecmobilemanager.activity.D2_GoodsDetailActivity;
import com.insthub.ecmobilemanager.protocol.GOODS;
import com.nostra13.universalimageloader.core.ImageLoader;

public class GoodItemLargeCell extends LinearLayout
{
    private ImageView item_photo;
    private TextView briefTextView;
    private TextView priceContent;
    private TextView marketContent;
    Context mContext;
    
    private SharedPreferences shared;
	private SharedPreferences.Editor editor;
    protected ImageLoader imageLoader = ImageLoader.getInstance();

    public GoodItemLargeCell(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    void init()
    {
         if (null == item_photo)
         {
             item_photo = (ImageView)findViewById(R.id.gooditem_photo);
         }

        if (null == briefTextView)
        {
            briefTextView = (TextView)findViewById(R.id.brief);
        }

        if (null == priceContent)
        {
            priceContent = (TextView)findViewById(R.id.price_content);
        }
        
        if(null == marketContent) {
        	marketContent = (TextView) findViewById(R.id.market_content);
        	marketContent.getPaint().setAntiAlias(true);
        	marketContent.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    public void bindData(final GOODS goods)
    {
        init();
        
        shared = mContext.getSharedPreferences("userInfo", 0); 
		editor = shared.edit();

        imageLoader.displayImage(goods.goods_img, item_photo, EcmobileApp.options);
        briefTextView.setText(goods.name);
        priceContent.setText("商店价格："+ goods.shop_price);
        marketContent.setText("市场价格："+ goods.market_price);
        item_photo.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View v) {
				 
	        	Intent it = new Intent(mContext, D2_GoodsDetailActivity.class);
	        	it.putExtra("good_id", goods.id);
	            mContext.startActivity(it);
			}
		});
    }
}
