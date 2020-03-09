# URL Shortening Service

## Descriptions
URL을 입력받아 짧게 줄여주고, Shortening된 URL을 입력하면 원래 URL로 리다이렉트하는 URL Shortening Service

예) https://en.wikipedia.org/wiki/URL_shortening => http://localhost/JZfOQNro

* URL 입력폼 제공 및 결과 출력
  - RESTful api 와 client(jQuery) 구조 구현
    - 변환 결과 및 오류 메시지 출력 
  - 입력폼에서 json 형식으로 전달된 값은 `Spring Validation` 으로 유효성 검증
  
* URL Shortening Key는 8 Character 이내로 생성되어야 합니다.
  - Base52(0-9A-Za-z) encoding
    - BaseN encoder 구현
  - 최소 3자리 ~ 최대 8자리 Shortening Key 생성
  - 생성된 Shortening Key의 길이가 8자리를 초과 할 경우 `OutOfMaxLengthException` 발생
  
* 동일한 URL에 대한 요청은 동일한 Shortening Key로 응답해야 합니다.
  - Shortening Key 생성 시 원본 url 의 md5 hash 값 저장
  - table 에서 hash 값을 lookup 하여 동일 url 이 있을 경우, 저장된 Shortening Key 를 이용하여 Shortened URL 응답
  - Entity -> DTO 변환에 `ModelMapper` 사용
  
* Shortening된 URL을 요청받으면 원래 URL로 리다이렉트 합니다.
  - Shortened URL Redirect 처리: http://localhost:8080/[Shortening Key]
  - 요청된 Shortening Key 에 대한 원본 URL 이 존재 하지 않을 경우 `ShortUrlNotFoundException` 발생 
    - `ShortUrlNotFoundException` 발생 시 custom error page 출력 (bitly.com 참조)
  
* Shortening된 URL 요청 수 정보를 가져야 한다(화면 제공 필수 아님)
  - Shortened URL Redirect 처리 시 요청 수 증가 처리
   
* Database 사용은 필수 아님
  - 동일한 URL 처리를 위해 MySQL Database 사용

### TODO
* 실행 중인 service name 감지 및 적용
  - 현재는 http://localhost:8080 으로 고정
  
* MySQL native query 제거
  - Shortening Key 조회 수 증가 기능에 native query 를 사용함
  
* BaseN decoding 기능 개발
  - 현재 encoding 기능만 개발 되어 있음

* DB migration tool 적용
  - 현재 JPA의 DDL generation 적용
  
* Error Handling 고도화

---

## Dependencies
### Frontend Dependencies
| Dependence            | Version       | 
| :---                  | :---:         |
| jquery                | 3.2.1         |
| Clipboard2            | 2.0.6         |

### Backend Dependencies
| Dependence            | Version       | 
| :---                  | :---:         |
| Java                  | 1.8           |
| Spring Boot           | 2.2.5         |
| ModelMapper           | 2.3.6         |
| Apache Commons Lang   | 3.9           |
| JUnit                 | JUnit 5       |
| MySQL                 | 5.7.26        |

---

## Prepare Environment
### Java
* java 설치 여부 및 버전 확인
  ```console
  ]$ java -version
  openjdk version "1.8.0_201"
  OpenJDK Runtime Environment (build 1.8.0_201-b09)
  OpenJDK 64-Bit Server VM (build 25.201-b09, mixed mode)
  ```
* java 1.8 설치
  ```console
  ]$ sudo yum install java-1.8.0-openjdk-devel
  ```
  
### Gradle
* gradle 설치 여부 및 버전 확인
  ```console
  ]$ $ gradle -v
  ------------------------------------------------------------
  Gradle 6.2.2
  ------------------------------------------------------------
  ```
* gradle 설치
  ```console
  ]$ wget https://services.gradle.org/distributions/gradle-6.2.2-bin.zip -P /tmp
  ]$ mkdir /opt/gradle
  ]$ unzip -d /opt/gradle /tmp/gradle-6.2.2-bin.zip
  ]$ ls /opt/gradle/gradle-6.2.2
  bin  init.d  lib  LICENSE  NOTICE  README
  
  ]$ sudo vi /etc/profile.d/gradle.sh
  export GRADLE_HOME=/opt/gradle/gradle-6.2.2
  export PATH=${GRADLE_HOME}/bin:${PATH}
  
  ]$ sudo chmod +x /etc/profile.d/gradle.sh
  ]$ source /etc/profile.d/gradle.sh
  ]$ gradle -v
  Welcome to Gradle 6.2.2!
  ...
  ...
  ------------------------------------------------------------
  Gradle 6.2.2
  ------------------------------------------------------------
  ```
  
### MySQL
* MySQL 설치 여부 및 버전 확인
  ```console
  ]$ mysqld --version
  mysqld  Ver 5.7.26 for Linux on x86_64 (MySQL Community Server (GPL))
  ```

* MySQL 설치
  - [MySQL 설치 방법](https://www.notion.so/razy/MySQL-2b11f14db9274c26a16088e5fc60bfe3)

---

## Project Build & Run
### checkout
  ```console
  ]$ git clone https://github.com/razy-dev/url_shortener.git
  ```

### configuration
  ```consolr
  ]$ cd PROJECT_HOME/src/main/resources
  ]$ vi jdbc.properties
  ```
  ```properties
  # jpa.hibernate
  spring.jpa.hibernate.ddl-auto=update
  
  # MySQL
  spring.datasource.url=jdbc:mysql://[MYSQL_HOST]:[MYSQL_PORT]/[DATABASE_NAME]?characterEncoding=UTF-8&serverTimezone=UTC
  spring.datasource.username=[DATABASE_USER]
  spring.datasource.password=[DATABASE_PASSWORD]
  ```

## Build & Run
* build & test
  ```console
  ]$ cd PROJECT_HOME
  ]$ gradle build 
  ```
  
* build without test
  ```console
  ]$ cd PROJECT_HOME
  ]$ gradle build -x test 
  ```
  
* run project
  ```console
  ]$ gradle run
  ```

* Service URL : http://localhost:8080