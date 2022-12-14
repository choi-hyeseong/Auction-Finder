<!DOCTYPE html>
<html lang="kr">
<script src="http://code.jquery.com/jquery-latest.js"></script>

<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>회원수정</title>
    <!-- Favicon-->
    <link rel="icon" type="image/x-icon" href="assets/favicon.ico"/>
    <!-- Core theme CSS (includes Bootstrap)-->
    <link href="../css/register.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <!-- Core theme JS-->
    <script src="../js/memberEdit.js"></script>
</head>
<body onload="onLoad()">
<div class="container">
    <div class="input-form-backgroud row">
        <div class="input-form col-md-12 mx-auto">
            <h4 class="mb-3">회원수정</h4>
            <form class="validation-form" onsubmit="return false" novalidate>
                <div class="row">
                    <div class="col-md-3 mb-3">
                        <label for="name">아이디</label>
                        <input type="text" class="form-control" id="name" placeholder="" value="" disabled required>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-3 mb-3">
                        <label for="nickname">닉네임</label>
                        <input type="text" class="form-control" id="nickname" placeholder="" value=""  onkeydown="clear(this.id)" required>
                        <div class="invalid-feedback" id="nickFeedback">
                            닉네임을 입력해주세요.
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="email">이메일</label>
                        <input type="email" class="form-control" id="email" placeholder="example@example.com" onkeydown="clear(this.id)"
                               required>
                        <div class="invalid-feedback" id="emailFeedback">
                            이메일을 입력해주세요.
                        </div>
                    </div>
                </div>

                <hr class="mb-4">
                <div class="mb-4"></div>
                <button class="btn btn-primary btn-lg btn-block" type="submit">수정 완료</button>
            </form>
        </div>
    </div>
    <footer class="my-3 text-center text-small">
        <p class="mb-1">&copy; 2022 HS</p>
    </footer>
</div>
</body>
<!-- Bootstrap core JS-->

</html>
