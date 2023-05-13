# 자바로 직접 구현하는 HTTP Server
자바에서 제공하는 httpserver 클래스 사용 없이 HTTP 프로토콜을 사용하는 웹서버를 ServerSocket만 사용하여 구현한다. 

서버를 구현하기 위해서는 client가 보내오는 Request Message 구조를 이해하고 분석하여 원하는 동작을 얻어야 하며 Response Message 구조에 맞춰서 구성하여 Client에게 보내는 작업을 해야 한다.
HttpServer 클래스를 사용하는 것보다 불편하지만 HTTP 프로토콜을 직접 구현하면서 HTTP 동작 원리 이해를 넓히는 프로젝트였다.

### 서버가 제공하는 동작
-   ServerSocket으로 HTTP WEB 서버 구현
    -   Client Request Message 분석하여 원하는 동작 파악
    -   Response Message 구조대로 상황에 맞는 상태 코드와 Json 데이터 바디로 전송
-   GET /time -> 현재 시간을 json 에 담아서 알려줌(크롬 브라우저 통신 가능)
-   POST /text/{textid} -> Body로 전달된 문자열을 서버가 저장
-   GET /text/{textid} -> 저장된 문자열을 알려줌
-   DELETE /text/{textid} -> 저장된 문자열을 삭제
-   GET /image -> jpeg 이미지를 다운로드



# 블로그 포스트
[Java로 직접 구현하는 HTTP Server](https://coding-business.tistory.com/125)

&nbsp;&nbsp;&nbsp;[HTTP 서버를 편리하게 만들 수 있는 HttpServlet 이해와 사용법](https://coding-business.tistory.com/126)

**<HTTP 와 웹브라우저 이해>** 

[HTTP 프로토콜 이해와 HTTP 버전 별 특징](https://coding-business.tistory.com/6)

[URI URL 차이와 구조](https://coding-business.tistory.com/45)

[REST API, RESTFUL에 대한 이해](https://coding-business.tistory.com/13)

[리퀘스트 메소드와 HTTP 상태 코드 이해](https://coding-business.tistory.com/7)

[웹 브라우저 와 데이터 저장소 이해](https://coding-business.tistory.com/121)

# ServerSocket으로 HTTPServer 열기
ServerSocket과 Runnable Thread를 사용하여 여러 웹 브라우저가 접속하는 HTTP WEBServer를 구현했다.

<img width="70%" src="https://blog.kakaocdn.net/dn/c8OuM5/btsfjIJ4tyZ/X1YJLKxgbo7SFpHLLXlJL1/img.gif">

[동작 로직 설명](https://coding-business.tistory.com/125#serversocket%EC%9C%BC%EB%A1%9C-httpserver-%EC%97%B4%EA%B8%B0)

서버를 연 이후 [HTTP Reqeust Message를 분석](https://coding-business.tistory.com/125#client-request-message-%EB%B6%84%EC%84%9D%ED%95%98%EA%B8%B0)하고


[Response Message 형식에 맞춰 메세지 전달](https://coding-business.tistory.com/125#response-message-%EA%B5%AC%EC%A1%B0%EB%8C%80%EB%A1%9C-%EB%B0%98%ED%99%98%ED%95%98%EA%B8%B0) 하여 웹브라우저와 통신 가능한 서버를 구현했다.
# GET /time -> 현재 시간을 json 에 담아서 알려줌
요청이 GET/time일 경우 현재 시간 message body json 형식으로 제공한다.

<img width="70%" src="https://blog.kakaocdn.net/dn/bTtT3i/btsfcMNMbKC/dK8Z6IueKUrsqxtvMtNraK/img.gif">

[동작 로직 설명](https://coding-business.tistory.com/125#1.-get-/time--%3E-%ED%98%84%EC%9E%AC-%EC%8B%9C%EA%B0%84%EC%9D%84-json-%EC%97%90-%EB%8B%B4%EC%95%84%EC%84%9C-%EC%95%8C%EB%A0%A4%EC%A4%8C)

# POST /text/{textid} -> Body로 전달된 문자열을 서버가 저장
요청이 POST /text/{textid}일 경우 path parameter를 분석하여 해당 아이디에 맞는 body 데이터를 서버에 저장한다

<img width="70%" src="https://blog.kakaocdn.net/dn/bpzf8f/btsfaJKWsYv/v1ZMG6ecRs3uhSperIM6w0/img.gif">

[동작 로직 설명](https://coding-business.tistory.com/125#2.-post-/text/{textid}--%3E-body%EB%A1%9C-%EC%A0%84%EB%8B%AC%EB%90%9C-%EB%AC%B8%EC%9E%90%EC%97%B4%EC%9D%84-%EC%84%9C%EB%B2%84%EA%B0%80-%EC%A0%80%EC%9E%A5)

# GET /text/{textid} -> 저장된 문자열을 알려줌
요청이 GET /text/{textid}일 경우 path parameter를 분석하여 해당 아이디로 저장한 데이터를 Response로 제공한다.

<img width="70%" src="https://blog.kakaocdn.net/dn/VtVQz/btse93v9xsD/M3AvcCK1gkfWHyOpcqPZ61/img.gif">

[동작 로직 설명](https://coding-business.tistory.com/125#3.-get-/text/{textid}--%3E-%EC%A0%80%EC%9E%A5%EB%90%9C-%EB%AC%B8%EC%9E%90%EC%97%B4%EC%9D%84-%EC%95%8C%EB%A0%A4%EC%A4%8C)

# DELETE /text/{textid} -> 저장된 문자열을 삭제
요청이 DELETE /text/{textid}일 경우 path parameter를 분석하여 해당 아이디로 저장한 데이터를 삭제한다

<img width="70%" src="https://blog.kakaocdn.net/dn/FU6Zs/btsfehfp2vp/kNz1a9K5BWx3LrbuxXEnJ1/img.gif">

[동작 로직 설명](https://coding-business.tistory.com/125#4.-delete-/text/{textid}--%3E-%EC%A0%80%EC%9E%A5%EB%90%9C-%EB%AC%B8%EC%9E%90%EC%97%B4%EC%9D%84-%EC%82%AD%EC%A0%9C)

# GET /image -> jpeg 이미지를 다운로드
요청이 GET /image -> jpeg일 경우 path parameter를 분석하여 해당 아이디로 저장한 데이터를 삭제한다

<img width="70%" src="https://blog.kakaocdn.net/dn/QARC1/btsfe6Lu10L/BcBx1428cZk0RKX0y4BEw0/img.gif">

[동작 로직 설명](https://coding-business.tistory.com/125#5.-get-/image--%3E-jpeg-%EC%9D%B4%EB%AF%B8%EC%A7%80%EB%A5%BC-%EB%8B%A4%EC%9A%B4%EB%A1%9C%EB%93%9C)








