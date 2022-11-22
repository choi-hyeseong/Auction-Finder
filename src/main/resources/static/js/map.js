let map
let geocoder;
let search = $(".search_input");
let recommendFirst = ['서울', '부산', '대구', '인천', '광주', '대전', '울산', '세종', '경기', '강원', '충북', '충남', '전북', '전남', '경북', '경남', '제주'];
let recommendSecond = [];
let parsedCity = [];
let markers = [];
let index = -1;
let startX, startY, startOverlayPoint, customoverlay;
let parsedData = [];


$("#loading").hide();
search.keyup((e) => {
    let input = search.val();
    if (e.keyCode === 13) {
        if (input.includes(" ")) {
            let res = input.split(" ");
            let city = ""
            for (let i = 1; i < res.length; i++) {
                city += res[i] + " "
            }
            areaSearch(res[0], city)
        } else {
            //특정 매물 검색
        }
    } else if (e.keyCode === 38 || e.keyCode === 40) {
        let children = $(".suggestions").children();
        if (children.length > 1) {
            if (index === -1)
                index = 0;
            else {
                //업
                if (e.keyCode === 38) {
                    if (index > 0)
                        index = index - 1;
                } else {
                    //다운
                    if (index < children.length)
                        index = index + 1;
                }
            }
            search.val(children.get(index).innerText);
        }
    } else {
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
                    let city = ""
                    for (let i = 1; i < res.length; i++) {
                        city += res[i] + " "
                    }
                    areaSearch(res[0], city)
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
            $.ajax({
                url: "api/heart",
                async: false,
                type: "get",
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                success: (heartRes) => {
                    for (let i = 0; i < res.length; i++) {
                        let focused = false;
                        for (let j = 0; j < heartRes.length; j++) {
                            if (heartRes[j].court === res[i].court && heartRes[j].auctionValue === res[i].auctionNumber) {
                                focused = true;
                                break;
                            }
                        }
                        if (!parsedData.includes(res[i]))
                            parsedData.push(res[i]);
                        for (let k = 0; k < res[i].area.length; k++) {
                            loadMarker(res[i].area[k].first, res[i].type, res[i].auctionNumber, focused);
                        }
                    }
                },
                error: (request, status, error) => {
                    for (let i = 0; i < res.length; i++) {
                        if (!parsedData.includes(res[i]))
                            parsedData.push(res[i]);
                        for (let k = 0; k < res[i].area.length; k++) {
                            loadMarker(res[i].area[k].first, res[i].type, res[i].auctionNumber, false);
                        }
                    }
                }
            })
        },
        error: (request, status, error) => {
            $("#loading").hide();
            console.log("error");
        }
    })
}

