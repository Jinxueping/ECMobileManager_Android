package com.insthub.ecmobilemanager.fragment;

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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.res.Resources;
import com.insthub.ecmobilemanager.activity.*;
import com.insthub.ecmobilemanager.protocol.ApiInterface;
import com.umeng.analytics.MobclickAgent;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.external.activeandroid.query.Select;
import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.external.maxwin.view.XListView.IXListViewListener;
import com.insthub.BeeFramework.fragment.BaseFragment;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.WebImageView;
import com.insthub.ecmobilemanager.R;
import com.insthub.ecmobilemanager.model.ProtocolConst;
import com.insthub.ecmobilemanager.model.UserInfoModel;
import com.insthub.ecmobilemanager.protocol.USER;

/**
 * 身份主页
 */
public class E0_ProfileFragment extends BaseFragment implements IXListViewListener, OnClickListener, BusinessResponse {

	private File file;
	
	private View view;
	private View headView;
	private XListView xlistView;
	
	private ImageView setting;
	private WebImageView photo;
	private ImageView camera;
	
	private TextView name;
	private FrameLayout payment;
	private TextView payment_num;
	private FrameLayout ship;
	private TextView ship_num;
	private FrameLayout receipt;
	private TextView receipt_num;
	private FrameLayout history;
	private TextView history_num;
	private TextView collect_num;
	
	private LinearLayout collect;
	private LinearLayout notify;
	private LinearLayout address_manage;

    private LinearLayout memberLevelLayout;
    private TextView     memberLevelName;
    private ImageView    memberLevelIcon;

    private LinearLayout help;
	
	private USER user;
	private UserInfoModel userInfoModel;
	
	private SharedPreferences shared;
	private SharedPreferences.Editor editor;
	
	private ImageView image;
	private String uid;
	public static boolean isRefresh = false;

    protected Context mContext;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 
		
		view = inflater.inflate(R.layout.e0_profile, null);
		
		mContext = getActivity();
		
		shared = getActivity().getSharedPreferences("userInfo", 0); 
		editor = shared.edit();

        headView = LayoutInflater.from(getActivity()).inflate(R.layout.e0_profile_head, null);

        image = (ImageView) view.findViewById(R.id.profile_setting);


		xlistView = (XListView) view.findViewById(R.id.profile_list);
        xlistView.addHeaderView(headView);

		xlistView.setPullLoadEnable(false);
		xlistView.setRefreshTime();
		xlistView.setXListViewListener(this,1);
		xlistView.setAdapter(null);

        memberLevelLayout = (LinearLayout)headView.findViewById(R.id.member_level_layout);
        memberLevelName   = (TextView)headView.findViewById(R.id.member_level);
        memberLevelIcon   = (ImageView)headView.findViewById(R.id.member_level_icon);

		setting  = (ImageView) headView.findViewById(R.id.profile_head_setting);
		photo = (WebImageView) headView.findViewById(R.id.profile_head_photo);
		camera = (ImageView) headView.findViewById(R.id.profile_head_camera);
		name = (TextView) headView.findViewById(R.id.profile_head_name);
		
		payment = (FrameLayout) headView.findViewById(R.id.profile_head_payment);
		payment_num = (TextView) headView.findViewById(R.id.profile_head_payment_num);
		
		ship = (FrameLayout) headView.findViewById(R.id.profile_head_ship);
		ship_num = (TextView) headView.findViewById(R.id.profile_head_ship_num);
		
		receipt = (FrameLayout) headView.findViewById(R.id.profile_head_receipt);
		receipt_num = (TextView) headView.findViewById(R.id.profile_head_receipt_num);
		
		history = (FrameLayout) headView.findViewById(R.id.profile_head_history);
		history_num = (TextView) headView.findViewById(R.id.profile_head_history_num);
		
		collect = (LinearLayout) headView.findViewById(R.id.profile_head_collect);
		notify = (LinearLayout) headView.findViewById(R.id.profile_head_notify);
		address_manage = (LinearLayout) headView.findViewById(R.id.profile_head_address_manage);
		collect_num = (TextView) headView.findViewById(R.id.profile_head_collect_num);
        help = (LinearLayout)headView.findViewById(R.id.profile_help);
		
		setting.setOnClickListener(this);
		camera.setOnClickListener(this);
		payment.setOnClickListener(this);
		ship.setOnClickListener(this);
		receipt.setOnClickListener(this);
		history.setOnClickListener(this);
		collect.setOnClickListener(this);
		notify.setOnClickListener(this);
		address_manage.setOnClickListener(this);
		name.setOnClickListener(this);
        help.setOnClickListener(this);

