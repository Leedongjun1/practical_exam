package com.practical.exam.cms.examination.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.practical.exam.cms.examination.dao.ExaminationDao;
import com.practical.exam.common.auth.UserInfo;

@Service("examinationService")
public class ExaminationService {

	@Resource
	UserInfo userInfo;

	@Autowired
	ExaminationDao examinationDao;

	public List<Map<String, Object>> getExamination() {
		// 과목별 출제될 문제 갯수
		List<Map<String, String>> examCntList = examinationDao.getExaminationCnt();
		// 사용자의 회차 조회 ( 갯수 + 1)
		int testNum = examinationDao.getUserTestCnt(userInfo.getUserId());

		// 랜덤 문제 20개 생성을 위한 params 기입
		HashMap<String, Object> params = new HashMap<String, Object>();

		params.put("testNum", testNum);
		params.put("userId", userInfo.getUserId());

		// 신규 문제 생성
		for (Map<String, String> data : examCntList) {
			params.put("q_type", data.get("q_type"));
			params.put("q_cnt", data.get("q_cnt"));
			examinationDao.setRandomExamination(params);
		}

		// 문제 넘버 랜덤으로 변경

		examinationDao.updateRandomNumExamination(params);

		// 생성된 문제 리스트
		List<Map<String, Object>> result = examinationDao.getExamination(params);

		// 문제 유형이 2개 이상 인풋란이 있어야 하는 문제인 경우,
		for (Map<String, Object> data : result) {
			Object ansType = data.get("qAnsType");

			if (ansType != null) {
				try {
					int ansTypeNum = Integer.parseInt((String) ansType);
					ArrayList<Integer> ansTypes = new ArrayList<Integer>();

					for (int i = 1; i <= ansTypeNum; i++) {
						ansTypes.add(i);
					}
					data.put("qAnsType", ansTypes);
				} catch (NumberFormatException e) {
					String[] ansTypesToArr = ((String) ansType).split(",");

					ArrayList<String> ansTypes = new ArrayList<String>();

					for (String ansTypeStr : ansTypesToArr) {
						ansTypes.add(ansTypeStr);
					}
					data.put("qAnsType", ansTypes);
				}
			}
		}

		return result;
	}

		
	public HashMap<String,Object> marking(HashMap<String,Object> reqData) {
	
		// 유저가 입력한 답 DB에 입력
		int testNum = Integer.parseInt((String)reqData.get("testNum"));
		
		// 유저가 입력한 정답
		List<HashMap<String,Object>> markData = (List<HashMap<String,Object>>)reqData.get("markData");
		reqData.put("userId", userInfo.getUserId());
		// 실제 정답
		List<Map<String,Object>> correctAnswer = examinationDao.correctAnswer(reqData);
		
		HashMap<String,Object> updateData = new HashMap<String,Object>();
		updateData.put("userId", userInfo.getUserId());
		updateData.put("testNum",testNum );
		
		for (int i = 0; i < 20; i++) {
			int questionNo = i+1;
//					Integer.parseInt((String)markData.get(i).get("questionNo"));
			ArrayList<String> userAnswer= (ArrayList<String>)markData.get(i).get("answer");
			updateData.put("qNo", questionNo);
			String userAns = String.join(",", userAnswer);
			updateData.put("userAnswer", userAns);
            String answerYn = "N";
            if(correctAnswer.get(i).get("qType") == null) {
            	
				String answer = (String)correctAnswer.get(i).get("correctAnswer");
				
				if(answer.contains("||")) {
					String[] answers = answer.split("||");
					int succCount = 0;
					for(String ans:answers) {
						if(ans.trim().equals(userAnswer.get(0).trim())){
							succCount = 1;
						}
					}
					if(succCount>0) {
						answerYn = "Y";
					}
				} else {					
					if(answer.trim().equals(userAnswer.get(0).trim())){
						answerYn = "Y";
					}
				}
	            
			} else {
				String answer = (String)correctAnswer.get(i).get("correctAnswer");
				String[] answers = answer.split(",");
				
				int mark = 0;
				for (int j = 0; j < answers.length; j ++) {
					if (!userAnswer.get(j).trim().equals(answers[j].trim())) {
						mark++;
					}
				}
				if(mark ==0) {
					answerYn="Y";
				}
			}
            updateData.put("answerYn", answerYn);
			examinationDao.updateAnswerYn(updateData);
		}
		HashMap<String,Object> result = new HashMap<String,Object>();
		int score = examinationDao.getMarkingScore(updateData);
		String passYn = score >= 60 ? "합격":"불합격";
		
		result.put("score", score);
		result.put("passYn", passYn);
		
		return result;
	}

}
