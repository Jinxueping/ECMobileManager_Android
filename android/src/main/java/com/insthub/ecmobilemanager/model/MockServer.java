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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.external.androidquery.callback.AjaxCallback;

/*
 *	 ______    ______    ______
 *	/\  __ \  /\  ___\  /\  ___\
 *	\ \  __<  \ \  __\_ \ \  __\_
 *	 \ \_____\ \ \_____\ \ \_____\
 *	  \/_____/  \/_____/  \/_____/
 *
 *
 *	Copyright (c) 2013-2014, {Bee} open source community
 *	http://www.bee-framework.com
 *
 *
 *	Permission is hereby granted, free of charge, to any person obtaining a
 *	copy of this software and associated documentation files (the "Software"),
 *	to deal in the Software without restriction, including without limitation
 *	the rights to use, copy, modify, merge, publish, distribute, sublicense,
 *	and/or sell copies of the Software, and to permit persons to whom the
 *	Software is furnished to do so, subject to the following conditions:
 *
 *	The above copyright notice and this permission notice shall be included in
 *	all copies or substantial portions of the Software.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *	FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 *	IN THE SOFTWARE.
 */

import com.insthub.BeeFramework.model.BeeCallback;
import com.insthub.ecmobilemanager.protocol.*;

public class MockServer
{
    private static MockServer instance;
    public static MockServer getInstance()
    {
        if (instance == null) {
            instance = new MockServer();
        }
        return instance;
    }
    
    public static <K> void ajax(AjaxCallback<K> callback)
    {
	try
	{
    	JSONObject responseJsonObject = new JSONObject();
		if (callback.getUrl() == ProtocolConst.CATEGORYGOODS)
    	{
    		STATUS status = new STATUS();
    		status.succeed = 1;
    		status.error_code= 0;
    		status.error_desc="";
    		responseJsonObject.put("status", status.toJson());
    		
    		JSONArray dataJsonObject = new JSONArray();    		
    		for (int i = 0; i < 3; i++) 
    		{    			    		
    			CATEGORYGOODS good = new CATEGORYGOODS();
    			        		
        		for (int j = 0; j < 3; j++) 
        		{    			    		
        			GOODS simplegood = new GOODS();
        			simplegood.goods_img = "http://cache.miyoo.cn/attach/13/42157/1/6054/193729.jpg";
        			good.goods.add(simplegood);       		
    			}
        		good.name = "家居";        		
        		dataJsonObject.put(good.toJson());        		        		
			}
    		    		
    		responseJsonObject.put("data", dataJsonObject);
    		
		}
    	else if (callback.getUrl() == ProtocolConst.SEARCH) 
    	{
    		STATUS status = new STATUS();
    		status.succeed = 1;
    		status.error_code= 0;
    		status.error_desc="";
    		responseJsonObject.put("status", status.toJson());
    		
    		JSONArray dataJsonObject = new JSONArray();    		
    		    			        		
    		for (int j = 0; j < 3; j++) 
    		{    			    		
    			GOODS simplegood = new GOODS();
    			simplegood.goods_img = "http://cache.miyoo.cn/attach/13/42157/1/6054/193729.jpg";
    			dataJsonObject.put(simplegood.toJson());        		
			}
        	        		        		       		        	    		    		
    		responseJsonObject.put("data", dataJsonObject);
		}
    	
        ((BeeCallback)callback).callback(callback.getUrl(), responseJsonObject, callback.getStatus());
    
    } catch (JSONException e) {
		// TODO: handle exception
	}    	
   }
}
