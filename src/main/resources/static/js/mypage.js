function load() {
    //member logic
    loadMember()
    //heart logic
    loadHeart()
    //board logic
    loadBoardList()
}

function loadBoardList() {
    $.ajax({
        url: "../../api/board/all?userId=" + $("#id").text(),
        type: "GET",
        dataType: "json", //로그인할때는 application/json 쓰지말고, datatype으로 보냄
        success: function (response) {
            addBoard(response)
        }
    })
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
        async: false,
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

function addBoard(response) {
    $("#board").empty()
    for (let i = 0; i < response.length; i++) {
        let board = "";
        board += "<div class=\"col-md-6 jumbo\" style='margin-top: 10px; margin-bottom: 10px; background-color: #F5F5F5'>"
        board += "<div class=\"h-100 p-5 bg-light border rounded-3\" style='position:relative;'>"
        board += "<p>게시글 id : " + response[i].id + "</p>"
        board += "<p>제목 : " + response[i].title + "</p>"
        board += "<p>작성 시간 : " + response[i].localDateTime.toString().replace("T", " ") + "</p>"
        board += "<p style='width: 1px; height: 1px; visibility: hidden'>" + response[i].id + "</p>"
        board += "<button style='position: absolute; right: 10px; top: 10px; border: 1px solid gray' onclick='deleteBoard(this)'>X</button>"
        board += "</div></div>"
        $("#board").append(board);
    }
}
function deleteUser() {
    if (confirm("회원탈퇴를 진행하시겠습니까?, 생성된 모든 정보는 제거됩니다.")) {
        $.ajax({
            url: "../api/user/remove",
            async: true,
            type: "post",
            contentType: "application/json",
            success: (res) => {
                alert("회원탈퇴가 완료되었습니다. 이용해주셔서 감사합니다.")
                location.href = "../main"
            },
            error: (error) => {
                console.log(error)
            }
        })
    }
}

function deleteBoard(obj) {
   let id = obj.parentElement.children[3].innerHTML
    if (confirm("해당 게시글을 삭제하시겠습니까?")) {
        $.ajax({
            url: "/api/board/delete/" + id,
            async: true,
            type: "delete",
            dataType: "application/json", //text로 해야 json인가 리턴값 확인되는듯
            success: (res) => {
                console.log(res);
                $("#board").empty()
                loadBoardList()
            },
            error: (res) => {
                console.log(res);
                $("#board").empty()
                loadBoardList()
            }
        })
    }
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