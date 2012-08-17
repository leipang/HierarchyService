package com.qunb.geonames;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RestHandler {
	private RestHandler(){}

	private final static String charset = "UTF-8";
	
	public static String callSvc(String strUrl)  {

		try {
			URL url = new URL(strUrl);
			String result = callSvc(url);
			return result;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}


	public static String callSvc(URL url)  {
		try{
			System.out.println(url.toString() + "\t[RestHandler.callSvc()]");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setReadTimeout(200000);
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept","application/json");
			connection.setRequestProperty("Accept-Charset", charset);
			connection.setRequestProperty("Content-encoding", charset);
			connection.connect();
			if(connection.getResponseCode()!=200){
				System.out.println("Failed : HTTP error code : "+ connection.getResponseCode());
				return connection.getResponseMessage();
			}
			InputStream in = connection.getInputStream();

			BufferedReader data = new BufferedReader(new InputStreamReader(in, "UTF-8"));

			StringBuffer buf = new StringBuffer();
			String line = null;
			while((line = data.readLine()) != null)
				buf.append(line);
			connection.disconnect();
			return buf.toString();
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public static JSONObject callSvcAsJson(String url) throws JSONException, IOException {
		String bufString = callSvc(url);
		if(bufString==null)
			return null;
		return new JSONObject(bufString);
	}
	public static JSONArray callSvcAsJsonArray(String url) throws JSONException, IOException {
		String bufString = callSvc(url);
		if(bufString==null)
			return null;
		return new JSONArray(bufString);
	}
}
