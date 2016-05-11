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

import android.os.Environment;

public class ProtocolConst {
	
	public static String FILEPATH = Environment.getExternalStorageDirectory()+ "/ECMobile/cache";

	public static String CATEGORYGOODS ="home/category";

	public static String SEARCH = "search";

    public static String GOODSDETAIL = "goods";
    
    public static String GOODSDESC = "goods/desc";					// 商品详情，商品描述
    
    public static String SIGNIN = "user/signin";					// 登录
    
    public static String SIGNUPFIELDS  = "user/signupFields";		// 注册字段
    
    public static String SIGNUP = "user/signup";					// 注册
    
    public static String SEARCHKEYWORDS = "searchKeywords";			// 获取搜索推荐关键字
    
    public static String USERINFO = "user/info";					// 获取用户中心相关信息

    public static String CONFIG = "config";						// 获取配置

    public static String CATEGORY = "category";                  //获取所有分类

    public static String BRAND = "brand";                       //获取所有品牌

    public static String PRICE_RANGE = "price_range";           //根据分类获取价格区间

}
