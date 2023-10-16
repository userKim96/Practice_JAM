package com.koreaIT.example.JAM;
import java.sql.Connection;
import java.sql.DriverManager;
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
			
			System.out.println("==게시물 목록==");
			
			System.out.println("번호   /   제목");
			for (Article article : articles) {
				System.out.printf("%4d   /   %s\n", article.id, article.title);
			}
		} 
		else if(cmd.startsWith("article modify")) {
			
			if (cmd.equals("article modify")) {
				System.out.println("게시물 번호를 입력해 주세요.");
				return 0;
			}
			
			
			int id = Integer.parseInt(cmd.split(" ")[2]);
			
			
			SecSql sql = SecSql.from("SELECT COUNT(*) > 0");
			sql.append("FROM article");
			sql.append("WHERE id = ?", id);
			
			int articleCount = DBUtil.selectRowIntValue(conn, sql);

			if (articleCount == 0) {
				System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
				return 0;
			}
				
			System.out.printf("== %d번 게시물 수정 ==\n", id);
			System.out.printf("새 제목 : ");
			String newTitle = sc.nextLine();
			System.out.printf("새 내용 : ");
			String newBody = sc.nextLine();
			
			sql = new SecSql();
			sql.append("UPDATE article");
			sql.append("SET updateDate = NOW(),");
			sql.append("title = ?,", newTitle);
			sql.append("`body` = ?", newBody);
			sql.append("WHERE id = ?", id);
			
			DBUtil.update(conn, sql);
			
			System.out.println(id + "번 글이 수정되었습니다");
		}
		else if (cmd.startsWith("article detail")) {
			
			if (cmd.equals("article detail")) {
				System.out.println("게시물 번호를 입력해 주세요.");
				return 0;
			}
			
			int id = Integer.parseInt(cmd.split(" ")[2]);
			
			SecSql sql = SecSql.from("SELECT *");
			sql.append("FROM article");
			sql.append("WHERE id = ?", id);
			
			Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);
			
			if (articleMap.isEmpty()) {
				System.out.printf("%d번 게시물은 존재하지 않습니다.", id);
				return 0;
			}
			
			Article article = new Article(articleMap);
			
			System.out.printf("== %d번 게시물 상세보기 ==\n", id);
			System.out.printf("번호 : %d\n", article.id);
			System.out.printf("작성일 : %s\n", article.regDate);
			System.out.printf("수정일 : %s\n", article.updateDate);
			System.out.printf("제목 : %s\n", article.title);
			System.out.printf("내용 : %s\n", article.body);
			
		}
		else if (cmd.startsWith("article delete")) {
			
			if (cmd.equals("article delete")) {
				System.out.println("게시물 번호를 입력해 주세요.");
				return 0;
			}
			
			int id = Integer.parseInt(cmd.split(" ")[2]);
			
			
			SecSql sql = SecSql.from("SELECT COUNT(*) > 0");
			sql.append("FROM article");
			sql.append("WHERE id = ?", id);
			
			int articleCount = DBUtil.selectRowIntValue(conn, sql);
			
			if (articleCount == 0) {
				System.out.printf("%d번 게시물은 존재하지 않습니다.", id);
				return 0;
			}
			
			sql = new SecSql();
			sql.append("DELETE FROM article");
			sql.append("WHERE id = ?", id);
			
			DBUtil.delete(conn, sql);
			
			System.out.printf("%d번 게시물을 삭제했습니다.", id);
			
		}
		
		if (cmd.equals("member join")) {
			
			String name = null;
			String loginId = null;
			String loginPw = null;
			String loginPwCheck = null;
			
			while (true) {
				
				System.out.println("이름 : ");
				name = sc.nextLine().trim();
				
				if(name.length() == 0) {
					System.out.println("이름을 입력해주세요.");
					continue;
				}
				
				break;
			}
			
			while (true) {
				
				System.out.println("아이디 : ");
				loginId = sc.nextLine();
				
				if(name.length() == 0) {
					System.out.println("아이디을 입력해주세요.");
					continue;
				}
				
				SecSql sql = new SecSql();
				
				sql.append("SELECT COUNT(*) > 0");
				sql.append("FROM `member`");
				sql.append("WHERE loginId = ?", loginId);
				
				boolean isLoginId = DBUtil.selectRowBooleanValue(conn, sql);
				
				if (isLoginId) {
					System.out.println("이미 사용중인 아이디 입니다.");
					continue;
				}
				break;
			}
			
			while (true) {
				
				System.out.println("비밀번호 : ");
				loginPw = sc.nextLine();
				
				if(name.length() == 0) {
					System.out.println("비밀번호를 입력해주세요.");
					continue;
				}
				
				while (true) {
					
					System.out.println("비밀번호 확인 : ");
					loginPwCheck = sc.nextLine();
					
					if(loginPwCheck.length() == 0) {
						System.out.println("비밀번호 확인을 입력해주세요.");
						continue;
					}
					
					break;

				}
				
				if (loginPw.equals(loginPwCheck) == false) {
					
					System.out.println("비밀번호가 틀립니다.");
					System.out.println("비밀번호를 다시 입력해 주세요.");
					
					continue;
				}
				break;
				
			}
			
			SecSql sql = new SecSql();
			sql.append("INSERT INTO `member`");
			sql.append("SET regDate = NOW(),");
			sql.append("updateDate = NOW(),");
			sql.append("loginId = ?,", loginId);
			sql.append("loginPw = ?,", loginPw);
			sql.append("name = ?", name);
			
			
			DBUtil.insert(conn, sql);
			
			System.out.println("회원가입 되었습니다.");
			
			
		}
		return 0;
	}
}