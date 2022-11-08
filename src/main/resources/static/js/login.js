window.addEventListener('load', () => {
    //안된이유 => script를 head / body에 안적음, load이후 안전하게 동적으로 추가안함.
    $("#login_btn").on("click", () => {
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

    })
})