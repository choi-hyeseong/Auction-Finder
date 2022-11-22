$(".modal").click(() => {$(".modal").toggle()})
function onImageClick(obj) {
    let img = new Image();
    img.src = obj.src
    $(".modalBox").html(img)
    $(".modal").show()
}
function load() {
    let param = window.location.search.replace("?", "");
    let split = param.split("&");
    let court = split[0].replace("court=", "");
    let value = split[1].replace("value=", "");
    $.ajax({
        async: true,
        url: "../api/auction/detail?court=" + court + "&value=" + value,
        type: "GET",
        success: function (response) {
            console.log(response)
            $("#images").empty()
            let image = "";
            for (let i = 0; i < response.images.length; i++) {
                image += "<img src='" + response.images[i] + "' style='height: 200px; width: 200px; margin-right: 30px' onclick='onImageClick(this)'>";
            }
            $("#images").append(image);
            $("#value").text(response.part + " - " + response.auctionValue + "(" + response.auctionNumber + ")");
            //이미지
            $("#money").text(response.type + " (" + response.minimumValue + " / " + response.checkValue + ")");
            $("#sell").text("매각기일 : " + response.endDate);
            $("#extra").text(response.extra)
            $("#area").text("");
            for (let i = 0; i < response.areas.length; i++) {
                $("#area").text($("#area").text() + response.areas[i]);
                if (i !== response.areas.length - 1) {
                    $("#area").text($("#area").text() + "\n");
                }
            }
            $("#date").text("사건접수(" + response.startDate + "), 경매개시일(" + response.auctionDate + "), 배당요구종기(" + response.bidDate + ")")
            $("#amount").text("청구금액 : " + response.askBid);
            let date = "";
            for (let i = 0; i < response.dates.length; i++) {
                date += "<tr>"
                date += "<td>" + response.dates[i].date + "</td>"
                date += "<td>" + response.dates[i].type + "</td>"
                date += "<td>" + response.dates[i].location + "</td>"
                date += "<td>" + response.dates[i].minimum + "</td>"
                date += "<td>" + response.dates[i].result + "</td>"
                date += "</tr>"
            }
            $("#dateBody").append(date)
            let list = "";
            for (let i = 0; i < response.list.length; i++) {
                list += "<tr>"
                list += "<td>" + response.list[i].number + "</td>"
                list += "<td>" + response.list[i].type + "</td>"
                list += "<td><pre>" + response.list[i].detail + "</pre></td>"
                list += "</tr>"
            }
            $("#areaBody").append(list)
            $("#realExtra").text(response.summary)
            $("#loading").hide()
        },
        error: function (response) {
            console.log(response)
        }

    })
}