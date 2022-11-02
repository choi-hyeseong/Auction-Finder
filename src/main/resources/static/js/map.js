let map
let geocoder;

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

}