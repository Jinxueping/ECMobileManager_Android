package com.insthub.ecmobilemanager;

public class ECMobileAppConst
{
//    public static final String AppId = "1136530ac5aacf74";
   public static final String AppId = "52653a425feb4754";
//    public static final String AppKey = "7859dc9d12c213b3b223da8a524344a3";
    public static final String AppKey = "00d4f7310e493c3e92026f71a825c253";

    public static final String IP_ADDR = "192.168.1.66";
//    public static final String SERVER_PRODUCTION = "http://shop.ecmobile.me/ecmobile/?url=";
//    public static final String SERVER_DEVELOPMENT = "http://dev.ecmobile.me/ecmobile/?url=";
//    public static final String WAP_PAY_CALLBCK_DEVELOPMENT="http://dev.ecmobile.me/ecmobile/payment/app_callback.php?";
//    public static final String WAP_PAY_CALLBCK_PRODUCTION="http://shop.ecmobile.me/ecmobile/payment/app_callback.php?";

    public static final String SERVER_PRODUCTION = "http://" + IP_ADDR + "/ecshop/admin/mobile/?act=";
    //public static final String SERVER_PRODUCTION = "http://" + IP_ADDR + "/ecshop/ecmobile/?url=";
    public static final String SERVER_MANAGER_PRODUCTION = "http://" + IP_ADDR + "/manager/ecshop/ecmobile/?url=";
    public static final String SERVER_DEVELOPMENT = "http://" + IP_ADDR + "/ecshop/ecmobile/?url=";
    public static final String WAP_PAY_CALLBCK_DEVELOPMENT="http://" + IP_ADDR + "/ecshop/ecmobile/payment/app_callback.php?";
    public static final String WAP_PAY_CALLBCK_PRODUCTION="http://" + IP_ADDR + "/ecshop/ecmobile/payment/app_callback.php?";
}
