<!DOCTYPE html>
<html lang="kr">
<script src="http://code.jquery.com/jquery-latest.js"></script>
<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>경매어때?</title>
    <!-- Favicon-->
    <link rel="icon" type="image/x-icon" href="assets/favicon.ico"/>
    <!-- Core theme CSS (includes Bootstrap)-->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body id="page-top" style="height: 1500px" onload="load(0)">
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
        <h2 style="margin-bottom: 100px">자유게시판</h2>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>날짜</th>
                <th>조회수</th>
            </tr>
            </thead>
            <tbody id="body">
            <tr>
                <td>1</td>
                <td>테스트</td>
                <td>테스트</td>
                <td>2020.02.02</td>
                <td>-1</td>
            </tr>
            <tr>
                <td>2</td>
                <td>테스트</td>
                <td>테스트</td>
                <td>2021.02.02</td>
                <td>-2</td>
            </tr>
            </tbody>
        </table>
        <a class="btn" style="background: #adb3bd; width: 100px; color: #ffffff; float:right;"
           href="/board/write">글쓰기</a>
            <div class="input-group mb-3" style="position: relative; width: 60%; left: 20%">
                <select class="selectpicker" type="submit" id="select">
                    <option>제목</option>
                    <option>작성자</option>
                    <option>내용</option>
                </select>
                <input type="text" class="form-control" id="search-value" aria-describedby="button-addon2" placeholder="검색어를 입력해주세요.">
                <button class="btn btn-outline-secondary" type="submit" id="search">검색</button>
            </div>
        <div class="text-center">
            <ul class="pagination" style="display: -webkit-inline-flex " id="page">
                <li class="page-link"><a href="#">1</a></li>
                <li class="page-link"><a href="#">2</a></li>
                <li class="page-link"><a href="#">3</a></li>
            </ul>

        </div>
    </div>
</header>
<!-- Bootstrap core JS-->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<!-- Core theme JS-->
<script src="../js/board.js"></script>
</body>
</html>
