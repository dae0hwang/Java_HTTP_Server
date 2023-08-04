## 프로젝트 목표
- Request를 직접 분석하고 비즈니스 로직을 실행하여 적절한 Response 문자열을 제공하는 코드를 작성함으로써   
HTTP 프로토콜의 구조에 대한 이해를 넓힙니다.
- RESTful API 개념을 익힌 후 Json 형식으로 데이터를 제공합니다.

## 프로젝트 동작
### 1. GET /time -> 현재 시간을 json 에 담아서 제공
요청이 GET/time일 경우 현재 시간 message body json 형식으로 제공합니다. [동작 로직 설명](https://coding-business.tistory.com/125#1.-get-/time--%3E-%ED%98%84%EC%9E%AC-%EC%8B%9C%EA%B0%84%EC%9D%84-json-%EC%97%90-%EB%8B%B4%EC%95%84%EC%84%9C-%EC%95%8C%EB%A0%A4%EC%A4%8C)

<img width="65%" src="https://blog.kakaocdn.net/dn/bTtT3i/btsfcMNMbKC/dK8Z6IueKUrsqxtvMtNraK/img.gif">

### 2. POST /text/{textid} -> Body로 전달된 문자열을 서버에 저장
요청이 POST /text/{textid}일 경우 path parameter를 분석하여 해당 아이디에 맞는 body 데이터를 서버에 저장합니다. [동작 로직 설명](https://coding-business.tistory.com/125#2.-post-/text/{textid}--%3E-body%EB%A1%9C-%EC%A0%84%EB%8B%AC%EB%90%9C-%EB%AC%B8%EC%9E%90%EC%97%B4%EC%9D%84-%EC%84%9C%EB%B2%84%EA%B0%80-%EC%A0%80%EC%9E%A5)

<img width="65%" src="https://blog.kakaocdn.net/dn/bpzf8f/btsfaJKWsYv/v1ZMG6ecRs3uhSperIM6w0/img.gif">


### 3. GET /text/{textid} -> id에 해당하는 문자열 제공
요청이 GET /text/{textid}일 경우 path parameter를 분석하여 해당 아이디로 저장한 데이터를 제공합니다. [동작 로직 설명](https://coding-business.tistory.com/125#3.-get-/text/{textid}--%3E-%EC%A0%80%EC%9E%A5%EB%90%9C-%EB%AC%B8%EC%9E%90%EC%97%B4%EC%9D%84-%EC%95%8C%EB%A0%A4%EC%A4%8C)

<img width="65%" src="https://blog.kakaocdn.net/dn/VtVQz/btse93v9xsD/M3AvcCK1gkfWHyOpcqPZ61/img.gif">

### 4. DELETE /text/{textid} -> 저장된 문자열을 삭제
요청이 DELETE /text/{textid}일 경우 path parameter를 분석하여 해당 아이디로 저장한 데이터를 삭제합니다. [동작 로직 설명](https://coding-business.tistory.com/125#4.-delete-/text/{textid}--%3E-%EC%A0%80%EC%9E%A5%EB%90%9C-%EB%AC%B8%EC%9E%90%EC%97%B4%EC%9D%84-%EC%82%AD%EC%A0%9C)

<img width="65%" src="https://blog.kakaocdn.net/dn/FU6Zs/btsfehfp2vp/kNz1a9K5BWx3LrbuxXEnJ1/img.gif">

### 5. GET /image -> jpeg 이미지를 다운로드
요청이 GET /image -> jpeg일 경우 서버가 가지고 있는 이미지를 전달합니다. [동작 로직 설명](https://coding-business.tistory.com/125#5.-get-/image--%3E-jpeg-%EC%9D%B4%EB%AF%B8%EC%A7%80%EB%A5%BC-%EB%8B%A4%EC%9A%B4%EB%A1%9C%EB%93%9C)

<img width="65%" src="https://blog.kakaocdn.net/dn/QARC1/btsfe6Lu10L/BcBx1428cZk0RKX0y4BEw0/img.gif">
