let boardId = -1;

$("#submit").on("click", () => {
    if (boardId !== -1) {
        let form = new FormData($('#form')[0]);
        for (let i = 0; i < $('#file')[0].files.length; i++) {
            form.append('file', $('#file')[0].files[i])
        }
        $.ajax({
            url: "../../api/board/edit/" + boardId,
            type: "PUT", //put도 폼 전달이 되네..?
            data: form,
            enctype: 'multipart/form-data',
            contentType: false,
            processData: false,
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
                for (let i = 0; i < response.fileList.length; i++) {
                    let image = "";
                    image += "<img style='width: 200px; margin-right: 15px; height: 100%' src='" + window.location.origin + "/api/board/image/" + response.fileList[i].id + "' onclick='imageDelete(this)'>"
                    $("#images").append(image)
                }
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

function imageDelete(obj) {
    let split = obj.src.toString().split("/");
    let id = split[split.length - 1]
        if (confirm("해당 이미지를 삭제하시겠습니까?")) {
            $.ajax({
                    url: "../api/board/image/delete/" + id,
                    type: "DELETE",
                    dataType: "text",
                    success: function (response) {
                        console.log(response)
                        alert("해당 이미지가 삭제되었습니다.")
                        obj.remove()
                    },
                    error: function (response) {
                        console.log(response)
                        alert("error")
                    }

                }
            )
        }
}
