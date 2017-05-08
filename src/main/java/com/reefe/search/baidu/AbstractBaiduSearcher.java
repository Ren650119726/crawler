package com.reefe.search.baidu;

import com.reefe.search.SearchResult;

/**
 *
 */
public abstract class AbstractBaiduSearcher implements BaiduSearcher {
    /**
     * 新闻搜索
     * @param keyword
     * @return 
     */
    @Override
    public SearchResult searchNews(String keyword){
        return searchNews(keyword, 1);
    }
    /**
     * 新闻搜索（分页）
     * @param keyword
     * @param page
     * @return 
     */
    @Override
    public SearchResult searchNews(String keyword, int page){
        throw new RuntimeException("未实现");
    }
    /**
     * 贴吧搜索
     * @param keyword
     * @return 
     */
    @Override
    public SearchResult searchTieba(String keyword){
        return searchTieba(keyword, 1);
    }
    /**
     * 贴吧搜索（分页）
     * @param keyword
     * @param page
     * @return 
     */
    @Override
    public SearchResult searchTieba(String keyword, int page){
        throw new RuntimeException("未实现");
    }
    /**
     * 知道搜素
     * @param keyword
     * @return 
     */
    @Override
    public SearchResult searchZhidao(String keyword){
        return searchZhidao(keyword, 1);
    }
    /**
     * 知道搜索（分页）
     * @param keyword
     * @param page
     * @return 
     */
    @Override
    public SearchResult searchZhidao(String keyword, int page){
        throw new RuntimeException("未实现");
    }
    /**
     * 文库搜索
     * @param keyword
     * @return 
     */
    @Override
    public SearchResult searchWenku(String keyword){
        return searchWenku(keyword, 1);
    }
    /**
     * 文库搜索（分页）
     * @param keyword
     * @param page
     * @return 
     */
    @Override
    public SearchResult searchWenku(String keyword, int page){
        throw new RuntimeException("未实现");
    }
}