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
    <title>마이페이지</title>
    <!-- Favicon-->
    <link rel="icon" type="image/x-icon" href="assets/favicon.ico"/>
    <!-- Core theme CSS (includes Bootstrap)-->
    <link href="css/styles.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css"
          integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
</head>
<body id="page" onload="load()">
<!-- Masthead-->

<div class="wrapper">
    <nav id="sidebar">
        <div class="sidebar-header bottom-border">
            <h3>경매어때?</h3>
        </div>
        <ul class="list-unstyled components">
            <li>
                <a href="../" data-toggle="collapse" aria-expanded="false"><i class="fas fa-home"></i>ㅤㅤ메인</a>
            </li>
            <li>
                <a href="../board" data-toggle="collapse" aria-expanded="false"><i class="fas fa-pen-square"></i>ㅤㅤ
                    자유게시판</a>
            </li>
            <li>
                <a href="../map" data-toggle="collapse" aria-expanded="false"><i class="fas fa-search"></i>ㅤㅤ매물검색</a>
            </li>
        </ul>
    </nav>
    <div class="align-items-md-stretch" style="vertical-align: top; width: 100%; padding: 50px">
        <h2 style="text-align: left">마이페이지</h2>
        <h5 style="text-align: left">사용자 정보</h5>
        <div class="col-md-6 jumbo">
            <div class="h-100 p-5 bg-light border rounded-3">
                <h5 style="text-align: left; margin-top: 0; margin-bottom: 30px">현재 로그인한 사용자의 정보입니다.</h5>
                <table class="table" style="width: 45%;">
                    <tbody>
                    <tr style="border: 1px solid #000000;">
                        <td style="width: 10%">
                            <t5>아이디 </t5>
                        </td>
                        <td>
                            <t5 id="id">asdf</t5>
                        </td>
                    </tr>
                    <tr style="border: 1px solid #000000;">
                        <td>
                            <t5>닉네임 </t5>
                        </td>
                        <td>
                            <t5 id="nick">nick</t5>
                        </td>
                    </tr>
                    <tr style="border: 1px solid #000000;">
                        <td>
                            <t5>이메일 </t5>
                        </td>
                        <td>
                            <t5 id="email">email</t5>
                        </td>
                    </tr>
                    <tr style="border: 1px solid #000000;">
                        <td>
                            <t5> 등급 </t5>
                        </td>
                        <td>
                            <t5 id="rank">rank</t5>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <button class="btn btn-outline-light" type="button" onclick="location.href = '../logout'">로그아웃</button>
                <button class="btn btn-outline-light" type="button" onclick="location.href = '../mypage/edit'">회원수정</button>
                <button class="btn btn-outline-light" type="button" onclick="deleteUser()">회원탈퇴</button>
            </div>
        </div>
        <h5 style="text-align: left">작성한 게시글</h5>
        <div class="col-md-6 jumbo" style="margin-top: 20px; overflow: auto">
            <div class="p-5 bg-light rounded-3" style="height: 300px;" id="board">
            </div>
        </div>
        <h5 style="text-align: left">좋아요한 매물</h5>
        <div class="col-md-6 jumbo border" style="margin-top: 20px; overflow: auto">
            <div class="p-5 bg-light rounded-3" style="height: 300px;" id="heart">
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap core JS-->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="js/mypage.js"></script>
</body>
</html>
