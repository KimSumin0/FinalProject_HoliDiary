<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
</head>
<body>
<script type="text/javascript">

  var naver_id_login = new naver_id_login("rX3BsIpQkj6CJiShI2rn", "http://localhost/main/social.login.naver");
  // 접근 토큰 값 출력
  //alert(naver_id_login.oauthParams.access_token);
  // 네이버 사용자 프로필 조회
  naver_id_login.get_naver_userprofile("naverSignInCallback()");
  // 네이버 사용자 프로필 조회 이후 프로필 정보를 처리할 callback function
  function naverSignInCallback() {
    //console.log(naver_id_login.getProfileData('name'));
    //console.log(naver_id_login.getProfileData('nickname'));
    //console.log(naver_id_login.getProfileData('id'));
    //console.log(naver_id_login.getProfileData('email'));
    //console.log(naver_id_login.getProfileData('profile_image'));
    
    let naverID = naver_id_login.getProfileData('id');
    let email = naver_id_login.getProfileData('email');
    let id = email.split('@')[0];
    console.log(naverID);
    console.log(id);

    $.ajax({
		url : "id.check",
		type : "GET",
		dataType : "text",
		data : {"naverID" : naverID, "userID" : id},
		success : function(getData){
			console.log(getData);
			if(getData == 1){
				alert('로그인성공')
				window.close();
					window.opener.location.href = "http://localhost/main/";
			}else{
				alert('회원가입을 먼저 진행해주세요');
				window.close();
				window.opener.location.href = "http://localhost/main/login.go";
			}
			},
		error : function(request,status,error) {
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		} 
  })
     
  }
</script>
	
</body>
</html>