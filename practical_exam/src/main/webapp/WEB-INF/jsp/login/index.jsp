<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>정보처리기사 실기 사이트</title>
<link href="css/login.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/common/ajax.js"></script>
<script>

	// 로그인 탭 눌렀을때
	function signIn() {
		document.getElementById('signUpTxt').className = 'nonactive';
		document.getElementById('signInTxt').className = 'active';
		document.getElementById('checkNum').className = 'notAdd';
		document.getElementById('inNum').className = 'notAdd';

		document.getElementById("pwDivId").remove();
		document.getElementById("phoneCheckDiv").remove();

		document.getElementById("findPw").style.display = "";
		document.getElementById("keepLogin").style.display = "";
		document.getElementById("keepLoginTxt").style.display = "";
		document.getElementById("bottomLine").style.display = "block";

		document.getElementById('userId').value = "";
		document.getElementById('password').value = "";

		let x = document.getElementById("signin");

		x.innerText = "로그인";
	}

	// 회원가입 탭 눌렀을때	
	function signUp() {
		if (document.getElementById('signUpTxt').className != 'active') {

			const box = document.getElementById("addTxtDiv");
			const newP = document.createElement('div');
			newP.innerHTML = "<input type='password' class='text' id='password2' name='password2'><span>비밀번호 확인</span>"
					+ "<input type='text' class='text' id='userName' name='userName'> <span>이름</span>";
			newP.id = 'pwDivId';
			box.appendChild(newP);

			const box2 = document.getElementById("addPhoneNum");
			const newP2 = document.createElement('div');

			newP2.innerHTML = "<div><input type='text' class='text' id='phoneNum' name='phoneNum' maxlength='11' onKeyup='onlyNum()'> <span id='phoneNumWord'>전화번호</span></div>"
					+ "<button type='button' id='phoneCheck' onclick='checkNumBtn()'>인증번호발송</button>";

			newP2.id = 'phoneCheckDiv';

			box2.appendChild(newP2);

			document.getElementById("findPw").style.display = "none";
			document.getElementById("keepLogin").style.display = "none";
			document.getElementById("keepLoginTxt").style.display = "none";
			document.getElementById("bottomLine").style.display = "none";

			document.getElementById('userId').value = "";
			document.getElementById('password').value = "";

			let x = document.getElementById("signin");
			x.innerText = "회원가입";

		}

		document.getElementById('signUpTxt').className = 'active';
		document.getElementById('signInTxt').className = 'nonactive';
	}

	// 공백확인 함수    
	function checkExistData(eleId, dataName) {
		if (eleId.value == "") {
			alert(dataName + " 입력해주세요!");
			eleId.focus();
			return false;
		}
		return true;
	}

	// 아이디 유효성체크
	function checkId(id) {
		if (!checkExistData(id, "계정명을")) {
			return false;
		}
		var idRegExp = /^[A-Za-z]{1}[A-Za-z0-9]{3,19}$/;

		if (!idRegExp.test(id.value)) {
			alert("계정명이 올바르지 않습니다.\n(영어,숫자 4~20자리, 첫글자 숫자 X)");
			id.value = "";
			id.focus();
			return false;
		}
		return true; //확인이 완료되었을 때   
	}

	// 비밀번호 유효성체크
	function checkPw(id, pw1, pw2) {
		// 비밀번호가 입력되었는지 확인     
		if (!checkExistData(pw1, "비밀번호를")) {
			pw1.focus();
			return false;
		}
		// 비밀번호 확인이 입력되었는지 확인
		if (!checkExistData(pw2, "비밀번호 확인을")) {
			pw2.focus();
			return false;
		}
		// 비밀번호 유효성검사    
		var pwRegExp = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/;
		if (!pwRegExp.test(pw1.value)) {
			alert("비밀번호는 최소 8자리, 숫자,문자,특수문자 최소 1개");
			pw1.value = "";
			pw2.value = "";
			pw1.focus();
			return false;
		}
		//비밀번호와 비밀번호 확인이 맞지 않을때
		if (pw1.value != pw2.value) {
			alert("두 비밀번호가 일치하지 않습니다.");
			pw1.value = "";
			pw2.value = "";
			pw2.focus();
			return false;
		}
		//아이디와 비밀번호가 같을 때
		if (id.value == pw1.value) {
			alert("아이디와 비밀번호는 같을 수 없습니다.");
			pw1.value = "";
			pw2.value = "";
			pw1.focus();
			return false;
		}
		return true; //확인이 완료되었을 때   
	}

	// 이름 유효성체크
	function checkName(name) {
		if (!checkExistData(name, "이름을"))
			return false;

		var nameRegExp = /^[가-힣]{2,4}$/;
		if (!nameRegExp.test(name.value)) {
			alert("이름이 올바르지 않습니다.\n(한글 2~4자리)");
			name.value = "";
			name.focus();
			return false;
		}
		return true; //확인이 완료되었을 때   
	}

	// 전화번호 유효성체크
	function checkTell(tell) {
		
		if (!checkExistData(tell, "전화번호를")){
			return false;
		}

		var tellRegExp = /^01([0|1|6|7|8|9])?([0-9]{3,4})?([0-9]{4})$/;
		if (!tellRegExp.test(tell.value)) {
			alert("전화번호가 올바르지 않습니다.");
			tell.value = "";
			tell.focus() = "";
			return false;
		}
		return true; //확인이 완료되었을 때   
	}

	// 숫자가 아닌 다른값입력시 숫자뺴고 삭제
	function onlyNum() {
		var phoneNumValue = document.getElementById('phoneNum').value;
		var regex = /[^0-9]/g;				// 숫자가 아닌 문자열을 선택하는 정규식
		var result = phoneNumValue.replace(regex, "");
		document.getElementById('phoneNum').value = result;
		console.log("나오냐?");
		
	}

	// 인증번호 눌렀을때 callback
	function smsCallback(response) {
		
		if (response.responseCode == 200) {
			alert(response.responseData);
			
			document.getElementById('checkNum').className = 'text';
			document.getElementById('phoneNum').disabled = true;
			document.getElementById('inNum').className = 'inNum';
			document.getElementById('phoneCheck').innerText = '전화번호수정';
			
		} else if (response.responseCode == 424) {
			alert(response.responseData);
			document.getElementById('userId').value = "";
			form.document.getElementById('userId').focus();
		}
		
	}
	
	function signUpCallback(response) {

		if (response.responseCode == 200) {
			alert(response.responseData);
			window.location.href = '/';
		} else if (response.responseCode == 424){
			alert(response.responseData);
			document.getElementById('userId').value = "";
			document.getElementById('password').value = "";
			document.getElementById('password2').value = "";
			form.document.getElementById('userId').focus();
		} else {
			alert(response.responseData);
			document.getElementById('checkNum').value = "";
		}



	}

	// 인증번호발송 버튼 눌렀을때
	function checkNumBtn() {

		if (document.getElementById('phoneCheck').innerText == '인증번호발송') {

			if (checkOk()) {

				if (checkTell(document.getElementById('phoneNum'))) {
					
					let jsonStr = {
							"userId" : document.getElementById('userId').value,
							"phoneNum" : document.getElementById('phoneNum').value
						}

						callPostData("/sendSms", jsonStr, smsCallback);
					

					

					

				}
			}

		} else {

			document.getElementById('phoneNum').disabled = false;
			document.getElementById('checkNum').className = 'notAdd'
			document.getElementById('phoneNum').value = "";
			document.getElementById('inNum').className = 'notAdd';
			document.getElementById('checkNum').value = '';
			document.getElementById('phoneCheck').innerText = '인증번호발송';

		}

	}

	// 인증번호 맞으면 회원가입가능
	function inNum(checkNum) {

		if (document.getElementById('phoneCheck').innerText == '인증번호발송') {
			alert("전화번호 인증을 해주세요");
			return false;
		}

		if (checkNum.value == "" ) {
			alert("인증번호를 입력해주세요");
			checkNum.focus();
			return false;
		} 
		
		if (checkNum.value.length != 6) {
			alert("6자리의 인증번호를 입력해주세요.");
			checkNum.value = "";
			checkNum.focus();
			return false;
		} 

		
		return true;
	}

	// 유효성검사 통합
	
	function checkOk() {
		
		if (!checkId(document.getElementById("userId"))) {
			return false;
		} else if (!checkPw(document.getElementById('userId'), document
				.getElementById('password'), document
				.getElementById('password2'))) {
			return false;
		} else if (!checkName(document.getElementById('userName'))) {
			return false;
		} 

		return true;
		
	}
	
	function checkAll() {

		if (!checkId(document.getElementById("userId"))) {
			return false;
		} else if (!checkPw(document.getElementById('userId'), document
				.getElementById('password'), document
				.getElementById('password2'))) {
			return false;
		} else if (!checkName(document.getElementById('userName'))) {
			return false;
		}
//		else if (!inNum(document.getElementById('checkNum'))) {
//			return false;
//		}

		return true;
	}

	function callback(response) {

		if (response.responseCode == 200) {
			alert(response.responseData);
			window.location.href = '/';
		} else if (response.responseCode == 424){
			alert(response.responseData);
			document.getElementById('userId').value = "";
			document.getElementById('password').value = "";
			document.getElementById('password2').value = "";
			form.document.getElementById('userId').focus();
		} else {
			alert(response.responseData);
			document.getElementById('checkNum').value = "";
		}

	}
	function loginCallback(response) {

		if (response.responseCode == 200) {
			window.location.href = '/';
		} else {
			alert(response.responseData);
			document.getElementById('password').value = "";
			form.document.getElementById('userId').focus();
		}

	}



	// 로그인 or 회원가입 버튼 눌렀을때
	function btnClick() {
		const dataForm = document.getElementById('dataForm');
		// 로그인 버튼 눌렀을때
		if (document.getElementById("signin").innerHTML == '로그인') {
			let jsonStr = {
				"userId" : document.getElementById('userId').value,
				"password" : document.getElementById('password').value,
			}

			callPostData('/login', jsonStr, loginCallback);

		}

		// 회원가입 버튼 눌렀을때
		if (document.getElementById("signin").innerHTML == '회원가입') {

			if (checkAll()) {

				let jsonStr = {
					"userId" : document.getElementById('userId').value,
					"password" : document.getElementById('password').value,
					"userName" : document.getElementById('userName').value,
					"phoneNum" : document.getElementById('phoneNum').value,
					//"authNumber": document.getElementById('checkNum').value
				}
				
				callPostData('/signUp', jsonStr, signUpCallback);
			
			}

		}
	}
</script>
</head>
<body>
	<div class="login">
		<h2 id="signInTxt" class="active" onclick='signIn()'>로그인</h2>
		<h2 id="signUpTxt" class="nonactive" onclick='signUp()'>회원가입</h2>
		<form onsubmit="return false" id="dataForm" method="post">

			<input type="text" class="text" id="userId" name="userId"> <span>계정명</span> <input type="password" class="text" id="password" name="password"><span>비밀번호</span>
			<div id="addTxtDiv"></div>
			<div id="addPhoneNum"></div>
			<div id="addCheckNumTxt">
				<input type='text' class='notAdd' id='checkNum' name='checkNum' maxlength="6"> <span id='inNum' class='notAdd'>인증번호</span>
			</div>

			<input type="hidden" id="keepLogin" class="custom-checkbox" /><label id="keepLoginTxt" for="keepLogin"><!-- 로그인 유지 --></label>
			<button type="button" id="signin" class="signin" onclick="btnClick()">로그인</button>
			<hr id="bottomLine">
			<a id="findPw" href="#">비밀번호 찾기</a>
		</form>
	</div>
</body>
</html>