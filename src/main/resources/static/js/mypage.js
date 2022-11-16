function load() {
    //member logic
    $.ajax({
        url: "../api/user",
        async: true,
        type: "get",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        success: (res) => {
            $("#id").text(res.userId);
            $("#nick").text(res.nickName);
            $("#email").text(res.email);
            $("#rank").text(res.role);

        },
        error: (error) => {
            console.log(error)
        }
    })
    //heart logic
    $.ajax({
        url: "../api/heart",
        async: false,
        type: "get",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        success: (res) => {
            addContent(res)
        },
        error: (error) => {
            console.log(error)
        }
    })
}

function addContent(res) {
    for (let i = 0; i < res.length; i++) {
        let additional = "";
        additional += "<div class=\"col-md-6 jumbo\" style='margin-top: 10px; margin-bottom: 10px; background-color: #F5F5F5'>"
        additional += "<div class=\"h-100 p-5 bg-light border rounded-3\">"
        additional += "<p>" + res[i].court + "</p>"
        additional += "<p>" + res[i].auctionValue + "</p>"
        additional += "</div></div>"
        $("#heart").append(additional)
    }
}