<!DOCTYPE html>
<html lang="kr">

<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>로그인</title>
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="https://kit.fontawesome.com/53a8c415f1.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <!-- Core theme JS-->
    <script src="js/login.js"></script>
    <!-- Favicon-->
    <link rel="icon" type="image/x-icon" href="assets/favicon.ico"/>
    <!-- Core theme CSS (includes Bootstrap)-->
    <link href="css/login.css" rel="stylesheet"/>
</head><body>
<div class="wrap">
    <form class="validation-form login" onsubmit="return false" novalidate>
        <h2>로그인</h2>
        <p> </p>
        <div class="login_id">
            <h5>아이디</h5>
            <input type="text" class="form-control" name="" id="loginId" placeholder="id" required>
            <div class="invalid-feedback" id="idFeedback">
                아이디를 입력해주세요.
            </div>
        </div>
        <div class="login_pw">
            <h5>비밀번호</h5>
            <input type="password" name="" class="form-control" id="password" placeholder="Password" required>
            <div class="invalid-feedback" id="passFeedback">
                비밀번호를 입력해주세요.
            </div>
        </div>
        <div class="login_etc">
            <div class="checkbox">
                <input type="checkbox" name="" id=""> 로그인 유지
            </div>
            <div class="forgot_pw">
                <a href="" style="padding-right: 1rem">회원가입</a>
                <a href="">비밀번호 찾기</a>
            </div>

        </div>
        <div class="submit">
            <input type="submit" id="login_btn" value="로그인">
        </div>

    </form>
</div>
</body>
<!-- Bootstrap core JS-->

</html>
