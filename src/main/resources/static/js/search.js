let type;
let content;

function load(page) {
    let query = location.search.toString().replace("?", "").split("&")
    for (let i = 0; i < query.length; i++) {
        if (query[i].includes("type"))
            type = decodeURIComponent(query[i].replace("type=", ""))
        else if (query[i].includes("content"))
            content = decodeURIComponent(query[i].replace("content=", ""))
    }
    if (type === undefined || type === null || content === undefined || content === null)
        return;
    $("#select").val(type)
    $("#search-value").val(content)
    $.ajax({
        url: "../../api/board/search?page=" + page + "&type=" + type + "&content=" + content,
        type: "GET",
        dataType: "json", //로그인할때는 application/json 쓰지말고, datatype으로 보냄
        success: function (response) {
            console.log(response)
            let content = response.content;
            if (content.length === 0)
                return; //더미데이터 남겨놓기
            $("#body").empty();
            for (let i = 0; i < content.length; i++) {
                let tr = "";
                tr += "<tr onclick='location.href = \"/board/detail?id=" + content[i].id.toString().trim() + "\"'>"
                tr += "<td>" + content[i].id + "</td>";
                tr += "<td>" + content[i].title + "</td>";
                tr += "<td>" + content[i].nickName + "</td>";
                tr += "<td>" + content[i].localDateTime.toString().replace("T", " ") + "</td>";
                tr += "<td>" + content[i].viewCount + "</td>";
                tr += "</tr>"
                $("#body").append(tr);
            }
            $("#page").empty();
            let total = response.totalPages;
            for (let i = 0; i < total; i++) {
                let li = "";
                li += "<li class='page-link'><a href='#' onclick='changePage(this)'>" + (i+1) + "</a></li>"
                $("#page").append(li)
            }

        }
    })
}

function changePage(obj) {
    load(obj.innerText - 1);
}


$("#search").on("click", () => {
    let type = $("#select").val()
    let content = $("#search-value").val()
    if (type !== "" && content !== "")
        location.href = "/board/search?type=" + type + "&content=" + content;
})