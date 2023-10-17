package com.koreaIT.example.JAM.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.koreaIT.example.JAM.Article;
import com.koreaIT.example.JAM.service.ArticleService;
import com.koreaIT.example.JAM.util.DBUtil;
import com.koreaIT.example.JAM.util.SecSql;

public class ArticleController {
	
	private Scanner sc;
	private ArticleService articleService;

	public ArticleController(Scanner sc, Connection conn) {
		this.sc = sc;
		this.articleService = new ArticleService(conn);
	}

	public void doWrite() {
		
		System.out.println("==게시물 작성==");
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();

		
		int id = articleService.doWrite(title, body);

		System.out.printf("%d번 게시글이 생성되었습니다\n", id);
		
	}

	public void showList() {
		
		List<Article> articles = articleService.showList();
		
		if (articles.size() == 0) {
			System.out.println("게시글이 없습니다");
			return;
		}
		
		System.out.println("==게시물 목록==");
		
		System.out.println("번호   /   제목");
		for (Article article : articles) {
			System.out.printf("%4d   /   %s\n", article.id, article.title);
		}
		
	}

}
