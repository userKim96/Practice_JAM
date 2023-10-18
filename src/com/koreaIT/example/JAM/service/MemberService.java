package com.koreaIT.example.JAM.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.koreaIT.example.JAM.Article;
import com.koreaIT.example.JAM.Member;
import com.koreaIT.example.JAM.dao.MemberDao;

public class MemberService {
	
	private MemberDao memberDao;
	
	public MemberService(Connection conn) {
		this.memberDao = new MemberDao(conn);
	}

	public boolean isLoginId(String loginId) {
		return memberDao.isLogId(loginId);
	}

	public void doJoin(String loginId, String loginPw, String name) {
		
		memberDao.doJoin(loginId, loginPw, name);
		
	}


	public Member getMember(String loginId) {
		Map<String, Object> memberMap = memberDao.getMember(loginId);
		
		if(memberMap.isEmpty()) {
			return null;
		}
		
		return new Member(memberMap);
	}

}
