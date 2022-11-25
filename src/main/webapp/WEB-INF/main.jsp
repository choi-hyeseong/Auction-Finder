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
    <link href="css/styles.css" rel="stylesheet"/>
</head>
<body id="page-top">
<!-- Navigation-->
<nav class="navbar navbar-expand-lg navbar-light fixed-top py-3" id="mainNav">
    <div class="container px-4 px-lg-5">
        <a class="navbar-brand" href="#">경매어때?</a>
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
<header class="masthead">
    <div class="container px-4 px-lg-5 h-100">
        <div class="row gx-4 gx-lg-5 h-100 align-items-center justify-content-center text-center" id="first_div">
            <div class="col-lg-8 align-self-end">
                <h1 class="text-white font-weight-bold">쉽고 빠른 매물검색!</h1>
                <hr class="divider"/>
            </div>
            <div class="col-lg-8 align-self-baseline">
                <p class="text-white-75 mb-5">누구나 쉽고, 편리하게 확인할 수 있는 법원 경매 매물!</p>
                <a class="btn btn-primary btn-xl button-select" id="start">시작하기!</a>
            </div>
        </div>
        <div class="row gx-4 gx-lg-5 h-100 align-items-center justify-content-center text-center" id="second_div"
             style="display: none">
            <div class="col-lg-8 align-self-end">
                <h1 class="text-white font-weight-bold">매물 검색방식을 선택해주세요.</h1>
                <hr class="divider"/>
            </div>
            <div class="col-lg-8 align-self-baseline button-padding">
                <p class="text-white-75 mb-5"></p>
                <a class="btn btn-primary btn-xl button-select" id="nearby" style="margin-left: 1.5rem; margin-right: 1.5rem">지역별검색</a>
                <a class="btn btn-primary btn-xl button-select" id="address">주소지검색</a>
            </div>
        </div>
        <div class="row gx-4 gx-lg-5 h-75 justify-content-center text-center" id="nearby_div" style="display: none">
            <div class="col-lg-8 align-self-center">
                <h1 class="text-white font-weight-bold">지역을 선택해주세요.</h1>
                <hr class="divider"/>
            </div>
            <table>
                <tbody id="nearby_tb" class="align-baseline">
                <tr>
                    <td><a class="btn btn-primary btn-xl button-select button-province">서울</a></td>
                    <td><a class="btn btn-primary btn-xl button-select button-province">부산</a></td>
                    <td><a class="btn btn-primary btn-xl button-select button-province">대구</a></td>
                </tr>
                <tr>
                    <td><a class="btn btn-primary btn-xl button-select button-province">인천</a></td>
                    <td><a class="btn btn-primary btn-xl button-select button-province">광주</a></td>
                    <td><a class="btn btn-primary btn-xl button-select button-province">대전</a></td>
                </tr>
                <tr>
                    <td><a class="btn btn-primary btn-xl button-select button-province">울산</a></td>
                    <td><a class="btn btn-primary btn-xl button-select button-province">세종</a></td>
                    <td><a class="btn btn-primary btn-xl button-select button-province">경기</a></td>
                </tr>
                <tr>
                    <td><a class="btn btn-primary btn-xl button-select button-province">강원</a></td>
                    <td><a class="btn btn-primary btn-xl button-select button-province">충북</a></td>
                    <td><a class="btn btn-primary btn-xl button-select button-province">충남</a></td>
                </tr>
                <tr>
                    <td><a class="btn btn-primary btn-xl button-select button-province">전북</a></td>
                    <td><a class="btn btn-primary btn-xl button-select button-province">전남</a></td>
                    <td><a class="btn btn-primary btn-xl button-select button-province">경북</a></td>
                </tr>
                <tr>
                    <td><a class="btn btn-primary btn-xl button-select button-province">경남</a></td>
                    <td><a class="btn btn-primary btn-xl button-select button-province">제주</a></td>
                </tr>

                </tbody>
            </table>
        </div>
    </div>
</header>
<!-- Bootstrap core JS-->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<!-- Core theme JS-->
<script src="js/scripts.js"></script>
</body>
</html>
