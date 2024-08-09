package com.practical.exam.common.auth;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * 로그인 한 사용자 정보 기입 (세션)
 * 
 * @author 이동준 (blog.naver.com/adgjl1125)
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserInfo {
	private String userNo;
	private String userId;
	private String userNm;
	private String auth;
	private List<String> menu;
	
	
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public List<String> getMenu() {
		return menu;
	}
	public void setMenu(List<String> menu) {
		this.menu = menu;
	}
	
}
