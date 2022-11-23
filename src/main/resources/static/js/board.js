$("#submit").on("click", () => {
    $.ajax({
        url: "../../api/board/write",
        type: "POST",
        dataType: "json", //로그인할때는 application/json 쓰지말고, datatype으로 보냄
        data: {
            title: $("#title").val(),
            content: $("#content").val()
        },
        success: function (response) {
           if (response.success) {
               alert("게시글이 작성되었습니다.")
               location.href = "../board"
           }
        },
        error: function (response) {
            alert(response.responseJSON.message.toString().split("\n")[0])
        }

    })
})


function load(page) {
    $.ajax({
        url: "../../api/board/list?page=" + page,
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