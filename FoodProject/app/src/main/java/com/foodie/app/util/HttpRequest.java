package com.foodie.app.util;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.foodie.app.code.Code;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.List;


public  final  class HttpRequest {

    public static Handler handler=new Handler();
    public static void goPost(final List<NameValuePair> ListData, final String path, boolean checknet)
    {
        final List<NameValuePair> listdata = ListData;
        // TODO Auto-generated method stub
        final Message message =new Message();
        final Message message2 =new Message();
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(Constant.BASEURL+path);
                    if (ListData != null) {
                        HttpEntity entity = new UrlEncodedFormEntity(ListData, HTTP.UTF_8);
                        httpPost.setEntity(entity);
                    }

                    httpClient.getParams().setParameter(
                            CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
                    httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
                            3000);
                    HttpResponse httpResp = httpClient.execute(httpPost);
                    if (httpResp.getStatusLine().getStatusCode() == 200) {
                        String result = EntityUtils.toString(httpResp.getEntity(),HTTP.UTF_8);
                        message.obj=result;
                        handler.sendMessage(message);
                        Log.e("resultresult","result："+result);
                    } else {
                        message.obj= Code.fail;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("resultresult","Exception"+e.toString());
                    message.obj=Code.noRespond;
                    handler.sendMessage(message);
                }
            }
        });
        //check network
            if (checknet==true)
            {
                thread.start();
            }else if(checknet==false)
            {
                message.obj=Code.fail;
                handler.sendMessage(message);
            }
        }

}
