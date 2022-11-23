let boardId = -1;

$("#submit").on("click", () => {
    if (boardId !== -1) {
        $.ajax({
            url: "../../api/board/edit/" + boardId,
            type: "PUT",
            dataType: "json", //로그인할때는 application/json 쓰지말고, datatype으로 보냄
            data: {
                title: $("#title").val(),
                content: $("#content").val()
            },
            success: function (response) {
                if (response.success) {
                    alert("게시글이 수정되었습니다.")
                    location.href = "../board/detail?id=" + boardId
                }
            },
            error: function (response) {
                alert(response.responseJSON.message.toString().split("\n")[0])
            }

        })
    }
})

function onLoad() {
    let id = window.location.search.replace("?id=", "");
    if (id === undefined || id === null || id === "")
        return;
    else {
        $.ajax({
            url: "../../api/board/detail/" + id,
            type: "GET",
            dataType: "json",
            success: function (response) {
                console.log(response)
                boardId = response.id
                $("#title").val(response.title);
                $("#content").val(response.content)
                if (!response.editable) {
                    $("#submit").hide();
                }
            },
            error: function (response) {
                alert(response.responseJSON.message.toString().split("\n")[0])
            }
        })
    }
}
