# BF 백엔드 데이터베이스 스키마 설계 (Database Schema)

업데이트 관리 및 매장별 상태 관리를 위한 DB 테이블 기본 설계입니다.

## 1. 매장 정보 테이블 (Stores)
* id (PK)
* store_code (Unique, String): 매장 코드
* store_name (String)
* pos_count (Integer): 등록된 POS 대수
* created_at (DateTime)

## 2. 버전 패키지 테이블 (Versions)
* id (PK)
* version_name (Unique, String): 버전명 (예: v1.0.0)
* file_path (String): 패키지 파일 위치
* hash_key (String): 파일 검증용 해시
* release_notes (Text): 배포 변경 사항
* is_canary (Boolean): 테스트 배포용 여부
* created_at (DateTime)

## 3. 배포 규칙 테이블 (Deployment_Rules)
* id (PK)
* rule_name (String): 규칙명
* target_version_id (FK): 대상 버전
* rollout_percentage (Integer): 배포 비중 (점진 배포용 0 ~ 100)
* update_type (String): "SILENT" 또는 "FORCED"

## 4. 버전 핀닝 테이블 (Version_Pinning)
* id (PK)
* store_code (FK): 매장 코드
* pos_number (String): 특정 포스 번호
* fixed_version_id (FK): 고정할 버전
* reason (String): 고정 사유

## 5. 업데이트 이력 및 로그 테이블 (Sync_Logs)
* id (PK)
* store_code (FK)
* pos_no (String)
* start_version (String)
* end_version (String)
* status (String): 최종 결과 (SUCCESS, FAILED)
* last_sync_at (DateTime)
