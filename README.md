# 경매어때?

#### 법원 부동산 경매 조회를 한눈에! (2022.10.24 ~ 2022.11.30)

##### 사용된 기술 스택 :

<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"> <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"> 
  <img src="https://img.shields.io/badge/jquery-0769AD?style=for-the-badge&logo=jquery&logoColor=white"> <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">   <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">  <img src="https://img.shields.io/badge/bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white">  <img src="https://img.shields.io/badge/fontawesome-339AF0?style=for-the-badge&logo=fontawesome&logoColor=white"> <img src="https://img.shields.io/badge/SELENIUM-43B02A?style=for-the-badge&logo=selenium&logoColor=white">
 
 ![캡처](https://user-images.githubusercontent.com/114974288/204704459-7c03e97a-c378-43af-9a64-b44678144d3c.PNG)

## 1. 제작계기


##### 부동산 가격 상승으로 인한 내집마련이 힘들었던 작년, 대법원에 나온 경매 매물을 통해 집을 시세보다 저렴하게 구입할 수 있다는 점을 알게 되었습니다. <br/>하지만, 대법원 사이트의 특성상, 한눈에 보기도 어렵고, iframe 사용을 통해 로딩 지연은 줄였지만 뒤로가기의 사용 불가등등 불편한 점이 많았습니다. <br/>따라서, 저는 지도상에서 간편하게 매물을 한눈에 확인하고, 해당 매물의 정보를 구체적으로 알아 갈 수 있는 사이트의 필요성을 느껴 제작해봤습니다.

## 2. 구성요소

##### 2-1 기본적인 CRUD가 가능한 게시판
![캡처1](https://user-images.githubusercontent.com/114974288/204705707-21680a19-4c7e-437b-8c47-aaea6896ef10.PNG)
![캡처](https://user-images.githubusercontent.com/114974288/204705794-be3615c4-7694-463b-ac9e-d366e4c6367d.PNG)

### 2-2 지도상에서 확인 가능한 경매 매물, 매물에 찜(하트)기능을 넣어 한눈에 확인이 가능함
![캡처3](https://user-images.githubusercontent.com/114974288/204705896-616de778-8f99-4a8b-a96a-1ae2f237728a.PNG)

### 2-3 2-2에서 확인한 매물의 상세정보(대법원 정보)를 확인할 수 있는 페이지
![캡처4](https://user-images.githubusercontent.com/114974288/204705995-ccafb19d-baa1-4163-a3c0-d271426d7c79.PNG)

### 2-4 찜한 매물, 작성된 게시글 및 회원정보를 수정, 탈퇴할 수 있는 마이페이지
![캡처2](https://user-images.githubusercontent.com/114974288/204706108-11ba4547-5a8f-4bdd-b4b4-f547ec2bc54e.PNG)

### 3. ERD
![제목 없는 다이어그램 drawio](https://user-images.githubusercontent.com/114974288/204710585-20bef090-802e-4a50-8a85-bec64eae6e26.svg)

### 4. 작동 방식
##### 4-1. 프론트엔드 (jsp, 톰캣)
jsp를 사용했지만, 프론트와 백엔드 로직을 분리했습니다. (ajax를 활용하여 model을 되도록 사용하지 않았으며, js 자체의 쿼리 파라미터를 요청에 사용했습니다.)

##### 4-2. 백엔드
REST Api 위주로 프론트와 분리하여 개발하였으며 (/api endpoint) 프론트에서 요청된 ajax 요청을 활용하여 셀레니움을 작동시켜 데이터를 파싱한 뒤, 캐시에 저장후 결과값을 반환하였습니다. 이 과정에서 캐시가 만료되었을경우 파싱 과정을 다시 거치며, 만료되지 않았을 경우에는 캐시된 데이터를 리턴합니다.
또한, 프론트의 검색창에서 검색할 수 있는 도시, 지역 요소등을 얻어오기 위해 HttpClient를 통해 입력값을 받아오기도 했습니다.
위 크롤링 과정에서 이뤄지는 지연으로 인한 블로킹의 발생 우려로 인해 스프링에서 제공하는 @Async 어노테이션을 활용하여 Future<T> 형태로 반환하여 블로킹을 최소화 하도록 했습니다.

### 5. 트러블슈팅
##### 5-1 M:N 관계 문제
기존 JPA가 제공하는 ManyToMany 어노테이션 사용시 중간 테이블이 생성되는 부분이 있습니다. 이 테이블은 자동 생성되기때문에 추적 및 관리에 어려움이 있을거라 판단, member_heart라는 member와 heart를 중간에서 잇는 테이블을 작성하였습니다. 위 erd에서 보시다시피, member와 member_heart는 1:N, member_heart와 heart는 N:1로 중간에서 연결하고 있습니다.
##### 5-1-1 memberheart 저장 및 조회시 저장되지 않은 상태로 인한 오류 발생
위 M:N의 중간 테이블로서 각각 데이터가 db에 저장이 된 후, member_heart를 조회 및 저장을 해야 영속성 문제가 발생하지 않았습니다. 이에 불편함을 느껴, 차라리 member_heart가 저장이 될경우 cascade옵션을 통해 영속성 전파를 이뤄지게해 한번에 저장이 가능하게 하면 될 것 같아 ManyToOne 옵션에 cascade를 넣어 영속성 전파가 이뤄지게 했습니다.
##### 5-2 스프링 시큐리티 ajax 폼 로그인
스프링 시큐리티 자체의 폼 로그인을 사용할경우 리다이렉트 요청이 이루어져 로그인이 이루어졌는지, 아닌지를 확인할 수 있습니다. (로그인 성공/실패시 핸들러 처리) 하지만, 저는 리다이렉트 방식보단 api를 통해 성공 여부와 리다이렉트 url을 보내는 방식으로 짜는게 더욱 효율적이라 생각하여 ajax로 요청을 넣었으나, failHandler에서 보낸 오류원인 값을 읽지 못하고 리다이렉트 응답만 계속 넘겨졌습니다. 그마저도 ajax요청이다 보니 브라우저 자체의 리다이렉트도 이루어지지 않아, 클라이언트는 요청이 성공했는지 확인할 방법이 없었습니다.
그래서 방법을 찾던중 failHandler의 서블렛응답 파라미터를 활용하여 응답을 작성할 수 있는 메소드를 가져와 에러메시지를 프린트 하는 방식으로 수정하였습니다. (getWriter)

### 6. 보완할점
##### 셀레니움
1. 셀레니움은 크롬, 익스플로러등 실제 브라우저를 작동시켜 데이터를 가져오는 방식으로, Jsoup등에 비해 속도가 훨씬 느린점이 있었습니다. 이 부분에 대해 도시-지역 옵션을 파싱하는 부분처럼 웹페이지 분석 이후 rest api를 통한 데이터 파싱이 이루어질 수 있게 수정을 하면 좀더 속도 향상을 이뤄낼 수 있지 않을까 라는 생각이 들었습니다. 또한, 서블렛이 멀티쓰레드로 동작한다는 점때문에 매 파싱마다 웹드라이버 객체를 생성하는 오버헤드가 발생하는데, 이 부분을 쓰레드풀에다 묶어서 메모리 누수를 좀 줄일 수 있을것 같았습니다.
2. 1번과 마찬가지로 느린 속도를 보완하기 위해 캐시를 작성하였는데, 이마저도 신규 데이터를 파싱(캐시 만료, 캐시에 데이터 존재하지 않음)할때는 속도가 느린점이 있었습니다. 따라서 사용자의 요청이 없을때 정기적으로 데이터를 비동기적으로 파싱해 캐시에 저장을 해놓는다면 이또한 api응답속도를 빠르게 할 수 있지 않을까라는 생각을 했습니다. 다만, 데이터를 비동기적으로 파싱하는 동안 사용자가 파싱중인 데이터로의 api 접근시 현재 로직상 중복 파싱이 이루어지는데, 이 부분을 효율적으로 처리할 수 있는 방식에 대해 고민을 하게 되었습니다.
##### 웹소켓
3. 현재 rest api를 통한 프론트-백엔드와의 데이터 상호작용은 클라이언트의 일방적인 api 호출을 통해 이루어졌습니다. 이 부분은 html5에서 도입된 웹소켓을 이용하여, 2번과 연계해 사용자가 웹페이지를 보고있는 동안, 서버에서는 정기적인 데이터 파싱이 이루어지고, 업데이트가 발생하였을경우 웹소켓을 통해 데이터를 전송하여 사용자가 실시간으로 데이터를 확인할 수 있도록 하면 편리하겠다는 생각을 했습니다.
##### JPA
4. db에서 데이터를 가져올때 JPA가 제공하는 findBy 메소드를 이용하여 가져오는데 이는 Optional형태로 반환이 이루어집니다. 내부 객체를 가져올때는 Optional이 비었는지, 객체가 있는지를 확인하여 가져오게 되는데, 대부분의 처리를 orElseThrow를 통해 서블렛이 에러를 핸들링 하도록 처리하게 했었습니다. 이경우 클라이언트는 어떤점이 문제인지 모르기때문에 위 ValidatorException을 처리할때처럼 핸들러를 하나 만들어서 오류를 처리하면 어땠을까라는 아쉬움이 남습니다.
5. db를 작성할때 JPA가 자동 생성해주는 쿼리 및 테이블을 활용하여 사용했었는데, 이 부분에 대해 좀더 공부해서 JPA는 부수적인 부분으로서 사용하고, 실질적인 db 테이블 생성 및 쿼리를 직접 작성해보면 좋지 않을까 라는 생각이 들었습니다.
##### API
6. api 결과값과 응답코드가 제각기 달라서 통일되지 못한 부분이 있었습니다. 어떤부분은 ResponseEntity<Integer>고, 다른부분은 ResponseEntity<ApiResponse>였으며, 응답코드는 어디서는 InternalError로 반환한경우도 있었고, 다른 부분에서는 Bad request로 반환했었습니다. 이는 프론트엔드 개발자와의 상호작용시 악조건이 되는 부분이므로 API응답시 통일된 규격을 통해 리턴을 해주고, docs제작을 통해 분명하게 명시해놓는것이 좋을 것 같아 다음부터는 수정해보려 합니다.
