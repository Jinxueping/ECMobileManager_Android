package com.insthub.ecmobilemanager.model;

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

import java.util.HashMap;
import java.util.Map;

import com.insthub.BeeFramework.view.MyProgressDialog;
import com.insthub.ecmobilemanager.R;
import com.insthub.ecmobilemanager.protocol.*;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BaseModel;
import com.insthub.BeeFramework.model.BeeCallback;

public class LoginModel extends BaseModel {

    private SharedPreferences shared;
    private SharedPreferences.Editor editor;
    public STATUS responseStatus;

    public LoginModel(Context context) {
        super(context);
        shared = context.getSharedPreferences("userInfo", 0);
        editor = shared.edit();
    }

    public void login(String name, String password) {
        usersigninRequest request = new usersigninRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                LoginModel.this.callback(url, jo, status);
                try {
                    usersigninResponse response = new usersigninResponse();
                    response.fromJson(jo);
                    responseStatus=response.status;
                    if (jo != null) {
                        if (response.status.succeed == 1) {
                            SIGNIN_DATA data = response.data;
                            SESSION session = data.session;
                            SESSION.getInstance().uid=session.uid;
                            SESSION.getInstance().sid = session.sid;
                            USER user = data.user;
                            //user.save();
                            editor.putString("uid", session.uid);
                            editor.putString("sid", session.sid);
                            editor.putString("email",user.email);
                            editor.commit();
                        }
                        LoginModel.this.OnMessageResponse(url, jo, status);


                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

        };
        //test
        request.username = "admin";
        request.password = "admin123";
        /*
        request.username = name;
        request.password = password;
        */
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cb.url(ApiInterface.MANAGER_SIGNIN).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext,mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);
    }
}
