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
		const divA = document.getElementById('passwordDiv');
		  
		 divA.remove();
	}
	
	function signUp() {
		document.getElementById('signUpTxt').className = 'active';
		document.getElementById('signInTxt').className = 'nonactive';
		
		const box = document.getElementById("passwordDiv");
       	const newP = document.createElement('div');
        newP.innerHTML = "<br> <input type='password' class='text' name='passwordChk'><span>��й�ȣ Ȯ��</span>";
        box.appendChild(newP);
    
		
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
			
			<br> <input type="checkbox" id="checkbox-1-1"
				class="custom-checkbox" /> <label for="checkbox-1-1">�α��� ����</label>
			<button class="signin">�α���</button>
			<hr>
			<a href="#">��й�ȣ ã��</a>
		</form>
	</div>
</body>
</html>