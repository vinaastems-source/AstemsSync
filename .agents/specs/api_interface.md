# BF 서버 API 인터페이스 정의 (API Specification)

FB(Rust)와 BF(Kotlin) 간의 통신을 위한 API 초안입니다.

## 1. 인증 및 업데이트 체크 (POST /api/v1/sync/check)
* 요청본문 (Request Body):
    * userId (String): 사용자 ID
    * password (String): 암호
    * storeCode (String): 매장 코드
    * posNo (String): POS 번호
    * currentVersion (String): 현재 로컬 설치 버전 (from Astems.ini)
* 응답본문 (Response Body):
    * updateType (Enum): "SILENT", "FORCED", "NONE"
    * targetVersion (String): 배포 대상 버전
    * downloadUrl (String): 패키지 다운로드 주소
    * packageHash (String): 파일 무결성 검증용 해시값
    * isPinned (Boolean): 특정 버전 고정 여부
    * message (String): 공지사항 또는 정책 관련 메시지

## 2. 업데이트 상태 보고 (POST /api/v1/sync/status)
* 요청본문 (Request Body):
    * storeCode (String)
    * posNo (String)
    * status (Enum): "DOWNLOADING", "EXTRACTING", "COMPLETED", "FAILED"
    * progress (Integer): 0 ~ 100 사이의 진행률
    * errorMessage (String): 실패 시 오류 내용 기술

## 3. 기술적 요구사항
* 통신 방식: HTTPS 권장
* 타임아웃: 네트워크 장애 대응을 위해 충분한 타임아웃 및 재시도 로직 적용 (Exponential Backoff)
* 인증 방식: JWT 또는 API Key 기반의 추가 보안 계층 고려 가능
