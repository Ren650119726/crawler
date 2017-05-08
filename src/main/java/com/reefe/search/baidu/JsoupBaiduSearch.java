package com.reefe.search.baidu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.reefe.search.SearchResult;
import com.reefe.search.Webpage;
import com.reefe.search.util.TextExtract;
import com.reefe.search.util.Tools;

public class JsoupBaiduSearch extends AbstractBaiduSearcher{
	
    public SearchResult search(String keyword) {
        return search(keyword, 1);
    }
    
    @Override
	public SearchResult search(String keyword, int page) {
    	int pageSize = 10;
		String urlPath = "http://www.baidu.com/s?pn="+(page-1)*pageSize+"&wd="+keyword;
		SearchResult searchResult = new SearchResult();
		searchResult.setPage(page);
	    List<Webpage> webpages = new ArrayList<>();
		try {
			Document document = Jsoup.connect(urlPath).get();
			 //获取搜索结果数目
            int total = getBaiduSearchResultCount(document);
            searchResult.setTotal(total);
            int len = 10;
            if (total < 1) {
                return null;
            }
            //如果搜索到的结果不足一页
            if (total < 10) {
                len = total;
            }
            for (int i = 0; i < len; i++) {
				String titleCssQuery = "html body div div div div#content_left div#"
						+ (i + 1 + (page - 1) * pageSize)
						+ ".result.c-container h3.t a";
				String summaryCssQuery = "html body div div div div#content_left div#"
						+ (i + 1 + (page - 1) * pageSize)
						+ ".result.c-container div.c-abstract";
				Element titleElement = document.select(titleCssQuery).first();
				String href = "";
				String titleText = "";
				if (titleElement != null) {
					titleText = titleElement.text();
					href = titleElement.attr("href");
				} else {
					// 处理百度百科
					titleCssQuery = "html body div#out div#in div#wrapper div#container div#content_left div#1.result-op h3.t a";
					summaryCssQuery = "html body div#out div#in div#wrapper div#container div#content_left div#1.result-op div p";

					titleElement = document.select(titleCssQuery).first();
					if (titleElement != null) {
						titleText = titleElement.text();
						href = titleElement.attr("href");
					}
				}
				Element summaryElement = document.select(summaryCssQuery)
						.first();
				// 处理百度知道
				if (summaryElement == null) {
					summaryCssQuery = summaryCssQuery.replace("div.c-abstract",
							"font");
					summaryElement = document.select(summaryCssQuery).first();
				}
				String summaryText = "";
				if (summaryElement != null) {
					summaryText = summaryElement.text();
				}
                
                if (titleText != null && !"".equals(titleText.trim()) && summaryText != null && !"".equals(summaryText.trim())) {
                    Webpage webpage = new Webpage();
                    webpage.setTitle(titleText);
                    webpage.setUrl(href);
                    webpage.setSummary(summaryText);
	            	if (!StringUtil.isBlank(href.trim())) {
	            		System.out.println(href);
	            		URL url = new URL(href);
	            		if(url != null){
	            			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//	            			connection.setReadTimeout(5000);
//	            			connection.setRequestMethod("GET");
//	            			connection.connect();
	            			if(connection.getResponseCode() == 206 || connection.getResponseCode() == 200){
	            				InputStream is = connection.getInputStream();
	                    		BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
	                    		StringBuilder sb = new StringBuilder();
	                    		String temp = "";
	                    		while((temp = br.readLine())!=null){
	                    			sb.append(temp).append("\n");
	                    		}
	                    		String content = TextExtract.parse(sb.toString());
	                    		webpage.setContent(content);
	            			}
	            		}
	                }
                	webpages.add(webpage);
                }
			}
            searchResult.setWebpages(webpages);
            return searchResult;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private int getBaiduSearchResultCount(Document document) {
		String cssQuery = "html body div div div div .nums";
		Element totalElement = document.select(cssQuery).first();
		String totalText = totalElement.text();
		String regEx = "[^0-9]";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(totalText);
		totalText = matcher.replaceAll("");
		int total = Integer.parseInt(totalText); 
		return total;
	}
	
	public static void main(String[] args) {
		JsoupBaiduSearch baiduSearch = new JsoupBaiduSearch();
		baiduSearch.search("java");
	}
	
}
