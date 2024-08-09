package com.practical.exam.common.auth;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.practical.exam.common.utils.RequestUtils;


/**
 * API Interceptor
 * 호출 URL 및 호출 Parameter LOGGING
 * 
 * @author 이동준 (blog.naver.com/adgjl1125)
 *
 */

public class ApiInterceptor implements HandlerInterceptor {
	private static final Logger LOGGER = LogManager.getLogger(ApiInterceptor.class);
    private static final String REQUEST_METHODS_TYPE_OF_GET="GET";
    private static final String[] REQUEST_SESSION_NOT_CHECK={"/login","/", "/sendSms", "/signUp"};
    private static final String[] REQUEST_SESSION_NOT_FILE_CHECK={".css",".js"};
    @Autowired
    
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	// 현재 요청한 URL이 세션 체크 대상인지 확인
    	boolean sessionUrl = false;
    	String reqUrl = request.getRequestURI();
    	for(String uri:REQUEST_SESSION_NOT_FILE_CHECK) {
    		if(reqUrl.indexOf(uri) != -1) {
    			sessionUrl = true;
    			break;
    		}
    	}
    	if(!sessionUrl) {    		
    		for(String uri:REQUEST_SESSION_NOT_CHECK) {
    			if(uri.equals(request.getRequestURI())) {
    				sessionUrl = true;
    				break;
    			}
    		}
    	}
    	
    	// 세션 체크 대상인 경우,
    	if(!sessionUrl) {
    		HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("scopedTarget.userInfo") == null) {
				LOGGER.info("Session Expired...");
				response.sendRedirect("/");
				return false;
            } else {
            	if((session.getAttribute("scopedTarget.userInfo")) instanceof UserInfo) {
            		UserInfo user = (UserInfo)(session.getAttribute("scopedTarget.userInfo"));
            		
            		if(user == null||user.getUserId() == null) {
            			LOGGER.info("Session Expired...");
            			response.sendRedirect("/");
            			return false;
            		}
            		LOGGER.info("USER_NO => "+ user.getUserNo());
            		LOGGER.info("User_ID => "+ user.getUserId());
            	} else {  
        			LOGGER.info("Session Expired...");
        			response.sendRedirect("/");
        			return false;

            	}
            }
    	}
    	
    	//GET 방식인 경우, log를 남기지 않음
		if(request.getMethod().equals(REQUEST_METHODS_TYPE_OF_GET)) {
    		return true;
    	}
		LOGGER.info("Request URL ==> "+request.getRequestURI());
		LOGGER.info("Request Params ==>"+RequestUtils.getParameter(request));
		
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
