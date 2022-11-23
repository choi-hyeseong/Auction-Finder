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
        <h2 style="margin-bottom: 100px">게시글 상세보기</h2>
        <table class="table">
            <thead>
            <tr>
                <th>게시글 id</th>
                <th>제목</th>
                <th>작성자</th>
                <th>조회수</th>
                <th>날짜</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td id="id">1</td>
                <td id="title">테스트</td>
                <td id="author">1</td>
                <td id="viewCount">1</td>
                <td id="date">1</td>
            </tr>
            </tbody>
        </table>
        <h5 style="margin-top: 30px; font-weight: 700">내용</h5>
        <pre id="content" style="font-size: 12pt; margin-top: 20px">테스트</pre>
        <a class="btn" style="background: #18ffd5; width: 100px; color: #ffffff; float:right;" href="/board" >목록</a>
        <a class="btn" id="edit" style="margin-left: 15pt; margin-right: 15pt; background: #adb3bd; width: 100px; color: #ffffff; float:right;">글수정</a>
        <a class="btn" id="delete" style="background: #FF1D18; width: 100px; color: #ffffff; float:right;" >글삭제</a>

    </div>
</header>
<!-- Bootstrap core JS-->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<!-- Core theme JS-->
<script src="../js/boardDetail.js"></script>
</body>
</html>
