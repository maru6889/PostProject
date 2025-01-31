# 📝 게시판(PostProject)

## 📖 프로젝트 개요
- **이 프로젝트는 무엇인가요?**
  - 이 프로젝트는 회원가입 및 로그인 후, 자유게시판에서 게시글을 작성하거나 질문 게시판을 통해 궁금한 점을 물어볼 수 있는 커뮤니티 형태의 게시판입니다.
  - 댓글을 통해 사용자 간 의견을 공유할 수 있으며, 비회원도 게시글 및 댓글을 조회할 수 있습니다. 하지만 작성 및 삭제 권한은 회원에게만 부여됩니다.
- **왜 만들었나요?**
  - 단순한 게시판 기능 구현을 넘어, 실제 운영 환경에서의 **성능 테스트 및 개선**, **DB 백업 및 복원** 등의 **비기능적인 요소(NFR, Non-Functional Requirements)** 에 초점을 맞추기 위해 제작되었습니다.
- **어떤 문제를 해결했나요?**
  1. mybatis를 사용할 때 예를 들어 insert같은 로직 경우 여러 필드를 파라미터로 보냈을 때 mapper중 insert쿼리에서 keyproperty옵션이 id가 어떤것인지 인식을 못했음.
  -> 따로따로 받는것 보다는 객체로 파라미터를 전달해주는것이 좋다. 따로따로 필드를 받는 경우 keyproperty=“id” 같이 인식을 못할 수 있기 때문에 객체로 파라미터를 전달받는 방식이 안정적이다.
  2. post테이블의 created_at 컬럼이 null로 조회됨. 실제 테이블을 조회한 결과 데이터가 잘 들어가 있음.
  -> DB에서 조회한 datetime 타입이 LocalDateTime 타입의 필드에 제대로 매핑이 되지 않을 경우, mapper에 따로 resultmap을 추가로 만들어준다음 자바 객체와 매핑시켜줘서 해결해야 한다.
  3. 로컬에서는 게시글 작성이 잘 되었는데 ec2 에 배포후 게시글 작성을 해보니 작성이 안되는 에러가 발생 
  -> 데이터베이스의 문자셋 설정이 로컬 환경과 ec2 서버 환경 간에 다르기 때문.  실제로 확인해보니 로컬은 utf8mb4 이고 ec2는 latin1으로 설정되어있음.


## 🚀 주요 기능
### 📝 게시판 기능
- ✅ **게시글 CRUD** (작성, 수정, 삭제, 조회)
- ✅ **카테고리 분류 및 필터링**
- ✅ **검색 기능** (게시글 제목, 내용 검색, 사용자의 닉네임 검색, 게시글의 댓글 검색)

### 🔑 사용자 인증 및 권한
- ✅ **회원가입 및 로그인** (Spring Security 기반 세션 방식)
- ✅ **비회원 제한** (비회원은 조회만 가능, 작성 및 삭제는 불가)

### 💬 커뮤니케이션
- ✅ **댓글 기능** (작성, 수정, 삭제)

## ⚙ 기술 스택
- **Backend:** Java, Spring Boot, MyBatis
- **Frontend:** Thymeleaf, JavaScript
- **Database:** MariaDB
- **Infra:** AWS EC2

## 🎯 ERD (데이터베이스 구조)
![](/Users/exia/Desktop/post_project_erd.png)

## 🔧 프로젝트 설정 및 실행 방법
### 1️⃣ **환경 설정**
- JDK 17+
- MariaDB 10.5+
- Gradle 8.11+
- **설정 파일 (`application.yml`) 추가 필요**

### 2️⃣ **설치 방법**
```bash
# 프로젝트 클론
git clone https://github.com/your-repo/PostProject.git
cd PostProject

# 데이터베이스 설정 (MariaDB 실행 및 초기화)
mysql -u root -p < src/main/resources/schema.sql

# 프로젝트 실행
./gradlew build && ./gradlew bootRun