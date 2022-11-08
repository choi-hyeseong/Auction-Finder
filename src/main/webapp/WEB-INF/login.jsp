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
    <!-- Core theme JS-->
    <script src="js/login.js"></script>
    <!-- Favicon-->
    <link rel="icon" type="image/x-icon" href="assets/favicon.ico"/>
    <!-- Core theme CSS (includes Bootstrap)-->
    <link href="css/login.css" rel="stylesheet"/>
</head><body>
<div class="wrap">
    <div class="login">
        <h2>로그인</h2>
        <p> </p>
        <div class="login_id">
            <h4>아이디</h4>
            <input type="text" name="" id="loginId" placeholder="id">
        </div>
        <div class="login_pw">
            <h4>비밀번호</h4>
            <input type="password" name="" id="password" placeholder="Password">
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
            <button type="button" id="login_btn">로그인</button>
        </div>
    </div>
</div>
</body>
<!-- Bootstrap core JS-->

</html>
