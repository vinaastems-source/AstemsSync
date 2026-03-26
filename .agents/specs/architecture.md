# AstemsSync 시스템 아키텍처 정의 (Architecture Specification)

본 문서는 POS 프로그램 업데이트 시스템인 AstemsSync의 전체 구조와 기술 스택을 정의합니다.

## 1. 개요
* 프로젝트명: AstemsSync
* 목적: POS 프로그램 및 데이터의 안정적인 배포 및 상태 관리
* 주요 모델: FF(Front Front), FB(Front Backend), BF(Backend Front)

## 2. 계층별 기술 스택 및 역할

### 2.1 FF (Front Front / UI 영역)
* 기술: SvelteKit + Tailwind CSS (Tauri Wrapped)
* 역할: 사용자 UI/UX 제공, 로그인 위젯, 업데이트 진행률(Progress Bar) 가시화
* 특징: 웹 기술을 활용한 미려한 디자인 구현 및 Tauri를 통한 데스크톱 통합

### 2.2 FB (Front Backend / 로컬 서비스 영역)
* 기술: Rust
* 역할:
    * FF와의 IPC 통신 처리
    * 로컬 시스템 제어(Astems.ini 읽기/쓰기)
    * BF 서버와의 데이터 통신 및 파일 다운로드 관리
    * 파일 무결성 검증, 압축 해제 및 배포 로직 수행
* 특징: Rust의 안전성과 고성능을 활용하여 업데이트 중 발생할 수 있는 시스템 오류 최소화

### 2.3 BF (Backend Front / 서버 통신 영역)
* 기술: Kotlin (Spring Boot 기반 권장) + DB 연동
* 역할:
    * 클라이언트(FB) 인증 및 자격 증명 관리
    * 버전 체크 로직(점진 배포, Canary, 버전 핀닝 정책 적용)
    * 업데이트 패키지(다운로드 URL) 제공
    * 업데이트 상태 모니터링 및 이력 관리
* 특징: Kotlin의 생산성과 안정성을 활용하여 복잡한 배포 전략 관리

## 3. 통신 구조
* FF <-> FB: Tauri IPC (Events/Commands) 전역 상태 동기화
* FB <-> BF: HTTP/HTTPS (REST API 기반 JSON 통신)
* 실시간 상태 제어: FB가 다운로드 정보를 획득하여 FF에 전송하여 프로그레스바 갱신

## 4. 로컬 연동 정보 (Astems.ini)
* 매장코드 (Store Code)
* 포스번호 (POS Number)
* 현재 로컬 POS 버전 (Current Version)
* 기타 구동 환경 설정값

## 5. 표준 폴더 구조 (Agent Logic)
AI 모델의 효율적인 인식을 위해 본 프로젝트는 다음과 같은 폴더 구조를 준수합니다.
* `.agents/agents/`: 에이전트 역할 정의
* `.agents/rules/`: 개발 및 운영 규칙
* `.agents/skills/`: 핵심 기술 역량
* `.agents/specs/`: 시스템 및 API 명세
* `.agents/workflows/`: 작업 흐름 및 프로세스
