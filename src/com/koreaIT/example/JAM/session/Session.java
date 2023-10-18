package com.koreaIT.example.JAM.session ;

import com.koreaIT.example.JAM.Member;

public class Session {
	
	public static Member loginedMember;
	public static int loginedMemberId;
	
	static {
		loginedMember = null;
		loginedMemberId = -1;
	}
	
	public static void login(Member member) {
		loginedMember = member;
		loginedMemberId = member.id;
	}

	public static void logout(Member member) {
		loginedMember = null;
		loginedMemberId = -1;
	}
	
	public static boolean isLogined() {
		return loginedMemberId != -1;
	}

}
