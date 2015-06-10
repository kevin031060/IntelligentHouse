package com.example.wireframe.utils;

import android.os.AsyncTask;

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

    private static String resultGet="ll";
    private static final String APPLICATION_JSON = "application/json";

    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";

    public HttpUtils(){

    }

    public static String doGet(String url, String key){
        new doAsyncGet().execute(url,key);
        return resultGet;
    }



    private static class doAsyncGet extends AsyncTask<String,Void,String>{

//
//        private String result;

            protected String doInBackground(String... params) {

                try {
                    InputStream in = new URL(params[0]).openStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String line =null;
                    StringBuffer content=new StringBuffer();
                    while((line=reader.readLine())!=null){
                        content.append(line);
                    }
                    reader.close();

                    System.out.println(content.toString());
                    resultGet = JsonP(content.toString(),params[1]);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

    }






    public static void doPostJson2(String url) {

//
//        String encoderJson = "{" +
//                "  \"timestamp\":\"2015-06-10T16:13:14\"," +
//                "  \"value\":\"20\"" +
//                "}";


        String encoderJson =  "{\"value\":0}";


        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);


        try {
            StringEntity se = new StringEntity(encoderJson);

            httpPost.setEntity(se);
            httpPost.addHeader("U-ApiKey", "f4beef91284d8f240276802d883d89ff");
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();

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
