<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>����ó����� �Ǳ� ����Ʈ</title>
<link href="home.css" rel="stylesheet" type="text/css">
<script>

	function signIn() {
		document.getElementById('signUpTxt').className = 'nonactive';
		document.getElementById('signInTxt').className = 'active';
		
		
		const box = document.getElementById("pwDivId");
		box.remove();
		document.getElementById("findPw").style.display = "";
		document.getElementById("keepLogin").style.display = "";
		document.getElementById("keepLoginTxt").style.display = "";
		
		let x = document.getElementById("signin");
	    x.innerText="�α���"; 
	}
	
	function signUp() {
		
		if (document.getElementById('signUpTxt').className != 'active') {
			
			const box = document.getElementById("passwordDiv");
	       	const newP = document.createElement('div');
	        newP.innerHTML = "<br> <input type='password' class='text' name='passwordChk'><span>��й�ȣ Ȯ��</span>";
	        newP.id = 'pwDivId';
	        box.appendChild(newP);
	        document.getElementById("findPw").style.display = "none";
	        document.getElementById("keepLogin").style.display = "none";
	        document.getElementById("keepLoginTxt").style.display = "none";
	        
	        let x = document.getElementById("signin");
	        x.innerText="ȸ������"; 
		} 
		
		document.getElementById('signUpTxt').className = 'active';
		document.getElementById('signInTxt').className = 'nonactive';
		
    
		
	}
	
	
	
</script>
</head>
<body>
	<div class="login">
		<h2 id="signInTxt" class="active" onclick='signIn()'>�α���</h2>
		<h2 id="signUpTxt" class="nonactive" onclick='signUp()'>ȸ������</h2>
		<form>
			<input type="text" class="text" name="username"> <span>������</span>
			<br>
			<br> <input type="password" class="text" name="password"><span>��й�ȣ</span>
			<div id="passwordDiv"></div>
			
			<br> <input type="checkbox" id="keepLogin"
				class="custom-checkbox" /> <label id="keepLoginTxt" for="keepLogin">�α��� ����</label>
			<button id="signin" class="signin">�α���</button>
			<hr>
			<a id="findPw" href="#">��й�ȣ ã��</a>
		</form>
	</div>
</body>
</html>