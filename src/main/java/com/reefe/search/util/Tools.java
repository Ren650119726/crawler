package com.reefe.search.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Tools {
	
	public static String getHTML(String href) throws UnsupportedEncodingException, IOException{
		URL url = new URL(href);
		if(url != null){
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setReadTimeout(5000);
			connection.setRequestMethod("GET");
			connection.connect();
			if(connection.getResponseCode() == 206 || connection.getResponseCode() == 200){
				InputStream is = connection.getInputStream();
        		BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
        		StringBuilder sb = new StringBuilder();
        		String temp = "";
        		while((temp = br.readLine())!=null){
        			sb.append(temp).append("\n");
        		}
        		return sb.toString();
			}
		}
		return null;
	}
}
