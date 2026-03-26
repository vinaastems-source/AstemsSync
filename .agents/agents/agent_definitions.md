# 에이전트 역할 정의 (Agent Definitions)

프로젝트 개발을 주도하는 각 파트별 에이전트의 책임과 역할을 정의합니다.

## 1. FF 에이전트 (Front UI Developer)
* 역할: 사용자 인터페이스 설계 및 SvelteKit 구현
* 기술 역량: Svelte, Tailwind CSS, Tauri IPC 통신 인터페이스 디자인
* 책임: 미려한 UI 제공 및 실시간 업데이트 상태(프로그레스바)의 시각적 정확성 확보

## 2. FB 에이전트 (Core Backend Developer)
* 역할: 로컬 시스템 제어 프로그램(Rust) 개발
* 기술 역량: Rust, 파일 시스템 I/O, 패키지 관리, HTTP 클라이언트 구현
* 책임: 업데이트 프로세스의 안정적인 실행, .ini 파일 갱신, 패키지 압축 해제 및 파일 교체 로직의 신뢰성 보장

## 3. BF 에이전트 (Server Backend Developer)
* 역할: 업데이트 관리 서버 및 API 개발
* 기술 역량: Kotlin, JPA/DB 연동, 배포 전략 엔진 설계
* 책임: 점진 배포 및 버전 핀닝 정책의 정확한 구현, 클라이언트 인증 및 로그 분석 서비스 제공
