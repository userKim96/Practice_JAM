package com.koreaIT.example.JAM.controller;

import java.sql.Connection;
import java.util.Scanner;

import com.koreaIT.example.JAM.Member;
import com.koreaIT.example.JAM.service.MemberService;
import com.koreaIT.example.JAM.session.Session;

public class MemberController {
	private Scanner sc;
	private MemberService memberService;
	
	public MemberController(Scanner sc, Connection conn) {
		this.memberService = new MemberService(conn);
		this.sc = sc;
	}

	public void doJOin() {
		
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
			
			boolean isLoginId = memberService.isLoginId(loginId);
			
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
		memberService.doJoin(loginId, loginPw, name);
		System.out.println("회원가입 되었습니다.");
		
	}

	public void doLogin() {
		
		if (Session.isLogined()) {
			System.out.println("로그인 상태입니다.");
			return;
		}
		
		while (true) {
			
			
			
			System.out.println("아이디 : ");
			String loginId = sc.nextLine();
			
			System.out.println("비밀번호 : ");
			String loginPw = sc.nextLine();
			
			if(loginId.length() == 0) {
				System.out.println("아이디를 입력해주세요.");
				continue;}
			if(loginId.length() == 0) {
				System.out.println("비밀번호를 입력해주세요.");
				continue;}
				
			Member member = memberService.getMember(loginId);
			
			if (member == null) {
				System.out.println("아이디 없음.");
				continue;
			}
			
			if (member.loginPw.equals(loginPw) == false) {
				System.out.println("비밀번호가 일치하지 않습니다.");
				continue;
			}
			
			Session.login(member);
			
			System.out.println("로그인 성공");
			break;
		}
	}
}
