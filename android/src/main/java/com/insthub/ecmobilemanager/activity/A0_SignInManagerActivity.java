package com.insthub.ecmobilemanager.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobilemanager.R;
import com.insthub.ecmobilemanager.fragment.E0_ProfileFragment;
import com.insthub.ecmobilemanager.model.LoginModel;
import com.insthub.ecmobilemanager.protocol.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/5/6 0006.
 */
public class A0_SignInManagerActivity extends BaseActivity implements View.OnClickListener, BusinessResponse {

    private Button login;

    private EditText manager_name;
    private EditText password;

    private String name;
    private String psd;

    private LoginModel loginModel;
    private final static int REQUEST_SIGN_UP = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a0_sigin_manager);
        Intent intent = new Intent();
        intent.setAction("com.BeeFramework.NetworkStateService");
        startService(intent);

        login = (Button) findViewById(R.id.login_manager_login);
        manager_name = (EditText) findViewById(R.id.manager_name);
        password = (EditText) findViewById(R.id.login_manager_password);

        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Resources resource = (Resources) getBaseContext().getResources();
        String usern=resource.getString(R.string.user_name_cannot_be_empty);
        String pass=resource.getString(R.string.password_cannot_be_empty);
        Intent intent;
        switch(v.getId())
        {
            case R.id.login_manager_login:
                name = manager_name.getText().toString();
                psd = password.getText().toString();
                /*name="admin";
                psd ="admin123";*/
                if(name.length()<2){
                    ToastView toast = new ToastView(this, resource.getString(R.string.username_too_short));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                if(name.length()>20){
                    ToastView toast = new ToastView(this, resource.getString(R.string.username_too_long));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                if(psd.length()<6){
                    ToastView toast = new ToastView(this, resource.getString(R.string.password_too_short));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                if(psd.length()>20){
                    ToastView toast = new ToastView(this, resource.getString(R.string.password_too_long));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                if("".equals(name))
                {
                    ToastView toast = new ToastView(this, usern);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else if("".equals(psd))
                {
                    ToastView toast = new ToastView(this, pass);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else
                {
                    loginModel = new LoginModel(A0_SignInManagerActivity.this);
                    loginModel.addResponseListener(this);
                    loginModel.login(name, psd);
                    CloseKeyBoard();
                }
                break;
        }
        /*Intent manger_activity = new Intent(this,ECMobileManagerMainActivity.class);
        startActivity(manger_activity);
        finish()*/;
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        if(loginModel.responseStatus.succeed == 1) {
            if(url.endsWith(ApiInterface.MANAGER_SIGNIN)) {
               // if(url.endsWith(ApiInterface.USER_SIGNIN)) {
                Intent intent = new Intent();
                intent.putExtra("login", true);
                setResult(Activity.RESULT_OK, intent);
                finish();
                Intent manger_activity = new Intent(this,ECMobileManagerMainActivity.class);
                startActivity(manger_activity);
                finish();
                //overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SIGN_UP) {
            if(data!=null) {
                Intent intent = new Intent();
                intent.putExtra("login", true);
                setResult(Activity.RESULT_OK, intent);
                finish();
                E0_ProfileFragment.isRefresh=true;
                overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
           this.finish();

            //finish();
            //overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
        }
        return true;
    }

    // 关闭键盘
    public void CloseKeyBoard() {
        manager_name.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(manager_name.getWindowToken(), 0);
    }
}
