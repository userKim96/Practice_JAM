package com.koreaIT.example.JAM.service;

import java.sql.Connection;

import com.koreaIT.example.JAM.dao.ArticleDao;

public class ArticleService {
	
	private ArticleDao articleDao;

	public ArticleService(Connection conn) {
		this.articleDao = new ArticleDao(conn);
	}
	
	public int doWrite(String title, String body) {
		
		return articleDao.doWrite(title, body);
	
	}

}
