window.addEventListener('load', () => {
    $("#nickname").keyup(() => clear($("#name")))
    $("#email").keyup(() => clear($("#password")))

    const forms = document.getElementsByClassName('validation-form');
    Array.prototype.filter.call(forms, (form) => {
        form.addEventListener('submit', function (event) {
            if (form.checkValidity() === false) {
                event.preventDefault();
                event.stopPropagation();
            } else {
                let data = {
                    nickName: $("#nickname").val(),
                    email: $("#email").val()
                }
                $.ajax({
                    url: "/api/user/edit",
                    async: true,
                    type: "post",
                    contentType: "application/json",
                    data:  JSON.stringify(data),
                    success: () => {
                        console.log("updated!")
                        alert("회원 수정이 완료되었습니다.")
                        location.href = "../mypage";
                    },
                    error: (res) => {
                        let response = res.responseJSON;
                        let errors = response.message.toString().split("\n");
                        errors.forEach((err) => {
                            let obj, target;
                            if (err.startsWith("닉네임")) {
                                obj = $("#nickFeedback");
                                target = $("#nickname")
                            } else {
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

function onLoad() {
    $.ajax({
        url: "../api/user",
        async: true,
        type: "get",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        success: (res) => {
            $("#name").val(res.userId);
            $("#nickname").val(res.nickName);
            $("#email").val(res.email);
        },
        error: (error) => {
            console.log(error)
        }
    })
}


function clear(val) {
    val.removeClass("register-error");
    switch (val.attr('id')) {
        case "nickname":
            $("nickFeedback").text("닉네임을 입력해주세요.").css("display", "")
            break
        default:
            $("#emailFeedback").text("이메일을 입력해주세요.").css("display", "")
            break
    }
}
