package com.koreaIT.example.JAM;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.koreaIT.example.JAM.controller.ArticleController;
import com.koreaIT.example.JAM.controller.MemberController;
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
		
		MemberController memberController = new MemberController(sc, conn);
		ArticleController articleController = new ArticleController(sc, conn);
		
		if (cmd.equals("exit")) {
			return -1;
		}
		if (cmd.equals("article write")) {
			articleController.doWrite();

		} else if (cmd.equals("article list")) {
			articleController.showList();
			
		} else if(cmd.startsWith("article modify")) {
			articleController.doModify(cmd);
			
		}
		else if (cmd.startsWith("article detail")) {
			articleController.showDetail(cmd);
			
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
			
			memberController.doJOin();
			
			
		}
		return 0;
	}
}