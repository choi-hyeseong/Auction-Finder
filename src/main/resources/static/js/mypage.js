function load() {
    //member logic
    loadMember()
    //heart logic
    loadHeart()
}

function loadHeart() {
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
function loadMember() {
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
}

function addContent(res) {
    for (let i = 0; i < res.length; i++) {
        let additional = "";
        additional += "<div class=\"col-md-6 jumbo\" style='margin-top: 10px; margin-bottom: 10px; background-color: #F5F5F5'>"
        additional += "<div class=\"h-100 p-5 bg-light border rounded-3\" style='position:relative;'>"
        additional += "<p>" + res[i].court + "</p>"
        additional += "<p>" + res[i].auctionValue + "</p>"
        additional += "<button style='position: absolute; right: 10px; top: 10px; border: 1px solid gray' onclick='deleteHeart(this)'>X</button>"
        additional += "</div></div>"
        $("#heart").append(additional)
    }
}

function deleteHeart(obj) {
    let parent = obj.parentElement.children;
    let court = parent[0].innerText;
    let auctionValue = parent[1].innerText;
    if (confirm("'" + court + " " + auctionValue + "' 해당 관심 항목을 제거하시겠습니까?")) {
        $.ajax({
            url: "/api/heart",
            async: true,
            type: "delete",
            dataType: "text", //text로 해야 json인가 리턴값 확인되는듯
            data: {
                court: court,
                auctionValue: auctionValue
            },
            success: (res) => {
                console.log(res);
                $("#heart").empty()
                loadHeart()
            },
            error: (res) => {
                console.log(res.status)
                if (res.status === 401)
                    alert("찜 기능은 로그인 이후 사용가능합니다.")
            }
        })
    }

}