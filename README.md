## 프로젝트 초기 설계

본 프로젝트는 ERD 및 세부 기능 정의 이전 단계에서  
**백엔드 구조의 일관성과 확장성 확보**를 목표로 초기 설계를 진행.

* 주의사항

1. 카카오 소셜 로그인의 경우 카카오 계정의 비밀번호는 당연히 카카오 인증 서버에서 해당 사용자의 PWD 줄 수 없으니 /global/oauth/service/OauthUserService 내 "kakao_oauth_placeholder" 문자열로 비밀번호 하드코딩하여 인코딩해서 DB에 넣었음.
2. 배포가 안되있어 로컬 DB로 application.yml 설정하였으니 각자 'givy' 이름의 DB 생성
3. .env 파일에 ${DB_USER}, ${DB_PWD}, ${JWT_SECRET}, ${JWT_EXPIRATION_MS} 설정 후 터미널에서 실행
4. ./gradlew clean build 시 test 코드 생성 안해서 실패할 것임. -> ./gradlew clean build -x test -> ./gradlew bootRun으로 실행
5. application.yml 내 oauth.kakao에 관련하여 client-secret은 절대 유출 x (카카오 개발자 사이트에서 카카오 소셜 로그인 RestAPI 문서 보시면 됩니다.)
6. 제가 사용한 JWT_SECRET, CLIENT_SECRET은 보내드리겠습니다.

---

### 1. Global 디렉토리 구조 설계 (ERD 이전 단계)

- ERD 설계 이전이라도 도메인 간 책임을 명확히 하기 위해  
  전역(Global) 디렉토리 구조를 먼저 정의
- 공통 설정, 공통 응답, 공통 예외 처리 등은 도메인과 분리하여 관리

### 2. User 도메인 중심 설계

- 인증, 인가, 소셜 로그인 등 대부분의 기능이 User 도메인에 의존하므로  
  User 도메인을 기준으로 프로젝트 구조를 설계
- 추후 다른 도메인 추가 시 동일한 패턴으로 확장 가능하도록 구성

### 3. DDD(Domain-Driven Design) 기반 프로젝트 폴더 설계

- 도메인 단위로 패키지를 분리하여 관심사 분리
- 각 도메인은 다음과 같은 계층 구조를 기본으로 가진다.
  - code
  - controller
  - converter
  - dto
  - entity
  - enums
  - exception
  - repository
  - service
### 4. API 응답 구조 통일

- 클라이언트와의 명확한 통신을 위해 API 응답 형식을 통일
- 성공 / 실패 여부, 메시지, 데이터 구조를 일관되게 유지
- 공통 ApiResponse 객체를 통해 중복 코드 최소화

### 5. 소셜 로그인 뼈대 설계

- OAuth 기반 소셜 로그인 확장을 고려하여 구조만 우선 정의
- 실제 Provider 연동 이전에 인터페이스 및 흐름 중심으로 설계
- 추후 Google, Kakao, Naver 등 확장 가능하도록 설계

---

## Git Commit 규칙

본 프로젝트는 커밋 히스토리의 가독성과 협업 효율을 위해  
다음과 같은 커밋 메시지 규칙을 따른다.

### 커밋 타입

- `init` : 프로젝트 초기 설정
- `feat` : 새로운 기능 추가
- `fix` : 버그 수정
- `refactor` : 리팩토링 (기능 변경 없음)
- `docs` : 문서 수정 (README 등)
- `chore` : 빌드 설정, 패키지 관리 등 기타 작업
- `test` : 테스트 코드 추가 및 수정

### 커밋 메시지 형식

#### 예시
- init: add README
- feat: implement user signup API
- fix: resolve login validation bug
- refactor: reorganize user domain packages
- docs: update project initial design


- 한 커밋에는 **하나의 목적만 포함**
- 불필요하게 긴 커밋 메시지는 지양
- 작업 의도가 드러나도록 명확하게 작성

---

