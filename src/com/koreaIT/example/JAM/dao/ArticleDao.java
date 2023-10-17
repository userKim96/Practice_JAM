package com.koreaIT.example.JAM.dao;

import java.sql.Connection;

import com.koreaIT.example.JAM.util.DBUtil;
import com.koreaIT.example.JAM.util.SecSql;

public class ArticleDao {
	
	private Connection conn;

	public ArticleDao(Connection conn) {
		this.conn = conn;
	}
	
	public int doWrite(String title, String body) {
		
		SecSql sql = new SecSql();
		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW(),");
		sql.append("updateDate = NOW(),");
		sql.append("title = ?,", title);
		sql.append("`body` = ?", body);
		
		return DBUtil.insert(conn, sql);
		
	}
	
	

}
