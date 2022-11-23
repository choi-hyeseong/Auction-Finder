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