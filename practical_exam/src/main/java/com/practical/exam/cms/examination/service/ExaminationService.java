package com.practical.exam.cms.examination.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practical.exam.cms.common.service.RedisService;
import com.practical.exam.cms.examination.dao.ExaminationDao;
import com.practical.exam.common.auth.UserInfo;

@Service("examinationService")
public class ExaminationService {
	
	@Autowired
	private RedisService redisService;

	@Resource
	UserInfo userInfo;

	@Autowired
	ExaminationDao examinationDao;

	public List<Map<String, Object>> getExamination() {
		// 과목별 출제될 문제 갯수
		List<Map<String, String>> examCntList = examinationDao.getExaminationCnt();
		System.out.println("한번 볼까나 ? " + examCntList.toString());
		
		List<Integer> CountList = examCntList.stream().map(map -> {
			Object qCntObj = map.get("q_cnt");
	        if (qCntObj instanceof String) {
	            return Integer.parseInt((String) qCntObj);  // String을 Integer로 변환
	        } else if (qCntObj instanceof Integer) {
	            return (Integer) qCntObj;  // 이미 Integer라면 캐스팅
	        } else {
	            throw new IllegalArgumentException("Unsupported type for q_cnt: " + qCntObj.getClass().getName());
	        }
			
		}).toList();
		int totalCount = CountList.stream()
	            .reduce(0, Integer::sum);
		
		if(totalCount > 20) {
			int fixCount = totalCount - 20;
			for(int i = fixCount; i > 0; i-- ) {
				Random random = new Random();

		        List<Integer> targetType = examCntList.stream()
		            .filter(map -> {
		                Object qCntObj = map.get("q_cnt");
		                if (qCntObj instanceof String) {
		                    return Integer.parseInt((String) qCntObj) > 0;
		                } else if (qCntObj instanceof Integer) {
		                    return (Integer) qCntObj > 0;
		                } else {
		                    throw new IllegalArgumentException("Unsupported type for q_cnt: " + qCntObj.getClass().getName());
		                }
		            })
		            .map(map -> {
		                Object qTypeObj = map.get("q_type");
		                if (qTypeObj instanceof String) {
		                    return Integer.parseInt((String) qTypeObj);
		                } else if (qTypeObj instanceof Integer) {
		                    return (Integer) qTypeObj;
		                } else {
		                    throw new IllegalArgumentException("Unsupported type for q_type: " + qTypeObj.getClass().getName());
		                }
		            })
		            .toList();

		        int randType = targetType.get(random.nextInt(targetType.size()));
		        System.out.println("당첨? " + randType);
		        examCntList.stream()
		            .filter(map -> {
		                Object qTypeObj = map.get("q_type");
		                return String.valueOf(randType).equals(qTypeObj.toString());
		            })
		            .forEach(map -> {
		            	int qCntValue = classCast(map.get("q_cnt"));
		                String finalCnt = String.valueOf(Integer.toString(qCntValue - 1));
		                map.put("q_cnt", finalCnt);
		            });
			}
		}
		
		System.out.println("바뀐거 볼까나 ? " + examCntList.toString());
		
		// 사용자의 회차 조회 ( 갯수 + 1)
		int testNum = examinationDao.getUserTestCnt(userInfo.getUserNo());
		
		// 랜덤 문제 20개 생성을 위한 params 기입
		HashMap<String, Object> params = new HashMap<String, Object>();

		params.put("testNum", testNum);
		params.put("userNo", userInfo.getUserNo());

		// 신규 문제 생성
		for (Map<String, String> data : examCntList) {
			System.out.println("뭐나오길래" + data.toString());
            int qCntValue = classCast(data.get("q_cnt"));
            
			params.put("q_type", data.get("q_type"));
			params.put("q_cnt", qCntValue);
			System.out.println("이걸봤어야하네" + params.toString());
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
		System.out.println("최종 문제목록1"+result.toString());
		// q_no 기준으로 오름차순 정렬
		result.sort(Comparator.comparing(map -> (Integer) map.get("qNo")));
		
		List<Map<String, Object>> selfMarkExamList = result.stream().filter(e -> (boolean)e.get("selfMarkingYn")).toList();
		System.out.println("셀프마킹 문제목록1"+selfMarkExamList.toString());
		redisService.setValue("SML" +"-"+userInfo.getUserNo() +"-"+ testNum, selfMarkExamList);
		
		System.out.println("레디스에 들어가있니? " + redisService.getValue("SML"+"-"+userInfo.getUserNo()+"-"+ testNum));
		
		System.out.println("최종 문제목록2"+result.toString());
		return result;
	}
	
	private int classCast(Object qCntObj) {
		int qCntValue;
        if (qCntObj instanceof String) {
            qCntValue = Integer.parseInt((String) qCntObj);
        } else if (qCntObj instanceof Integer) {
            qCntValue = (Integer) qCntObj;
        } else {
            throw new IllegalArgumentException("Unsupported type for q_cnt: " + qCntObj.getClass().getName());
        }
		return qCntValue;
	}

		
	public HashMap<String,Object> marking(HashMap<String,Object> reqData) {
		System.out.println("뭐가 오긴오나? " + reqData.toString());
		// 유저가 입력한 답 DB에 입력
		int testNum = Integer.parseInt((String)reqData.get("testNum"));
		List<Map<String, Object>> redisExamList = (List)redisService.getValue("SML"+"-"+userInfo.getUserNo()+"-"+testNum);
		System.out.println("나올려나..? => ? " + redisExamList.toString());
		
		if(redisExamList != null) {
			HashMap<String,Object> result = new HashMap<String,Object>();
			for(Map<String, Object> map : redisExamList	) {
				String qNo = String.valueOf(map.get("qNo"));
				int seqNo = (Integer)map.get("qSeq");
				Map<String, Object> answers = examinationDao.getAnswer(seqNo).entrySet()
						.stream()
						.sorted(Map.Entry.comparingByKey())
						.collect(Collectors.toMap(
						        Map.Entry::getKey,
						        Map.Entry::getValue,
						        (oldValue, newValue) -> oldValue,  // 중복 키 처리 (없으면 기본 값 유지)
						        LinkedHashMap::new  // 순서가 유지되는 LinkedHashMap에 수집
						    ));
				
				List<Object> answerList = new ArrayList<>(answers.values());
				System.out.println("신기한대? " + answerList.toString());

				map.put("answers", answerList);
				System.out.println("금학두기" + answers.toString());
				List<Map<String, Object>> markList = (List<Map<String, Object>>)reqData.get("markData");
				System.out.println("금두꺼비111" + markList.toString());
				Optional<Object> answer = markList.stream().filter(q -> q.get("questionNo").toString().equals(qNo))
						.map(q -> q.get("answer"))
						.findFirst();
				if(answer.isPresent()) {
					map.put("userAnswers", answer.get());
				} else {
					map.put("userAnswers", new ArrayList<>());
				}
			}
			
			result.put("markData", redisExamList);
			result.put("selfMark", "Y");
			System.out.println("최종 정리 .. => " + result.toString());
			return result;
		}
		System.out.println("잘 됐으려나?" + redisExamList.toString());
		// 유저가 입력한 정답
		List<HashMap<String,Object>> markData = (List<HashMap<String,Object>>)reqData.get("markData");
		reqData.put("userNo", userInfo.getUserNo());
		// 실제 정답
		List<Map<String,Object>> correctAnswer = examinationDao.correctAnswer(reqData);
		
		HashMap<String,Object> updateData = new HashMap<String,Object>();
		updateData.put("userNo", userInfo.getUserNo());
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
						if(ans.replaceAll(" ", "").equals(userAnswer.get(0).replaceAll(" ", ""))){
							succCount = 1;
						}
					}
					if(succCount>0) {
						answerYn = "Y";
					}
				} else {					
					if(answer.replaceAll(" ", "").equals(userAnswer.get(0).replaceAll(" ", ""))){
						answerYn = "Y";
					}
				}
	            
			} else {
				String answer = (String)correctAnswer.get(i).get("correctAnswer");
				String[] answers = answer.split(",");
				
				int mark = 0;
				for (int j = 0; j < answers.length; j ++) {
					if (!userAnswer.get(j).replaceAll(" ", "").equals(answers[j].replaceAll(" ", ""))) {
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
