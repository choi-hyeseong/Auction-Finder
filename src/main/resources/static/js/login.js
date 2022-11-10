window.addEventListener('load', () => {
    $("#loginId").keyup(() => clear($("#loginId")))

    const forms = document.getElementsByClassName('validation-form');
    Array.prototype.filter.call(forms, (form) => {
        form.addEventListener('submit', function (event) {
            if (form.checkValidity() === false) {
                event.preventDefault();
                event.stopPropagation();
            } else {
                let username = $("#loginId").val()
                let password = $("#password").val()
                $.ajax({
                    url: "/login",
                    type: "POST",
                    dataType: "json", //로그인할때는 application/json 쓰지말고, datatype으로 보냄
                    data: {
                        username: username,
                        password: password
                    },
                    success: function (response) {
                        alert("로그인에 성공하였습니다.") //실질적으로 작동안함 => 리다이렉트
                    },
                    error: function (response) {
                        console.log(response)
                        if (response.status === 200) {
                            //어쨋든 결과
                            let result = response.responseText;
                            if (result.includes("url:")) {
                                result = result.replace("url:", "");
                                location.href = result;
                            }
                            else {
                                $("#idFeedback").text(response.responseText.replace("\r\n", ""));
                                $("#loginId").addClass("register-error")
                                $("#idFeedback").css("display", "block")
                            }
                        }
                    }

                })
            }
            form.classList.add('was-validated');
        }, false);
    });
}, false);


function clear(val) {
    val.removeClass("register-error");
    $("#idFeedback").text("아이디를 입력해주세요.").css("display", "")

}