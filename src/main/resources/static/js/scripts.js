let province = ""; //경기도, 서울(특별시) 등등
let city = ""; //강남구 등등
let city_num = new Array(17); //도시 목록
setupCity();

//start btn
$("#start").on("click", () => {
    $("#first_div").hide(1000);
    $("#second_div").show(2000);
});

//nearby find
$("#nearby").on("click", () => {
    $("#second_div").hide(1000);
    $("#nearby_div").show(2000);
});

$(".button-province").on("click", (e) => {
    let pro = e.target.innerText;
    let city = getCity(pro);
    let col = 0; //0..1...2...3
    province = pro; //등록
    let child = "";
    let cur = city_num[city][col];
    let newLine = city_num[city].length > 40 ? 5 : city_num[city].length > 24 ? 4 : 3 // 새로운 행 기준
    while (cur !== null && cur !== undefined) {
        let index = (col + 1) % newLine;
        let curLength = cur.length;
        if (index === 1)
            child += "<tr>"; //행의 시작
        if (curLength > 3) //도시가 길경우
            child += "<td><a class=\"btn btn-primary btn-xl button-select button-city-big button-city\" onclick='selectCity(this)'>" + cur + "</a></td>";
        else
            child += "<td><a class=\"btn btn-primary btn-xl button-select button-city\" style='width: 120pt' onclick='selectCity(this)'>" + cur + "</a></td>"
        if (index === 0)
            child += "</tr>"; //행의 끝
        col++;
        cur = city_num[city][col];
    }
    if (child.charAt(child.length - 1) !== ">")
        child += "</tr>" //tr로 끝나지 않음
    $("#nearby_tb").empty(1000).append(child); //테이블 제거
    if (city === 7) {
        //세종시 특별처리
    }
});


function getCity(province) {
    switch (province) {
        case "서울":
            return 0;
        case "부산":
            return 1;
        case "대구":
            return 2;
        case "인천":
            return 3;
        case "광주":
            return 4;
        case "대전":
            return 5;
        case "울산":
            return 6;
        case "세종":
            return 7;
        case "경기":
            return 8;
        case "강원":
            return 9;
        case "충북":
            return 10;
        case "충남":
            return 11;
        case "전북":
            return 12;
        case "전남":
            return 13;
        case "경북":
            return 14;
        case "경남":
            return 15;
        case "제주":
            return 16;
        default:
            return 0;
    }
}

function setupCity() {
    city_num[0] = ['강남구', '강동구', '강북구', '강서구', '관악구', '광진구', '구로구', '금천구', '노원구', '도봉구', '동대문구', '동작구', '마포구', '서대문구', '서초구', '성동구', '성북구', '송파구', '양천구', '영등포구', '용산구', '은평구', '종로구', '중구', '중랑구'];
    city_num[1] = ['강서구', '금정구', '남구', '동구', '동래구', '부산진구', '북구', '사상구', '사하구', '서구', '수영구', '연제구', '영도구', '중구', '해운대구', '기장군'];
    city_num[2] = ['남구', '달서구', '동구', '북구', '서구', '수성구', '중구', '달성군'];
    city_num[3] = ['계양구', '남구', '남동구', '동구', '부평구', '서구', '연수구', '중구', '강화군', '옹진군', '미추홀구'];
    city_num[4] = ['광산구', '남구', '동구', '북구', '서구'];
    city_num[5] = ['대덕구', '동구', '서구', '유성구', '중구'];
    city_num[6] = ['남구', '동구', '북구', '중구', '울주군'];
    city_num[7] = []; //세종은 없음
    city_num[8] = ['고양시 덕양구', '고양시 일산동구', '고양시 일산서구', '과천시', '광명시', '광주시', '구리시', '군포시', '김포시', '남양주시', '동두천시', '부천시', '부천시 원미구', '성남시 분당구', '성남시 수정구', '성남시 중원구', '수원시 영통구', '수원시 권선구', '수원시 장안구', '수원시 팔달구', '시흥시', '안산시 단원구', '안산시 상록구', '안성군', '안성시', '안양시 동안구', '안양시 만안구', '양주시', '여주시', '오산시', '용인시 기흥구', '용인시 수지구', '용인시 처인구', '의왕시', '의정부시', '이천시', '파주시', '평택시', '하남시', '화성시', '가평군', '양평군', '연천군', '포천시'];
    city_num[9] = ['강릉시', '삼척시', '원주시', '춘천시', '태백시', '양구군', '영월군', '인제군', '정선군', '철원군', '평창군', '홍천군', '화천군', '횡성군'];
    city_num[10] = ['청주시 상당구', '청주시 흥덕구', '청주시 서원구', '청주시 청원구', '충주시', '괴산군', '보은군', '영동군', '옥천군', '음성군', '진천군', '증평군'];
    city_num[11] = ['계롱시', '공주시', '논산시', '보령시', '서산시', '아산시', '천안시', '천안시 동남구', '천안시 서북구', '금산군', '당진시', '부여군', '서천군', '예산군', '청양군', '태안군', '홍성군'];
    city_num[12] = ['군산시', '김제시', '남원시', '익산시', '전주시 덕진구', '전주시 완산구', '정읍시', '고창군', '무주군', '부안군', '순창군', '완주군', '임실군', '진안군'];
    city_num[13] = ['광양시', '나주시', '목포시', '순천시', '여수시', '고흥군', '곡성군', '구례군', '담양군', '무안군', '보성군', '신안군', '영광군', '영암군', '완도군', '장성군', '진도군', '함평군', '해남군', '화순군'];
    city_num[14] = ['경산시', '경주시', '구미시', '김천시', '문경시', '상주시', '안동시', '영주시', '영천시', '포항시 남구', '포항시 북구', '고령군', '군위군', '봉화군', '성주군', '예천군', '울릉군', '의성군', '청도군', '청송군', '칠곡군'];
    city_num[15] = ['거제시', '김해시', '밀양시', '사천시', '양산시', '진주시', '창원시', '창원시 마산합포구', '창원시 마산회원구', '창원시 성산구', '창원시 의창구', '창원시 진해구', '통영시', '거창군', '고성군', '남해군', '산청군', '의령군', '창녕군', '하동군', '함안군', '함양군', '합천군'];
    city_num[16] = ['서귀포시', '제주시'];
}

function selectCity(target) {
    city = target.innerText;
}