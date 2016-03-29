/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nuance.expertassistant;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.*;


public class HTTPConnection {

    public static final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception {

        String url = "http://ibm-dqa8.grid.nuance.com:8080/question?";

        HTTPConnection http = new HTTPConnection();

        System.out.println("\nTesting 2 - Send Http POST request");

        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("contextId", "451425");
        paramMap.put("question", "touch screen with beats audio");

        http.sendPost(url, paramMap);
    }

    // HTTP GET request
    public static JSONObject sendGet(String getURL) throws Exception {

        String url = getURL;

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);

        // add request header
        request.addHeader("User-Agent", USER_AGENT);

        System.out.println(" The URL is :" + url);

        HttpResponse response = client.execute(request);

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuilder builder = new StringBuilder();
        for (String line = null; (line = rd.readLine()) != null;) {
            builder.append(line).append("\n");
        }
        
         System.out.print("JSON TEXT : " + builder.toString());
         
         String jsonText = builder.toString();
         
         if(jsonText.startsWith("["))
                 {
                     jsonText = jsonText.substring(1,jsonText.length());
                 }
         
         if(jsonText.endsWith("]"))
                 {
                     jsonText = jsonText.substring(0,jsonText.length()-1);
                 }
       
        JSONObject object = new JSONObject(jsonText);
        
        return object;

    }

    // HTTP POST request
    public static String sendPost( String postURL, HashMap<String, String> paramMap) throws Exception {

        String url = postURL;

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        post.setHeader("User-Agent", USER_AGENT);

        if(paramMap!=null)
        {
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        System.out.println(" Printing Parameters ");
        for (String key : paramMap.keySet()) {
            System.out.println(key + " :: " + paramMap.get(key));

            urlParameters.add(new BasicNameValuePair(key, paramMap.get(key)));

        }

        post.setEntity(new UrlEncodedFormEntity(urlParameters));
        }
        HttpResponse response = client.execute(post);
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + post.getEntity());
        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuilder builder = new StringBuilder();
        for (String line = null; (line = rd.readLine()) != null;) {
            builder.append(line).append("\n");
        }
        /*
         System.out.print("JSON TEXT : " + builder.toString());
         
         String jsonText = builder.toString();
         
                 jsonText = "{\"response\":" + jsonText + "}";

                     
                     JSONObject object = new JSONObject(jsonText); */
        
        System.out.println(" The response code is :" + response.getStatusLine().getStatusCode());
        
         System.out.println(" The string returned is :" + builder.toString());

        return builder.toString();
        
  
    }

}
