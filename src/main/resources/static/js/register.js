window.addEventListener('load', () => {
    const forms = document.getElementsByClassName('validation-form');

    Array.prototype.filter.call(forms, (form) => {
        form.addEventListener('submit', function (event) {
            if (form.checkValidity() === false) {
                event.preventDefault();
                event.stopPropagation();
            }
            else {
                let data = {userId: $("#name").val(), password: $("#password").val(), nickName: $("#nickname").val(), email: $("#email").val()}
                $.ajax({
                    url: "/api/user/register",
                    async: true,
                    type: "post",
                    contentType: "application/json",
                    data: JSON.stringify(data),
                    success: (res) => {
                        console.log("created!")
                    },
                    error: () => {
                        console.log("error");
                    }
                })
            }
            form.classList.add('was-validated');
        }, false);
    });
}, false);
