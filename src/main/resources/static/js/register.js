window.addEventListener('load', () => {
    $("#name").keyup(() => clear($("#name")))
    $("#password").keyup(() => clear($("#password")))

    const forms = document.getElementsByClassName('validation-form');
    Array.prototype.filter.call(forms, (form) => {
        form.addEventListener('submit', function (event) {
            if (form.checkValidity() === false) {
                event.preventDefault();
                event.stopPropagation();
            } else {
                let data = {
                    userId: $("#name").val(),
                    password: $("#password").val(),
                    nickName: $("#nickname").val(),
                    email: $("#email").val()
                }
                $.ajax({
                    url: "/api/user/register",
                    async: true,
                    type: "post",
                    contentType: "application/json",
                    data: JSON.stringify(data),
                    success: () => {
                        console.log("created!")
                        alert("회원가입이 완료되었습니다. 로그인 페이지로 이동합니다.")
                        location.href = "../login";
                    },
                    error: (res) => {
                        let response = res.responseJSON;
                        let errors = response.message.toString().split("\n");
                        errors.forEach((err) => {
                            let obj, target;
                            if (err.startsWith("아이디")) {
                                obj = $("#idFeedback");
                                target = $("#name")
                            }
                            else if (err.startsWith("비밀번호")){
                                obj = $("#passFeedback")
                                target = $("#password")
                            }
                            else if (err.startsWith("닉네임")) {
                                obj = $("#nickFeedback");
                                target = $("#nickname")
                            }
                            else {
                                obj = $("#emailFeedback");
                                target = $("#email")
                            }
                            target.addClass("register-error")
                            obj.text(err).css("display", "block");
                        })
                        /*
                        아이디는 필수로 입력해야합니다.
                        비밀번호는 필수로 입력해야합니다.
                        닉네임은 필수로 입력해야합니다.
                        이메일은 필수 입력값입니다.
                        올바르지 않은 이메일 형식입니다.
                        닉네임이 이미 존재합니다.
                        아이디가 이미 존재합니다.
                        */
                    }
                })
            }
            form.classList.add('was-validated');
        }, false);
    });
}, false);


function clear(val) {
    val.removeClass("register-error");
    switch (val.attr('id')) {
        case "name":
            $("#idFeedback").text("아이디를 입력해주세요.").css("display", "")
            break
        case "password":
            $("#passFeedback").text("비밀번호를 입력해주세요.").css("display", "")
            break
        case "nickname":
            $("nickFeedback").text("닉네임을 입력해주세요.").css("display", "")
            break
        default:
            $("#emailFeedback").text("이메일을 입력해주세요.").css("display", "")
            break
    }
}
