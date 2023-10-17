package com.koreaIT.example.JAM.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.koreaIT.example.JAM.Article;
import com.koreaIT.example.JAM.dao.ArticleDao;

public class ArticleService {
	
	private ArticleDao articleDao;

	public ArticleService(Connection conn) {
		this.articleDao = new ArticleDao(conn);
	}
	
	public int doWrite(String title, String body) {
		
		return articleDao.doWrite(title, body);
	
	}

	public List<Article> showList() {
		
		List<Article> articles = new ArrayList<>();
		
		
		List<Map<String, Object>> articlesListMap = articleDao.showList();
		
		for(Map<String, Object> articleMap : articlesListMap) {
			articles.add(new Article(articleMap));
		}
		
		
		
		
		return articles;
	}

}
