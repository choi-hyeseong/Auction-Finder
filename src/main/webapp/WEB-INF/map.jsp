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
    <title>Creative - Start Bootstrap Theme</title>
    <!-- Favicon-->
    <link rel="icon" type="image/x-icon" href="assets/favicon.ico"/>
    <!-- Core theme CSS (includes Bootstrap)-->
    <link href="css/styles.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css"
          integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
</head>
<body id="page-" onload="initMap()">
<!-- Masthead-->

<div id="map" style="width: 100%; height: 100%; position: relative;">
    <div class="searchbar" id="searchbar" style="position: absolute; z-index: 6;"> <!--? Z인덱스가 높으면 위에 뜨나?-->
        <a href="#" onclick="toggleSide()" class="menu_icon"><i class="fas fa-bars"></i></a>
        <input class="search_input" type="text" name="" placeholder="Search..." onfocus="this.placeholder = ''">
        <a href="#" class="search_icon"><i class="fas fa-search"></i></a>
        <img src="../assets/img/loading.gif" id="loading" style="width: 35px; height: 35px; margin: 3px; float: right;">
        <div class="suggestions suggestions_pannel"></div> <!--여기다 같이 넣으면 같이 움직이겠지 ㅎㅎㅎㅎ-->
    </div>
    <div class="wrapper" style="position: absolute;">
        <nav id="sidebar" class="active">
            <div class="sidebar-header bottom-border">
                <h3>경매어때?</h3>
            </div>

            <ul class="list-unstyled components">
                <li>

                    <a href="../" data-toggle="collapse" aria-expanded="false"><i class="fas fa-home"></i>ㅤㅤ메인</a>
                </li>
                <li>
                    <a href="../board" data-toggle="collapse" aria-expanded="false"><i class="fas fa-pen-square"></i>ㅤㅤ 자유게시판</a>
                </li>
                <li>
                    <a href="../mypage" data-toggle="collapse" aria-expanded="false"><i class="fas fa-user-edit"></i>ㅤㅤ마이페이지</a>
                </li>
            </ul>

        </nav>
    </div>
</div>

<!-- Bootstrap core JS-->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<!-- Core theme JS-->
<script src="js/map.js"></script>
<!--Kakao Api-->
<script type="text/javascript"
        src="//dapi.kakao.com/v2/maps/sdk.js?appkey=46b4ff5ade4e3ec3bcbf486566df31d7&libraries=services"></script>
</body>
</html>
