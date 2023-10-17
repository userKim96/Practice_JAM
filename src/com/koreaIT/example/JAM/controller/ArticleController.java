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

	public void doModify(String cmd) {
		
		if (cmd.equals("article modify")) {
			System.out.println("게시물 번호를 입력해 주세요.");
			return;
		}
		
		int id = Integer.parseInt(cmd.split(" ")[2]);
		

		int articleCount = articleService.articleCount(id);

		if (articleCount == 0) {
			System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
			return;
		}
			
		System.out.printf("== %d번 게시물 수정 ==\n", id);
		System.out.printf("새 제목 : ");
		String newTitle = sc.nextLine();
		System.out.printf("새 내용 : ");
		String newBody = sc.nextLine();
		
		
		articleService.doModify(newTitle, newBody, id);
		
		System.out.println(id + "번 글이 수정되었습니다");
		
	}

}
