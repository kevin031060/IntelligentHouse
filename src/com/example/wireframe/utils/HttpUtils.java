package com.example.wireframe.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

/**
 * Created by likaiwen on 15/6/6.
 */
public class HttpUtils {

    public HttpUtils(){

    }


    public static String doGet(String url, String key){

        String result="";
        try {
            InputStream in = new URL(url).openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line =null;
            StringBuffer content=new StringBuffer();
            while((line=reader.readLine())!=null){
                content.append(line);
            }
            reader.close();

            System.out.println(content.toString());
            result = JsonP(content.toString(),key);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void doPostJson2(String url, String tem) {

//Send json indicating entity body
        String encoderJson =  "{\"value\":"+tem+"}";

// Use httpClient to post json
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);


        try {
            StringEntity se = new StringEntity(encoderJson);

            httpPost.setEntity(se);
// API key needed
            httpPost.addHeader("U-ApiKey", "f4beef91284d8f240276802d883d89ff");
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();
// Get response code
            InputStreamReader reader = new InputStreamReader(resEntity.getContent(), "ISO-8859-1");
            char[] buff = new char[1024];
            int length = 0;
            while ((length = reader.read(buff)) != -1) {
                System.out.println(new String(buff, 0, length));
                httpclient.getConnectionManager().shutdown();
            }
            System.out.println(response.getStatusLine().getStatusCode());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    public static String JsonP(String s, String index){

        String jp;
        try{
            if (index.equals("value")) {
                JSONObject jsonObject = new JSONObject(s);
                jp = jsonObject.getString("value");


            }else if (index.equals("timestamp")){
                JSONObject jsonObject = new JSONObject(s);
                jp = jsonObject.getString("timestamp");
            }else {
                jp=s;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            jp=null;
        }

        return jp;
    }
}
