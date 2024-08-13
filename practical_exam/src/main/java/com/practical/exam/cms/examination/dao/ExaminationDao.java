package com.practical.exam.cms.examination.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExaminationDao {
	
	//과목별 출제될 문제갯수
	List<Map<String,String>> getExaminationCnt();
	
	// 사용자 시험 회차
	int getUserTestCnt(String userId);
	
	//과목별 랜덤으로 문제 생성
	void setRandomExamination(Map<String,Object> params);
	
	// 사용자ID 및 TEST_NUM 으로 문제번호 RANDOM으로 변경
	void updateRandomNumExamination(Map<String,Object> params);
	
	// 1번 ~ 20번 정렬 후 LIST 출력
	List<Map<String,Object>> getExamination(Map<String,Object> params);
	
	// 세션아이디와 마지막testCnt 비교해서 seq 가져오기
	List<Map<String,Object>> getAnswerSeq(Map<String,Object> params);
	
	// 유저가 입력한 답 DB에 입력
	void updateUserInputAnswer(int testNum, int seq, String userAnswer);
	
	//정답 리스트 불러오기
	List<Map<String,Object>> correctAnswer(Map<String,Object> params);
	
	//정답 업데이트
	void updateAnswerYn(Map<String,Object> params);
	
	//채점한 정보 점수 
	int getMarkingScore(Map<String,Object> params);
	
	// seq 를 주고 문제 정답가져오기
	Map<String, Object> getAnswer(int seqNo);
}
