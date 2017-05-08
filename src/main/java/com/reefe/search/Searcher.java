package com.reefe.search;

public interface Searcher {
    public SearchResult search(String keyword);
    public SearchResult search(String keyword, int page);
}