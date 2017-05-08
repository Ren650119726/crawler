package com.reefe.search;

import java.util.List;

public class SearchResult {
	private Integer total;
	private Integer page;
	private List<Webpage> webpages;
	
	
	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public List<Webpage> getWebpages() {
		return webpages;
	}

	public void setWebpages(List<Webpage> webpages) {
		this.webpages = webpages;
	}
	
}
