window.addEventListener('load', () => {
    $("#name").keyup(() => clear($("#name")))
    $("#password").keyup(() => clear($("#password")))

    const forms = document.getElementsByClassName('validation-form');
    Array.prototype.filter.call(forms, (form) => {
        form.addEventListener('submit', function (event) {
            if (form.checkValidity() === false) {
                event.preventDefault();
                event.stopPropagation();
            }
            else {
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
                        alert("로그인에 성공하였습니다.")
                    },

                })
            }
            form.classList.add('was-validated');
        }, false);
    });
}, false);