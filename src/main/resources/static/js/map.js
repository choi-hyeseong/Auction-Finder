let map
let geocoder;
let search = $(".search_input");
let recommendFirst = ['서울', '부산', '대구', '인천', '광주', '대전', '울산', '세종', '경기', '강원', '충북', '충남', '전북', '전남', '경북', '경남', '제주'];
let recommendSecond = [];
let parsedCity = [];
let markers = [];
let index = -1;

$("#loading").hide();
search.keyup((e) => {
    let input = search.val();
    if (e.keyCode === 13) {
        if (input.includes(" ")) {
            let res = input.split(" ");
            areaSearch(res[0], res[1])
        }
        else {
            //특정 매물 검색
        }
    }
    else if (e.keyCode === 38 || e.keyCode === 40) {
        let children = $(".suggestions").children();
        if (children.length > 1) {
            if (index === -1)
                index = 0;
            else {
                //업
                if (e.keyCode === 38) {
                    if (index > 0)
                        index = index - 1;
                }
                else {
                    //다운
                    if (index < children.length)
                        index = index + 1;
                }
            }
            children.get(index).hover()
            search.val(children.get(index).innerText);
        }
    }
    else {
        $(".suggestions").empty() //새로운 검색어
        if (input.includes(" ")) {
            let pro = search.val().split(" ");
            if (!parsedCity.includes(pro[0]))
                loadCityDynamic(pro[0]);
            let suggests = recommendSecond.filter((t) => t.includes(search.val()))
            if (suggests.length === 0) {
                addRecommend("\"" + input + "\" 검색", search)
                return
            }
            suggests.forEach((val) => {
                let div = document.createElement('div');
                div.innerHTML = val;
                div.onclick = () => {
                    search.val(val);
                    let res = val.split(" ");
                    areaSearch(res[0], res[1])
                }
                $(".suggestions").append(div);
            })
        } else {
            let suggests = recommendFirst.filter((t) => t.includes(search.val()))
            if (suggests.length === 0) {
                addRecommend("\"" + input + "\" 검색", search)
            } else {
                suggests.forEach((val) => {
                    addRecommend(val, search)
                })
            }
        }
    }
})

function addRecommend(text, search) {
    let div = document.createElement('div');
    div.innerHTML = text;
    div.onclick = () => search.val(text);
    $(".suggestions").append(div);
}

$("#map").on("click", () => $(".suggestions").empty());

//비동기로 처리하는 이유 -> 로딩 시간이 너무 오래걸림.
function areaSearch(pro, city) {
    $("#loading").show();
    $.ajax({
        url: "http://127.0.0.1:8080/api/auction?pro=" + pro + " &city=" + city,
        async: true,
        type: "get",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        success: (res) => {
            $("#loading").hide();
            console.log(res)
            for (let i = 0; i < res.length; i++) {
                for (let k = 0; k < res[i].area.length; k++) {
                    loadMarker(res[i].area[k].first, res[i].type);
                }
            }
        },
        error: (request, status, error) => {
            $("#loading").hide();
            console.log("error");
        }
    })
}
//사이드바 토글
function toggleSide() {
    let side = $('#sidebar');
    let search = $("#searchbar");
    if (side.attr('class') === 'active') { //숨겨진경우
        search.addClass('side-css').removeClass('side-css-off')
        side.css("z-index", 5);
    } else {
        search.addClass('side-css-off').removeClass('side-css')
        side.css("z-index", 1);
    }
    side.toggleClass('active');
}

//맵 초기화
function initMap() {
    let container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
    let options;
    navigator.geolocation.getCurrentPosition((position) => {
        options = { //지도를 생성할 때 필요한 기본 옵션
            center: new kakao.maps.LatLng(position.coords.latitude, position.coords.longitude), //지도의 중심좌표.
            level: 3 //지도의 레벨(확대, 축소 정도)
        };
        loadMap(container, options)
    }, (error) => {
        console.log(error.message)
        options = {
            center: new kakao.maps.LatLng(35.9078, 127.7669), //대한민국 좌표
            level: 3
        }
        loadMap(container, options)
    })
}

function loadMap(container, options) {
    map = new kakao.maps.Map(container, options); //map 객체
    let mapTypeControl = new kakao.maps.MapTypeControl();
    map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);
    let zoomControl = new kakao.maps.ZoomControl();
    map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
    geocoder = new kakao.maps.services.Geocoder();
    geocoder.coord2RegionCode(map.getCenter().getLng(), map.getCenter().getLat(), (result, status) => {
        if (status === "OK") {
            let arr = result[0].address_name.split(" ");
            areaSearch(arr[0], arr[1])
            console.log(result[0].address_name);
        }
    })

}

function loadMarker(location, type) {

    geocoder.addressSearch(location, (result, status) => {
        if (status === "OK") {
            let img;
            type = type.replace(/[0-9]/gi, "");
            switch (type) {
                case "임야":
                case "대지":
                case "전답":
                    img = "<img src='../assets/img/field.png' style='width: 50px; height: 50px'>";
                    break;
                case "기타":
                    img = "<img src='../assets/img/building.png' style='width: 50px; height: 50px'>";
                    break
                default:
                    img = "<img src='../assets/img/house.png' style='width: 50px; height: 50px'>";
                    break;
            }
            let overContent = '<div class="speech-bubble" style="width: 130%; padding: 7px; text-align: center"><p style="color: #ffffff; margin-bottom: 5px">' + type + "</p><hr class='solid'>" + img + '</div>' // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
            let overPosition = new kakao.maps.LatLng(result[0].y, result[0].x); //인포윈도우 표시 위치입니다
            let customOverlay = new kakao.maps.CustomOverlay({
                position: overPosition,
                content: overContent
            });
// 커스텀 오버레이를 지도에 표시합니다
            markers.push(customOverlay);
            customOverlay.setMap(map);
            map.setCenter(overPosition);

        }
    })

}

function loadCityDynamic(val) {
    $.ajax({
        url: "api/city?pro=" + val,
        async: false,
        type: "get",
        crossDomain: true,
        contentType: "application/json",
        success: (res) => {
            parsedCity.push(val);
            for (let i = 0; i < res.length; i++) {
                recommendSecond.push(val + " " + res[i]);
            }
        },
        error: (request, status, error) => {
            console.log("error");
        }
    })

}
