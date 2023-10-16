package com.koreaIT.example.JAM;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.koreaIT.example.JAM.util.DBUtil;
import com.koreaIT.example.JAM.util.SecSql;

public class App {

	public void start() {
		System.out.println("=프로그램 시작==");
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.print("명령어 ) ");
			String cmd = sc.nextLine().trim();
			Connection conn = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("드라이버 로딩 실패");
				e.printStackTrace();
			}
 
			String url = "jdbc:mysql://127.0.0.1:3306/JAM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
			try {
				conn = DriverManager.getConnection(url, "root", "");
				int actionRs = doAction(conn, cmd, sc);
				if (actionRs == -1) {
					System.out.println("==프로그램 종료==");
					break;
				}
			} catch (SQLException e) {
				System.out.println("에러 : " + e);
			} finally {
				try {
					if (conn != null && !conn.isClosed()) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		sc.close();
	}
	private int doAction(Connection conn, String cmd, Scanner sc) {
		if (cmd.equals("exit")) {
			return -1;
		}
		if (cmd.equals("article write")) {
			System.out.println("==게시물 작성==");
			System.out.printf("제목 : ");
			String title = sc.nextLine();
			System.out.printf("내용 : ");
			String body = sc.nextLine();

			SecSql sql = new SecSql();
			sql.append("INSERT INTO article");
			sql.append("SET regDate = NOW(),");
			sql.append("updateDate = NOW(),");
			sql.append("title = ?,", title);
			sql.append("`body` = ?", body);

			int id = DBUtil.insert(conn, sql);

			System.out.printf("%d번 게시글이 생성되었습니다\n", id);

		} else if (cmd.equals("article list")) {
			System.out.println("==게시물 목록==");
			
			ArrayList<Article> articles = new ArrayList<Article>();
			
			SecSql sql = new SecSql();
			sql.append("SELECT *");
			sql.append("FROM article");
			sql.append("ORDER BY id DESC;");
			
			List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);
			
			for(Map<String, Object> articleMap : articleListMap) {
				articles.add(new Article(articleMap));
			}

			if (articles.size() == 0) {
				System.out.println("게시글이 없습니다");
				return 0;
			}
			System.out.println("번호   /   제목");
			for (Article article : articles) {
				System.out.printf("%4d   /   %s\n", article.id, article.title);
			}
		} else if (cmd.startsWith("article modify")) {
			System.out.println("==게시물 수정==");
			int id = Integer.parseInt(cmd.split(" ")[2]);
			System.out.printf("새 제목 : ");
			String newTitle = sc.nextLine();
			System.out.printf("새 내용 : ");
			String newBody = sc.nextLine();
			PreparedStatement pstmt = null;
			try {
				String sql = "UPDATE article";
				sql += " SET updateDate = NOW(),";
				sql += " title = '" + newTitle + "',";
				sql += " `body` = '" + newBody + "'";
				sql += " WHERE id = " + id + ";";
				System.out.println(sql);
				pstmt = conn.prepareStatement(sql);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println("에러 : " + e);
			} finally {
				try {
					if (pstmt != null && !pstmt.isClosed()) {
						pstmt.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			System.out.println(id + "번 글이 수정되었습니다");
		}
		return 0;
	}
}