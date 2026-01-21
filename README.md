# 상품 관리 시스템 (Product Management System)

## 프로젝트 개요

이 프로젝트는 Java로 구현된 클라이언트-서버 기반의 상품 관리 시스템입니다. 소켓 통신을 통해 클라이언트와 서버가 통신하며, MySQL 데이터베이스를 사용하여 상품 정보를 관리합니다.

## 주요 기능

- **상품 등록**: 새로운 상품을 데이터베이스에 등록 (`post 상품명 가격 수량`)
- **상품 목록 조회**: 등록된 모든 상품 목록 조회 (`get`)
- **상품 상세 조회**: 특정 상품의 상세 정보 조회 (`get 상품ID`)
- **상품 삭제**: 등록된 상품 삭제 (`delete 상품ID`)

## 아키텍처

### 클라이언트-서버 구조
- **MyClient**: 클라이언트 애플리케이션 (포트 20000으로 서버에 연결)
- **MyServer**: 서버 애플리케이션 (포트 20000에서 클라이언트 요청 대기)

### 계층 구조
- **DTO 계층**: `RequestDTO`, `ResponseDTO` - 클라이언트와 서버 간 데이터 전송 객체
- **서비스 계층**: `ProductService` - 상품 관련 비즈니스 로직 처리
- **레포지토리 계층**: `ProductRepository` - 데이터베이스 접근 및 CRUD 작업
- **엔티티**: `Product` - 상품 도메인 모델

## 기술 스택

- **언어**: Java
- **빌드 도구**: Gradle
- **데이터베이스**: MySQL (productdb)
- **라이브러리**:
  - MySQL Connector/J (데이터베이스 연결)
  - Gson (JSON 처리)
  - Lombok (보일러플레이트 코드 감소)

## 프로젝트 구조

```
src/main/java/
├── client/
│   └── MyClient.java          # 클라이언트 애플리케이션
├── server/
│   ├── MyServer.java          # 서버 애플리케이션
│   ├── Product.java           # 상품 엔티티
│   ├── ProductService.java    # 상품 서비스 구현
│   ├── ProductServiceInterface.java  # 상품 서비스 인터페이스
│   ├── ProductRepository.java # 데이터베이스 접근 계층
│   └── DBConnection.java      # 데이터베이스 연결 관리
└── dto/
    ├── RequestDTO.java        # 요청 데이터 전송 객체
    └── ResponseDTO.java       # 응답 데이터 전송 객체
```

## 사용 방법

1. **서버 실행**: `MyServer` 클래스를 실행하여 서버를 시작합니다.
2. **클라이언트 실행**: `MyClient` 클래스를 실행하여 서버에 연결합니다.
3. **명령어 입력**: 클라이언트 콘솔에서 다음 명령어를 입력합니다:
   - `post 바나나 5000 20` - 상품 등록
   - `get` - 전체 상품 목록 조회
   - `get 1` - ID가 1인 상품 상세 조회
   - `delete 1` - ID가 1인 상품 삭제

## 데이터베이스 설정

- **데이터베이스명**: productdb
- **포트**: 3306
- **연결 정보**: `DBConnection.java` 파일에서 설정 가능

---

# 실습 평가 링크

https://getinthere.notion.site/3-2eb8a08b6c0d8039baccea3268e606dc?source=copy_link