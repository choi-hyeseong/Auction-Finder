<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="kr">
<script src="http://code.jquery.com/jquery-latest.js"></script>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>상세정보</title>
    <!-- Favicon-->
    <link rel="icon" type="image/x-icon" href="assets/favicon.ico"/>
    <!-- Core theme CSS (includes Bootstrap)-->
    <link href="css/styles.css" rel="stylesheet"/>
    <link href="css/detail.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css"
          integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
    <!-- CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

</head>
<body id="page" onload="load()">
<!-- Masthead-->

<div class="wrapper">
    <nav id="sidebar" style="height: 200vh;">
        <div class="sidebar-header bottom-border">
            <h3>경매어때?</h3>
        </div>
        <ul class="list-unstyled components">
            <li>
                <a href="../" data-toggle="collapse" aria-expanded="false"><i class="fas fa-home"></i>ㅤㅤ메인</a>
            </li>
            <li>
                <a href="#homeSubmenu" data-toggle="collapse" aria-expanded="false"><i class="fas fa-pen-square"></i>ㅤㅤ
                    자유게시판</a>
            </li>
            <li>
                <a href="../map" data-toggle="collapse" aria-expanded="false"><i class="fas fa-search"></i>ㅤㅤ매물검색</a>
            </li>
        </ul>
    </nav>
    <div class="align-items-md-stretch" style="vertical-align: top; width: 100%; padding: 50px">
        <h2 style="font-weight:700;text-align: left">상세정보</h2>
        <img src="../assets/img/loading.gif" id="loading" style="width: 35px; height: 35px; margin: 3px; float: left;">
        <h5 class="text" style="text-align: left" id="value">매물정보</h5>
        <div class="col-md-6 jumbo" style="margin-top: 20px; overflow: auto; width: 100%; float: none">
            <div class="h-100 p-5 bg-light border rounded-3"
                 style="vertical-align: middle; display: flex; align-items: center", id="images">
                <p>test</p>
                <p>test</p>
                <p>test</p>
                <p>test</p>
                <p>test</p>
                <p>test</p>
            </div>
        </div>
        <h5 class="text" style="text-align: left" id="money">단독주택~</h5>
        <h5 class="text" style="text-align: left" id="sell">매각기일</h5>
        <h5 class="text" style="text-align: left; font-weight: 700">물건 비고</h5>
        <pre style="text-align: left" id="extra">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec ultricies magna at sagittis consequat.
Phasellus laoreet pretium augue, a rutrum erat porta non. Vestibulum tempus urna vel velit rhoncus finibus. In hac habitasse platea dictumst. Nulla facilisi. Morbi non enim odio.
            Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin accumsan sed urna vitae vestibulum.
            Nulla gravida arcu quis nisl mattis, eget posuere ligula sagittis. Nulla facilisi.</pre>
        <h5 class="text" style="text-align: left; font-weight: 700">소재지</h5>
        <pre style="text-align: left" id="area">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec ultricies magna at sagittis consequat.
Phasellus laoreet pretium augue, a rutrum erat porta non. Vestibulum tempus urna vel velit rhoncus finibus. In hac habitasse platea dictumst. Nulla facilisi. Morbi non enim odio.
            Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin accumsan sed urna vitae vestibulum.
            Nulla gravida arcu quis nisl mattis, eget posuere ligula sagittis. Nulla facilisi.</pre>
        <h5 class="text" style="text-align: left" id="date">날짜</h5>
        <h5 class="text" style="text-align: left" id="amount">청구금액</h5>
        <h5 class="text" style="text-align: left; font-weight: 700">기일</h5>
        <table class="table" style="text-align: left" id="dateExtra">
            <thead>
            <tr>
                <th>기일</th>
                <th>기일종류</th>
                <th>기일장소</th>
                <th>최저매각가격</th>
                <th>기일결과</th>
            </tr>
            </thead>
            <tbody id="dateBody">

            </tbody>
        </table>
        <h5 class="text" style="text-align: left; font-weight: 700">목록내역</h5>
        <table class="table" style="text-align: left" id="areas">
            <thead>
            <tr>
                <th>목록번호</th>
                <th>목록구분</th>
                <th>상세내역</th>
            </tr>
            </thead>
            <tbody id="areaBody">

            </tbody>
        </table>
        <h5 class="text" style="text-align: left">감정평가요항표 요약</h5>
        <pre style="text-align: left" id="realExtra">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec ultricies magna at sagittis consequat.
Phasellus laoreet pretium augue, a rutrum erat porta non. Vestibulum tempus urna vel velit rhoncus finibus. In hac habitasse platea dictumst. Nulla facilisi. Morbi non enim odio.
            Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin accumsan sed urna vitae vestibulum.
            Nulla gravida arcu quis nisl mattis, eget posuere ligula sagittis. Nulla facilisi.</pre>

        <h3></h3>
        <button class="find" onclick="location.href = 'https://www.courtauction.go.kr/'">대법원 경매 홈페이지</button>
        <button class="find" onclick="location.href = 'https://www.auction1.co.kr/'">옥션원에서 찾아보기</button>
    </div>
</div>
<div class="modal">
    <div class="modalBox">
    </div>
</div>


<!-- Bootstrap core JS-->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="js/detail.js"></script>
</body>
</html>