        uid = shared.getString("uid", "");
		File files = new File(getActivity().getCacheDir()+"/ECMobile/cache"+"/"+uid+"-temp.jpg");
		if(files.exists()&&!uid.equals("")) {
			photo.setImageBitmap(BitmapFactory.decodeFile(getActivity().getCacheDir()+"/ECMobile/cache"+"/"+uid+"-temp.jpg"));
		} else {
			photo.setImageResource(R.drawable.profile_no_avarta_icon);
		}
		
		photo.setOnClickListener(this);

        if (null == userInfoModel)
        {
            userInfoModel = new UserInfoModel(getActivity());
        }
        userInfoModel.addResponseListener(this);
		if (uid.equals("")) {

			Resources resource = mContext.getResources();
           
            String click=resource.getString(R.string.click_to_login);
			name.setText(click);
			camera.setVisibility(View.GONE);
            memberLevelLayout.setVisibility(View.GONE);

		} else {
			userInfoModel.getUserInfo();
			camera.setVisibility(View.VISIBLE);
            memberLevelLayout.setVisibility(View.VISIBLE);
		}
		return view;
	}
	
	@Override
	public void onResume() {
		 
		super.onResume();
		uid = shared.getString("uid", "");
		if (!uid.equals("")) {
			if(isRefresh == true) {
				userInfoModel.getUserInfo();
			}
			camera.setVisibility(View.VISIBLE);
		} else {
			camera.setVisibility(View.GONE);
		}
		isRefresh = false;
        MobclickAgent.onPageStart("Profile");
	}
	
	// set User 信息
	public void setUserInfo() {
		name.setText(user.name);
        File files = new File(getActivity().getCacheDir()+"/ECMobile/cache"+"/"+uid+"-temp.jpg");
        if(files.exists()&&!uid.equals("")) {
            photo.setImageBitmap(BitmapFactory.decodeFile(getActivity().getCacheDir()+"/ECMobile/cache"+"/"+uid+"-temp.jpg"));
        } else {
            photo.setImageResource(R.drawable.profile_no_avarta_icon);
        }
        memberLevelName.setText(user.rank_name);
        memberLevelLayout.setVisibility(View.VISIBLE);
        Resources resource = mContext.getResources();       
        if (user.rank_level != UserInfoModel.RANK_LEVEL_NORMAL)
        {
            memberLevelIcon.setVisibility(View.VISIBLE);
        }
        else
        {
            memberLevelIcon.setVisibility(View.GONE);
        }


         if(user.collection_num.equals("0")){
             collect_num.setText(resource.getString(R.string.no_product));
         }else{
             collect_num.setText(user.collection_num+resource.getString(R.string.no_of_items));
         }
	}
	

	@Override
	public void onClick(View v) {
		 
		Intent intent;
		switch(v.getId()) {

           
		}
	}
	

	@Override
	public void onRefresh(int id) {
		 

		if (!uid.equals("")) {
			userInfoModel.getUserInfo();
		}
		
	}

	@Override
	public void onLoadMore(int id) {
		 
		
	}
	

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {  
           
        super.onActivityResult(requestCode, resultCode, data);  
        
        if (resultCode == Activity.RESULT_OK) {

        	if(requestCode == 1) {
        		String sdStatus = Environment.getExternalStorageState();
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式

                if (file == null) {
					file = new File(ProtocolConst.FILEPATH);
					if (!file.exists()) {
						file.mkdirs();
					}
				}
                FileOutputStream b = null;
                String fileName = getActivity().getCacheDir()+"/ECMobile/cache"+"/"+uid+"-temp.jpg";
                try {
                    b = new FileOutputStream(fileName);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        b.flush();
                        b.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                ((ImageView) view.findViewById(R.id.profile_head_photo)).setImageBitmap(bitmap);// 将图片显示在ImageView里
        	}

        }
        
        if(requestCode == 2) {
        	userInfoModel.getUserInfo();
    	}
    	
    	
    }  

	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) {
		if (url.endsWith(ApiInterface.USER_INFO)) {
			xlistView.stopRefresh();
			xlistView.setRefreshTime();
			user = userInfoModel.user; // 从网络获取
			setUserInfo();
		} 
	}


	public static USER userInfo(String uid) {
		return new Select().from(USER.class).where("USER_id = ?", uid).executeSingle();
	}

    @Override
    public void onDestroy() {
        userInfoModel.removeResponseListener(this);
        super.onDestroy();
    }
    @Override
    public void onPause() {
         
        super.onPause();
        MobclickAgent.onPageEnd("Profile");
    }
}
