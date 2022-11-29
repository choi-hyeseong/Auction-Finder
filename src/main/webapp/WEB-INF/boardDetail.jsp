<!DOCTYPE html>
<html lang="kr">
<script src="http://code.jquery.com/jquery-latest.js"></script>
<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>게시글 상세보기</title>
    <!-- Favicon-->
    <link rel="icon" type="image/x-icon" href="assets/favicon.ico"/>
    <!-- Core theme CSS (includes Bootstrap)-->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css"
          integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
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
        <h5 style="margin-top: 30px; font-weight: 700">이미지</h5>
        <li class="list-group-item" style="height: 200px" >
            <div id="images" style="width: 100%; height: 100%"></div>
        </li>
        <h5 style="margin-top: 30px; font-weight: 700">내용</h5>
        <pre id="content" style="font-size: 12pt; margin-top: 20px">테스트</pre>
        <a class="btn" style="background: #18ffd5; width: 100px; color: #ffffff; float:right;" href="/board" >목록</a>
        <a class="btn" id="edit" style="margin-left: 15pt; margin-right: 15pt; background: #adb3bd; width: 100px; color: #ffffff; float:right;">글수정</a>
        <a class="btn" id="delete" style="background: #FF1D18; width: 100px; color: #ffffff; float:right;" >글삭제</a>
        <div class="card mb-2" style="margin-top: 75px; width: 100%">
        <div class="card-header bg-light">
            <i class="far fa-comment"> 댓글</i>
        </div>
        <div class="card-body" >
            <ul class="list-group list-group-flush" id="comment">

                <li class="list-group-item">
                    <i class="fas fa-comment"> 작성자</i>
                    <button style="float: right; background: transparent; border: 1px solid gray; height: 30px; width: 30px">X</button>
                    <p>내용</p>
                </li>
                <li class="list-group-item">
                    <i class="fas fa-comment"> 작성자</i>
                    <p>내용</p>
                </li>
                <li class="list-group-item">
                    <i class="fas fa-comment"> 작성자</i>
                    <p>내용</p>
                </li>

            </ul>
            <li class="list-group-item" style="border: none">
                <textarea class="form-control" rows="3" id="commentData"></textarea>
                <button type="button" class="btn btn-dark mt-3" id="submit">댓글 작성</button>
            </li>
        </div>
    </div>

    </div>
    <div class="modal" style="margin-left: 20%">
        <div class="modalBox">
        </div>
    </div>
</header>
<!-- Bootstrap core JS-->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<!-- Core theme JS-->
<script src="../js/boardDetail.js"></script>
</body>
</html>
