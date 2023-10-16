package com.koreaIT.example.JAM.controller;

import java.sql.Connection;
import java.util.Scanner;

import com.koreaIT.example.JAM.service.MemberService;

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
	

}
