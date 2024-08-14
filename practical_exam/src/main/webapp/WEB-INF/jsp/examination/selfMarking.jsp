<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자가 채점</title>


<link href="/css/shamExamStyle.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/js/common/ajax.js"></script>


<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/Swiper/4.0.7/css/swiper.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/Swiper/4.0.7/js/swiper.min.js"></script>

<script>
	function callback(response) {
		document.getElementById('examinationBody').innerHTML = response.responseData;
	}

	function markingAnswer() {
		console.log("체크포인트");
		alert("금사마귀 " + JSON.stringify(${selfMarking.markData}));
        console.log("금사마귀 " + JSON.stringify(${selfMarking.markData}));
		let markingAnswer = new Array();

		for (let i = 1; i <= 20; i++) {
			let data = new Object();
			//문제 번호
			data.questionNo = i;
			//seq 정보
			data.seq = document.getElementById('seq-hidden-' + i).value;
			//답변 유형
			let ansType = document.getElementById('answer-type-' + i).value;

			let ansArr = new Array();
			// TEXT AREA 인 경우,
			if (ansType == 'area') {
				ansArr.push(document.getElementById('answer-area-' + i).value);
			} else {
				for (let k = 0; k < ansType; k++) {
					ansArr.push(document.getElementById('answer-type-input-'
							+ i + "-" + k).value);
				}
			}

			data.answer = ansArr;

			markingAnswer.push(data)

		}

		let postData = new Object();
		// 회차정보
		postData.testNum = document.getElementById('testNum-1').value;
		postData.markData = markingAnswer;

		var jsonData = JSON.stringify(postData);
		
		callPostData('/examination/marking', jsonData, callback);
	}

	// 정답 입력칸에 [,] 못쓰게 제한
	function characterCheck(obj) {

		var regExp = /[,]/gi;

		if (regExp.test(obj.value)) {

			alert("[, ]는 입력하실수 없습니다.");

			obj.value = obj.value.substring(0, obj.value.length - 1); // 입력한 특수문자 한자리 지움

		}

	}
	

    function test() {
    	console.log("체크포인트");
        console.log("금사마귀 " + JSON.stringify(${selfMarking.markData}));
    }


</script>

</head>
<body id="examinationBody">
		<div class="swiper-container">
			<div class="swiper-wrapper">
				
				<c:forEach var="data" items="${selfMarking.markData}" varStatus="status">
					<!-- 회차 -->
					<input type="hidden" id="testNum-${data.qNo}"
						value="${data.testNum }">

					<div class="swiper-slide">

						<div class="slide-inner">
							<div id="question-txt">${data.qNo}.${data.qTitle}</div>

							<c:choose>
								<c:when test="${empty data.imgUrl }">
									<div class="question-picture-box">${data.qContent}</div>
								</c:when>
								<c:otherwise>
									<div class="question-picture-box">
										<img src="${data.imgUrl}" alt="실기 문제 ">
									</div>
								</c:otherwise>
							</c:choose>

							<div>
								<input type="hidden" id="seq-hidden-${data.qNo}"
									value="${data.qSeq}">
								<p>정답</p>

								<c:choose>
									<c:when test="${data.qAnsType == null }">
										<input type="hidden" id="answer-type-${data.qNo}" value="area">
										<textarea id="answer-area-${data.qNo}" name="answer-area"
											class="answerBox" onkeyup="characterCheck(this)"
											onkeydown="characterCheck(this)" cols="50" rows="10"></textarea>
									</c:when>
									<c:otherwise>
										<input type="hidden" id="answer-type-${data.qNo}"
											value="${fn:length(data.qAnsType)}">

										<c:forEach var="qAnsT" items="${data.qAnsType}"
											varStatus="status">
											<c:out value="${qAnsT}" />
											<input type="text"
												id="answer-type-input-${data.qNo}-${status.index}" value=""
												onkeyup="characterCheck(this)"
												onkeydown="characterCheck(this)"
												style="width: 200px; height: 45px;" />
										</c:forEach>
									</c:otherwise>
								</c:choose>

							</div>
						</div>
					</div>
				</c:forEach>
			</div>


			<div class="swiper-button-next swiper-button-black"></div>
			<div class="swiper-button-prev swiper-button-black"></div>

		</div>


		<!-- <div id="pagination" class="swiper-pagination"></div> -->

		<div class="copy">
			<button class="submit" type="button" onclick="markingAnswer()">제출하기</button>
			<button class="test" type="button" onclick="test()">테스트</button>
		</div>

	<script type="text/javascript" src="/js/common/selfMarking.js"></script>
</body>
</html>