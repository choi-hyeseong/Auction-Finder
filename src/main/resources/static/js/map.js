let map
let geocoder;
let search = $(".search_input");
let recommendFirst = ['서울', '부산', '대구', '인천', '광주', '대전', '울산', '세종', '경기', '강원', '충북', '충남', '전북', '전남', '경북', '경남', '제주'];
let recommendSecond = [];
let parsedCity = [];

search.keyup(() => {
    $(".suggestions").empty()
    if (search.val().includes(" ")) {
        let pro = search.val().split(" ");
        if (!parsedCity.includes(pro[0]))
            loadCityDynamic(pro[0]);
        let suggests = recommendSecond.filter((t) => t.includes(search.val()))
        suggests.forEach((val) => {
            let div = document.createElement('div');
            div.innerHTML = val;
            div.onclick = () => {
                search.val(val);
                let res = val.split(" ");
                $.ajax({
                    url: "http://127.0.0.1:8080/api/auction?pro=" + res[0] + " &city=" + res[1],
                    async: false,
                    type: "get",
                    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                    success: (res) => {
                        console.log(res)
                        for (let i = 0; i < res.length; i++) {
                            for (let k = 0; k < res[i].area.length; k++) {
                                loadMarker(res[i].area[k].first, res[i].type);
                            }
                        }
                    },
                    error: (request, status, error) => {
                        console.log("error");
                    }
                })
            }
            $(".suggestions").append(div);
        })
    }
    else {
        let suggests = recommendFirst.filter((t) => t.includes(search.val()))
        suggests.forEach((val) => {
            let div = document.createElement('div');
            div.innerHTML = val;
            div.onclick = () => search.val(val);
            $(".suggestions").append(div);
        })
    }
})

$("#map").on("click", () => $(".suggestions").empty());

function toggleSide() {
    let side = $('#sidebar');
    let search = $("#searchbar");
    if (side.attr('class') === 'active') { //숨겨진경우
        search.addClass('side-css').removeClass('side-css-off')
        side.css("z-index", 5);
    }
    else {
        search.addClass('side-css-off').removeClass('side-css')
        side.css("z-index", 1);
    }
    side.toggleClass('active');

}

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
            $.ajax({
                url: "http://127.0.0.1:8080/api/auction?pro=" + arr[0] + " &city=" + arr[1],
                async: false,
                type: "get",
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                success: (res) => {
                    console.log(res)
                    for (let i = 0; i < res.length; i++) {
                        for (let k = 0; k < res[i].area.length; k++) {
                            loadMarker(res[i].area[k].first, res[i].type);
                        }
                    }

                },
                error: (request, status, error) => {
                    console.log("error");
                }
            })
            console.log(result[0].address_name);
        }
    })

}

function loadMarker(location, type) {
    geocoder.addressSearch(location, (result, status) => {
        if (status === "OK") {
            let img;
            type = type.replace("1", "");
            switch (type) {
                case "임야":
                case "대지":
                case "전답":
                    img = "<img src='../assets/img/mountain.png' style='width: 50px; height: 50px'>";
                    break;
                case "기타":
                    img = "<img src='../assets/img/factory.png' style='width: 50px; height: 50px'>";
                    break
                default:
                    img = "<img src='../assets/img/house.png' style='width: 50px; height: 50px'>";
                    break;
            }
            let overContent = '<div style="width: 100%; background: transparent">' + img + '</div>' // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
            let overPosition = new kakao.maps.LatLng(result[0].y, result[0].x); //인포윈도우 표시 위치입니다
            let customOverlay = new kakao.maps.CustomOverlay({
                position: overPosition,
                content: overContent
            });

// 커스텀 오버레이를 지도에 표시합니다
            customOverlay.setMap(map);
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
/*
function initMap(pro, city) {
    let container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
    let options;
    let parseCity = city;
    let province = pro;
    geocoder = new kakao.maps.services.Geocoder(); //Geoocder를 지원하므로 컴포넌트 제거
    if (parseCity === "전체")
        parseCity = "";
    geocoder.addressSearch(province + " " + parseCity, (result, status) => {
        if (status === "OK") {
            console.log(result);
            options = { //지도를 생성할 때 필요한 기본 옵션
                center: new kakao.maps.LatLng(result[0].y, result[0].x), //지도의 중심좌표.
                level: 3 //지도의 레벨(확대, 축소 정도)
            };
        } else {
            options = {
                center: new kakao.maps.LatLng(35.9078, 127.7669),
                level: 20
            }
        }
        map = new kakao.maps.Map(container, options); //map 객체
        let mapTypeControl = new kakao.maps.MapTypeControl();
        map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);
        let zoomControl = new kakao.maps.ZoomControl();
        map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
        setTimeout(() => {
            $.ajax({
                url: "http://127.0.0.1:8080/api/auction?pro=" + pro + " &city=" + city,
                async: false,
                type: "get",
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                success: (res) => {
                    console.log(res)
                    for (let i = 0; i < res.length; i++) {
                        for (let k = 0; k < res[i].area.length; k++) {
                            loadMarker(res[i].area[k].first, res[i].area[k].first + ", " + res[i].checkValue.toLocaleString("ko-kr") + "원ㅤㅤ");
                        }
                    }
                },
                error: (request, status, error) => {
                    console.log("error");
                }
            })
        }, 1000);

    })
}

function loadMarker(location, txt) {
    geocoder.addressSearch(location, (result, status) => {
        if (status === "OK") {
            let marker = new kakao.maps.Marker({
                position: new kakao.maps.LatLng(result[0].y, result[0].x),
            })
            marker.setMap(map);
            let iwContent = '<div style="width: 100%; font-size: 12pt; padding: 10px;"><a>' + txt +'</a></div>' // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
            let iwPosition = new kakao.maps.LatLng(result[0].y, result[0].x); //인포윈도우 표시 위치입니다
            let info = new kakao.maps.InfoWindow({position: iwPosition, content: iwContent});
            info.open(map, marker);
        }
    })

}*/