function onHeartClick(obj) {
    //하트를 클릭했으면 현재 창에 정보가 있는상태
    let check = obj.checked //눌러서 변화된 상태
    let info = $("#info-value").text().toString();
    if (!info.includes("null")) {
        let firstSplit = info.split("(");
        let court = firstSplit[1].split(",")[0].trim();
        let auctionValue = firstSplit[0].trim();
        if (check) {
            //하트 누르면
            $.ajax({
                url: "/api/heart",
                async: true,
                type: "put",
                dataType: "text", //text로 해야 json인가 리턴값 확인되는듯
                data: {
                    court: court,
                    auctionValue: auctionValue
                },
                success: (res) => {
                    console.log(res);
                },
                error: (res) => {
                    if (res.status === 401)
                        alert("찜 기능은 로그인 이후 사용가능합니다.")
                }
            })
        }
        else {
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
                },
                error: (res) => {
                    console.log(res.status)
                    if (res.status === 401)
                        alert("찜 기능은 로그인 이후 사용가능합니다.")
                }
            })
        }
    }
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
            disableDoubleClickZoom: true,
            level: 3 //지도의 레벨(확대, 축소 정도)
        };
        loadMap(container, options)
    }, (error) => {
        console.log(error.message)
        options = {
            center: new kakao.maps.LatLng(35.9078, 127.7669), //대한민국 좌표
            disableDoubleClickZoom: true,
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
    let content = document.createElement('div');
    content.style.padding = "1rem";
    content.className = 'overlay map-info';
    let innerDiv = "";
    innerDiv += "<p class='info-normal' style='color: #ffffff; font-size: 14pt; font-weight: bold; line-height: '>매물정보                 (최소가/최대가)</p>"
    innerDiv += "<a class='info-normal' style='color: #ffffff; font-size: 10pt; text-decoration: underline' id='info-value' href='#'>null()</a>"
    innerDiv += "<p class='info-normal' style='color: #ffffff; font-size: 14pt; font-weight: bold'>지역</p>"
    innerDiv += "<pre class='info-normal' style='color: #ffffff; font-size: 10pt; white-space: break-spaces'>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec ultricies magna at sagittis consequat. \nPhasellus laoreet pretium augue, a rutrum erat porta non. Vestibulum tempus urna vel velit rhoncus finibus. In hac habitasse platea dictumst. Nulla facilisi. Morbi non enim odio. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin accumsan sed urna vitae vestibulum. Nulla gravida arcu quis nisl mattis, eget posuere ligula sagittis. Nulla facilisi.</pre>"
    innerDiv += "<p class='info-normal' style='color: #ffffff; font-size: 14pt; font-weight: bold'>기타</p>"
    innerDiv += "<pre class='info-normal' style='color: #ffffff; font-size: 8pt; white-space: break-spaces'>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec ultricies magna at sagittis consequat. \nPhasellus laoreet pretium augue, a rutrum erat porta non. Vestibulum tempus urna vel velit rhoncus finibus. In hac habitasse platea dictumst. Nulla facilisi. Morbi non enim odio. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin accumsan sed urna vitae vestibulum. Nulla gravida arcu quis nisl mattis, eget posuere ligula sagittis. Nulla facilisi.</pre>"
    innerDiv += "<p class='info-normal' style='color: #ffffff; font-size: 14pt; font-weight: bold'>기간</p>"
    innerDiv += "<p class='info-normal' style='color: #ffffff; font-size: 10pt'>2022.12.25까지. (유찰 x회, 경매 x계)</p>"
    innerDiv += "<input type='checkbox' style='margin-top: 2px; margin-right: 10px' id='heart' onchange='onHeartClick(this)'>";
    innerDiv += "<button style='width: 50px; height: 50px; background: none; border: none; position:relative; bottom: 3px'><i class=\"far fa-share-square fa-2x\"></i></button>";
    content.insertAdjacentHTML('beforeend', innerDiv);
    customoverlay = new kakao.maps.CustomOverlay({
        map: map,
        content: content,
        position: map.getCenter()
    });
    addEventHandle(content, 'mousedown', onMouseDown);
// mouseup 이벤트가 일어났을때 mousemove 이벤트를 제거하기 위해
// document에 mouseup 이벤트를 등록합니다
    addEventHandle(document, 'mouseup', onMouseUp);
    $(".map-info").hide();
    kakao.maps.event.addListener(map, 'click', () => {
        $(".map-info").hide();
    })
}

function loadMarker(location, type, name, focus) {

    geocoder.addressSearch(location, (result, status) => {
        if (status === "OK") {
            let img;
            let cls = "";
            if (focus)
                cls = "focus\"";
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
            let overPosition = new kakao.maps.LatLng(result[0].y, result[0].x); //인포윈도우 표시 위치입니다
            let overContent = '<div class="speech-bubble ' + cls + '" style="width: 130%; padding: 7px; text-align: center" onclick="onMarkerClick(this)"><p style="color: #ffffff; margin-bottom: 5px">' + type + "</p><hr class='solid'>" + img + '<p style="visibility: hidden; width: 1px; height: 1px" id="mark-name">' + name + '</p></div>' // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
            let customOverlay = new kakao.maps.CustomOverlay({
                position: overPosition,
                content: overContent
            });
// 커스텀 오버레이를 지도에 표시합니다
            markers.push(customOverlay);
            customOverlay.setMap(map);
            map.setCenter(overPosition);
            $(".map-info").hide();
        }
    })

}


function onMarkerClick(obj) {
    let auctionNumber = obj.children[3].innerHTML;
    let selected = markers.filter((mark) => mark.getContent().includes(auctionNumber))
    if (selected.length === 0)
        alert("상세정보 조회에 실패했습니다. 새로고침 후 다시 시도해주세요.")
    else {
        $(".map-info").show();
        let additional = map.getLevel() * map.getLevel() * 0.00025; //확대 축소 비율에따른 위치설정
        let position = selected.at(0).getPosition();
        position.La = position.La + additional
        position.Ma = position.Ma + additional
        customoverlay.setPosition(position);
        //replace logic
        let content = customoverlay.getContent().children;
        let auctionData = parsedData.filter((val) => val.auctionNumber === auctionNumber).at(0);
        // 하트 공유 버튼까지
        if (auctionData !== undefined && auctionData !== null) {
            content[0].innerText = "매물정보 (" + auctionData.minimumValue.toLocaleString() + " / " + auctionData.checkValue.toLocaleString() + ")"
            content[1].innerText = auctionData.auctionNumber + " (" + auctionData.court + ", " + auctionData.type + ")";
            content[1].href = "../detail?court=" + auctionData.court + "&value=" + auctionData.auctionNumber;
            let areas = ""
            for (let i = 0; i < auctionData.area.length; i++) {
                areas += auctionData.area[i].first + " [" + auctionData.area[i].second + "]"
                if (i !== auctionData.area.length - 1) {
                    areas += "\n";
                }
            }
            content[3].innerText = areas;
            content[5].innerText = auctionData.extra
            content[7].innerText = auctionData.until.split("T")[0] + "까지. (" + auctionData.status + ", " + auctionData.part + ")";
        }
        $.ajax({
            url: "../api/heart/" + auctionData.auctionNumber,
            async: false,
            type: "get",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            success: (res) => {
                if (res.court !== null && res.court !== undefined)
                    $("#heart").prop('checked', true)
                else
                    $("#heart").prop('checked', false)
            },
            error: (request, status, error) => {
                $("#heart").prop('checked', false)
            }
        })

    }

}


// 커스텀 오버레이에 mousedown 했을 때 호출되는 핸들러 입니다
function onMouseDown(e) {
    // 커스텀 오버레이를 드래그 할 때, 내부 텍스트가 영역 선택되는 현상을 막아줍니다.
    if (e.preventDefault) {
        e.preventDefault();
    } else {
        e.returnValue = false;
    }

    var proj = map.getProjection(), // 지도 객체로 부터 화면픽셀좌표, 지도좌표간 변환을 위한 MapProjection 객체를 얻어옵니다
        overlayPos = customoverlay.getPosition(); // 커스텀 오버레이의 현재 위치를 가져옵니다

    // 커스텀오버레이에서 마우스 관련 이벤트가 발생해도 지도가 움직이지 않도록 합니다
    kakao.maps.event.preventMap();

    // mousedown된 좌표를 설정합니다
    startX = e.clientX;
    startY = e.clientY;

    // mousedown됐을 때의 커스텀 오버레이의 좌표를
    // 지도 컨테이너내 픽셀 좌표로 변환합니다
    startOverlayPoint = proj.containerPointFromCoords(overlayPos);

    // document에 mousemove 이벤트를 등록합니다
    addEventHandle(document, 'mousemove', onMouseMove);
}

// 커스텀 오버레이에 mousedown 한 상태에서
// mousemove 하면 호출되는 핸들러 입니다
function onMouseMove(e) {
    // 커스텀 오버레이를 드래그 할 때, 내부 텍스트가 영역 선택되는 현상을 막아줍니다.
    if (e.preventDefault) {
        e.preventDefault();
    } else {
        e.returnValue = false;
    }

    var proj = map.getProjection(),// 지도 객체로 부터 화면픽셀좌표, 지도좌표간 변환을 위한 MapProjection 객체를 얻어옵니다
        deltaX = startX - e.clientX, // mousedown한 픽셀좌표에서 mousemove한 좌표를 빼서 실제로 마우스가 이동된 픽셀좌표를 구합니다
        deltaY = startY - e.clientY,
        // mousedown됐을 때의 커스텀 오버레이의 좌표에 실제로 마우스가 이동된 픽셀좌표를 반영합니다
        newPoint = new kakao.maps.Point(startOverlayPoint.x - deltaX, startOverlayPoint.y - deltaY),
        // 계산된 픽셀 좌표를 지도 컨테이너에 해당하는 지도 좌표로 변경합니다
        newPos = proj.coordsFromContainerPoint(newPoint);

    // 커스텀 오버레이의 좌표를 설정합니다
    customoverlay.setPosition(newPos);
}

// mouseup 했을 때 호출되는 핸들러 입니다
function onMouseUp(e) {
    // 등록된 mousemove 이벤트 핸들러를 제거합니다
    removeEventHandle(document, 'mousemove', onMouseMove);
}

// target node에 이벤트 핸들러를 등록하는 함수힙니다
function addEventHandle(target, type, callback) {
    if (target.addEventListener) {
        target.addEventListener(type, callback);
    } else {
        target.attachEvent('on' + type, callback);
    }
}

// target node에 등록된 이벤트 핸들러를 제거하는 함수힙니다
function removeEventHandle(target, type, callback) {
    if (target.removeEventListener) {
        target.removeEventListener(type, callback);
    } else {
        target.detachEvent('on' + type, callback);
    }
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
