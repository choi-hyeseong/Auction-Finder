<!DOCTYPE html>
<html lang="kr">
<script src="http://code.jquery.com/jquery-latest.js"></script>
<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>Creative - Start Bootstrap Theme</title>
    <!-- Favicon-->
    <link rel="icon" type="image/x-icon" href="assets/favicon.ico"/>
    <!-- Core theme CSS (includes Bootstrap)-->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body id="page-top" style="height: 1500px" onload="onLoad()">
<!-- Navigation-->
<nav class="navbar navbar-expand-lg navbar-dark fixed-top py-3 bg-dark" id="mainNav">
    <div class="container px-4 px-lg-5">
        <a class="navbar-brand">경매어때?</a>
        <button class="navbar-toggler navbar-toggler-right" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false"
                aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ms-auto my-2 my-lg-0">
                <li class="nav-item"><a class="nav-link" href="/map">매물검색</a></li>
                <li class="nav-item"><a class="nav-link" href="/board">자유게시판</a></li>
                <li class="nav-item"><a class="nav-link" href="/mypage">마이페이지</a></li>
                <!--<li class="nav-button" style="padding-top: 1rem; padding-left: 1rem">
                    <button type="button" style="border: 1px solid #555555; background-color: #ffffff; color: white; border-radius: 2em; width: 50px; height: 50px"></button>
                </li>-->
            </ul>
        </div>
    </div>
</nav>
<!-- Masthead-->
<header class="masthead" style="height: 100%; margin-top: 150px;">
    <div class="container px-4 px-lg-5 h-100">
        <h2 style="margin-bottom: 100px">게시글 수정</h2>
        <form action="../api/board/write" method="post" id="form">
            <label for="title" style="margin: 0px 15px 15px 0;font-weight: 700; font-size: 15pt">제목</label>
            <input type="text" class="form-control" id="title" name="title" placeholder="제목을 입력해주세요">
            <label for="content" style="margin: 15px 15px 15px 0; font-weight: 700; font-size: 15pt">내용</label>
            <textarea type="text" class="form-control" id="content" name="content" rows=5 style="height: 300px" placeholder="내용을 입력해주세요"></textarea>
            <h5 style="margin-top: 30px; font-weight: 700">이미지 (클릭시 삭제가 가능합니다.)</h5>
            <li class="list-group-item" style="height: 200px" >
                <div id="images" style="width: 100%; height: 100%"></div>
            </li>
            <label style="margin: 15px 15px 15px 0; font-weight: 700; font-size: 15pt">이미지 첨부</label>
            <p></p>
            <input type="file" id="file" multiple style="margin-left: 35px;" accept="image/*">
            <button type="button" class="btn" id="submit" style="float: right; margin-top: 15px; background: #adb3bd; color: #ffffff">수정</button>

        </form>
    </div>
</header>
<!-- Bootstrap core JS-->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<!-- Core theme JS-->
<script src="../js/edit.js"></script>
</body>
</html>
