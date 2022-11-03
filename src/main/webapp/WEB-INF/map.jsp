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
    <div class="searchbar" style="position: absolute; z-index: 5;"> <!--? Z인덱스가 높으면 위에 뜨나?-->
        <a href="#" class="menu_icon"><i class="fas fa-bars"></i></a>
        <input class="search_input" type="text" name="" placeholder="Search...">
        <a href="#" class="search_icon"><i class="fas fa-search"></i></a>
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
