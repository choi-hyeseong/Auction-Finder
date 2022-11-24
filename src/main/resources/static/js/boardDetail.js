$(".modal").click(() => {$(".modal").toggle()})
function onImageClick(obj) {
    let img = new Image();
    img.src = obj.src
    $(".modalBox").html(img)
    $(".modal").show()
}

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
                $("#id").text(response.id)
                $("#title").text(response.title);
                $("#author").text(response.nickName);
                $("#viewCount").text(response.viewCount);
                $("#date").text(response.localDateTime.toString().replace("T", " "))
                $("#content").text(response.content)
                if (!response.editable) {
                    $("#edit").hide();
                    $("#delete").hide();
                }
                $("#comment").empty()
                for (let i = 0; i < response.replyList.length; i++) {
                    let comment = "";
                    comment += "<li class=\"list-group-item\">";
                    comment += "<i class=\"fas fa-comment\"> " + response.replyList[i].nickName + " " + response.replyList[i].localDateTime.toString().replace("T", " ") + "</i>";
                    comment += "<button style=\"float: right; background: transparent; border: 1px solid gray; height: 30px; width: 30px\" onclick='replyDelete(this)'>X</button>";
                    comment += "<p>" + response.replyList[i].content + "</p>";
                    comment += "<p style='float:right; visibility: hidden; width: 1px; height: 1px;'>" + response.replyList[i].id + "</p>";
                    comment += "</li>";

                    $("#comment").append(comment);
                }
                $("#images").empty()
                for (let i = 0; i < response.fileList.length; i++) {
                    let image = "";
                    image += "<img style='width: 200px; margin-right: 15px; height: 100%' src='" + window.location.origin + "/api/board/image/" + response.fileList[i].id + "' onclick='onImageClick(this)'>"
                    $("#images").append(image)
                }

            },
            error: function (response) {
                alert(response.responseJSON.message.toString().split("\n")[0])
            }
        })
    }
}

$("#delete").on("click", () => {
    if (confirm("해당 게시글을 삭제하시겠습니까?")) {
        $.ajax({
                url: "../api/board/delete/" + $("#id").text(),
                type: "DELETE",
                dataType: "text",
                success: function (response) {
                    console.log(response)
                    location.href = "/board"
                },
                error: function (response) {
                    console.log(response)
                    alert("error")
                }

            }
        )
    }
})

$("#edit").on("click", () => {
    location.href = "/board/edit?id=" + $("#id").text()
})

$("#submit").on("click", () => {
    $.ajax({
            url: "../api/board/reply/write",
            type: "POST",
            dataType: "application/json",
            data: {
                id: $("#id").text(),
                content: $("#commentData").val()
            },
            success: function (response) {
            },
            error: function (response) {
                if (response.status === 201) {
                    alert("댓글이 작성되었습니다.")
                    location.href = ""
                }
                else
                    console.log(response);
            }

        }
    )
})

function replyDelete(obj) {
    let writeId = obj.parentElement.children[3].innerHTML;
    if (confirm("해당 댓글을 삭제하시겠습니까?")) {
        $.ajax({
                url: "../api/board/reply/delete/" + writeId,
                type: "DELETE",
                success: function (response) {
                    alert("댓글이 삭제되었습니다.")
                    location.href = ""
                },
                error: function (response) {
                    console.log(response)

                }

            }
        )
    }
}