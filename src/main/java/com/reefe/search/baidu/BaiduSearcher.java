package com.reefe.search.baidu;

import com.reefe.search.SearchResult;
import com.reefe.search.Searcher;

/**
 *
 */
public interface BaiduSearcher extends Searcher{
    /**
     * 新闻搜索
     * @param keyword
     * @return 
     */
    public SearchResult searchNews(String keyword);
    /**
     * 新闻搜索（分页）
     * @param keyword
     * @param page
     * @return 
     */
    public SearchResult searchNews(String keyword, int page);
    /**
     * 贴吧搜索
     * @param keyword
     * @return 
     */
    public SearchResult searchTieba(String keyword);
    /**
     * 贴吧搜索（分页）
     * @param keyword
     * @param page
     * @return 
     */
    public SearchResult searchTieba(String keyword, int page);
    /**
     * 知道搜索
     * @param keyword
     * @return 
     */
    public SearchResult searchZhidao(String keyword);
    /**
     * 知道搜索（分页）
     * @param keyword
     * @param page
     * @return 
     */
    public SearchResult searchZhidao(String keyword, int page);
    /**
     * 文库搜索
     * @param keyword
     * @return 
     */
    public SearchResult searchWenku(String keyword);
    /**
     * 文库搜索（分页）
     * @param keyword
     * @param page
     * @return 
     */
    public SearchResult searchWenku(String keyword, int page);
}