package com.reefe.search.google;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.jsoup.helper.StringUtil;

import com.google.gson.Gson;
import com.reefe.search.SearchResult;
import com.reefe.search.Webpage;
import com.reefe.search.google.entities.GoogleResults;
import com.reefe.search.google.entities.Items;
import com.reefe.search.util.TextExtract;
import com.reefe.search.util.Tools;

public class GoogleAjaxSearcher implements GoogleSearcher{

	@Override
	public SearchResult search(String keyword) {
		return search(keyword,2);
	}

	@Override
	public SearchResult search(String keyword, int page) {
		int pageSize = 10;
		String url = "https://www.googleapis.com/customsearch/v1?key=AIzaSyBY6IZ24ZwpwmfQpQZAP05pi05zt_Hz3_8&cx=013036536707430787589:_pqjad5hr1a&alt=json&start="+(1+(page-1)*pageSize)+"&num="+pageSize+"&q=" + keyword;
		
		SearchResult searchResult = new SearchResult();
        searchResult.setPage(page);
        List<Webpage> webpages = new ArrayList<>();
        try {
			HttpClient client = new HttpClient();
			GetMethod method = new GetMethod(url);
			
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, 
					new DefaultHttpMethodRetryHandler());
			
			int statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
			    return null;
			}
    		InputStream in = method.getResponseBodyAsStream();
    		ByteArrayOutputStream out = new ByteArrayOutputStream();
    		try {
				byte[] buff = new byte[1024];
				int len = 0;
				while((len = in.read(buff))!= -1){
					out.write(buff, 0, len);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(out!=null){
					out.close();
				}
				if(in != null){
					in.close();
				}
			}
		   byte[] responseBody = out.toByteArray();
		   String response = new String(responseBody, "UTF-8");
		   Gson gson = new Gson();
		   GoogleResults results = gson.fromJson(response, GoogleResults.class);
		   List<Items> items = results.getItems();
		   for(Items item:items){
			   String link = item.getLink();
			   String snippet = item.getSnippet();
			   String title = item.getTitle();
			   Webpage webpage = new Webpage();
			   webpage.setUrl(link);
			   webpage.setTitle(title);
			   webpage.setSummary(snippet);
			   if (!StringUtil.isBlank(link.trim())) {
	        		String html = Tools.getHTML(link);
	        		String content = TextExtract.parse(html);
	        		System.out.println(content);
	    		    webpage.setContent(content);
	           }
			   webpages.add(webpage);
		   } 
		   searchResult.setWebpages(webpages);
		   return searchResult;
		} catch (Exception e) {
			e.printStackTrace();
		}
        
		return null;
	}
	public static void main(String[] args) {
		String keyword = "Gson";
		try {
			keyword = URLEncoder.encode(keyword, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return;
		}
		GoogleAjaxSearcher searcher = new GoogleAjaxSearcher();
		searcher.search(keyword);
	}

}
