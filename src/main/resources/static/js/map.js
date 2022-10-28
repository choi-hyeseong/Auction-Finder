let map
function initMap(pro, city) {
    let geocoder = new kakao.maps.services.Geocoder(); //Geoocder를 지원하므로 컴포넌트 제거
    let container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
    let options;
    geocoder.addressSearch(pro + " " + city, (result, status) => {
        if (status === "OK") {
            console.log(result);
            options = { //지도를 생성할 때 필요한 기본 옵션
                center: new kakao.maps.LatLng(result[0].y, result[0].x), //지도의 중심좌표.
                level: 3 //지도의 레벨(확대, 축소 정도)
            };
        }
        else {
            options = {
                center: new kakao.maps.LatLng(35.9078,127.7669),
                level: 20
            }
        }
        map = new kakao.maps.Map(container, options); //map 객체
    })


}