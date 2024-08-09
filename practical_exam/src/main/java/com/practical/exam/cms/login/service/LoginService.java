package com.practical.exam.cms.login.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.practical.exam.cms.login.dao.LoginDao;
import com.practical.exam.common.auth.UserInfo;
import com.practical.exam.common.sms.service.SmsService;

@Service("loginService")
public class LoginService {
	

	@Resource
	private UserInfo userInfo;
	
	@Autowired
	private LoginDao loginDao;
	
	@Autowired
	private SmsService smsService;
	
	/**
	 * 로그인 처리 
	 * 
	 * @param reqData
	 * @return
	 */
	
	public ResponseEntity<Object> login(HashMap<String,String> reqData) {
		System.out.println("어떻게 담겨오나" + reqData.toString());
		Map<String,String> reslt = loginDao.getUserInfo(reqData);
		
		
		String result = "아이디가 존재하지 않거나, 패스워드가 올바르지 않습니다.";
		
		// 로그인 성공 시,
		if(reslt != null) {			
			
			
			// 세션 생성
			userInfo.setUserNo(reslt.get("userNo"));
			userInfo.setUserId(reslt.get("userId"));
			userInfo.setUserNm(reslt.get("userNm"));

			result = "정상적으로 로그인되었습니다.";
			
			return new ResponseEntity<>(result,HttpStatus.OK);
		} else {
			
			return new ResponseEntity<>(result,HttpStatus.UNAUTHORIZED);
		}
	}
	
	
	/**
	 * 회원가입 신청
	 * 
	 * @param reqData
	 * @return
	 */
	public ResponseEntity<Object> signUp(HashMap<String,String> reqData) {
		
		// 아이디 중복여부 확인
		
		String result = "해당 계정 중복입니다. 다시 입력해주세요.";
		
		if(!loginDao.idDuplicated(reqData)) {
			
//			if(!loginDao.authNumberValid(reqData)) {
//				result = "인증번호가 옳바르지 않거나, 인증번호 세션이 만료되었습니다. ";
//				return new ResponseEntity<>(result,HttpStatus.UNPROCESSABLE_ENTITY);
//			}
			
			loginDao.addUserInfo(reqData);
			result = "회원가입 성공하셨습니다 !";
			return new ResponseEntity<>(result,HttpStatus.OK);
		} else {
			
			return new ResponseEntity<>(result,HttpStatus.FAILED_DEPENDENCY);
		}
	}
	
	
	
}
