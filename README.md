# URL Shortening Service

URL을 입력받아 짧게 줄여주고, Shortening된 URL을 입력하면 원래 URL로 리다이렉트하는 URL Shortening Service

예) https://en.wikipedia.org/wiki/URL_shortening => http://localhost/JZfOQNro

* URL 입력폼 제공 및 결과 출력
  - HTML5 + Bootstrap template 로 구현
  - jQuery를 이용하여 RESTful client 구현
    - 변환 결과 및 오류 메시지 출력 
  - 입력폼에서 json 형식으로 전달된 값은 Spring Validation 으로 유효성 검증
  
* URL Shortening Key는 8 Character 이내로 생성되어야 합니다.
  - Base52(0-9A-Za-z) encoding
  - 최소 3자리 ~ 최대 8자리 Shortening Key 생성
  - 생성된 Shortening Key의 길이가 8자리를 초과 할 경우 `OutOfMaxLengthException` 발생
  
* 동일한 URL에 대한 요청은 동일한 Shortening Key로 응답해야 합니다.
  - Shortening Key 생성 시 원본 url 의 md5 hash 값 저장
  - tabled에서 hash 값을 lookup 하여 동일 url 이 있을 경우, 저장된 Shortening Key 를 이용하여 Shortened URL 응답
  - Entity -> DTO 변환에 ModelMapper 사용
  
* Shortening된 URL을 요청받으면 원래 URL로 리다이렉트 합니다.
  - Shortened URL Redirect 처리: http://localhost:8080/[Shortening Key]
  - 요청된 Shortening Key 에 대한 원본 URL 이 존재 하지 않을 경우 `ShortUrlNotFoundException` 발생 
    - `ShortUrlNotFoundException` 발생 시 custom error page 출력 (bitly.com 참조)
  
* Shortening된 URL 요청 수 정보를 가져야 한다(화면 제공 필수 아님)
  - Shortened URL Redirect 처리 시 요청 수 증가 처리
   
* Database 사용은 필수 아님
  - 동일한 URL 처리를 위해 MySQL Database 사용

## Dependencies
### Frontend Dependencies
| Dependence            | Version       | 
| :---                  | :---:         |
| jquery                | 3.2.1         |

### Backend Dependencies
| Dependence            | Version       | 
| :---                  | :---:         |
| Java                  | 1.8           |
| Spring Boot           | 2.2.5         |
| ModelMapper           | 2.3.6         |
| Apache Commons Lang   | 3.9           |
| JUnit                 | JUnit 5       |
| MySQL                 | 5.7.26        |

## SetUp
### checkout

### configuration

## Build & Run

