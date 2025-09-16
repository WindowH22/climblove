# 커밋 컨벤션 가이드

## 📋 커밋 타입

| 타입 | 설명 | 예시 |
|------|------|------|
| `feat` | 새로운 기능 추가 | `feat: 사용자 로그인 기능 구현` |
| `fix` | 버그 수정 | `fix: 로그인 시 세션 만료 문제 해결` |
| `docs` | 문서 수정 | `docs: README 업데이트` |
| `style` | 코드 포맷팅, 세미콜론 누락 등 | `style: 코드 포맷팅 적용` |
| `refactor` | 코드 리팩토링 | `refactor: 사용자 서비스 로직 개선` |
| `test` | 테스트 코드 추가/수정 | `test: 사용자 서비스 단위 테스트 추가` |
| `chore` | 빌드 업무, 패키지 매니저 등 | `chore: Gradle 의존성 업데이트` |
| `perf` | 성능 개선 | `perf: 데이터베이스 쿼리 최적화` |
| `ci` | CI/CD 설정 변경 | `ci: GitHub Actions 워크플로우 추가` |
| `build` | 빌드 시스템 변경 | `build: Gradle 설정 파일 수정` |

## 📝 커밋 메시지 구조

```
<타입>(<범위>): <제목>

<본문>

<푸터>
```

### 제목 규칙
- 50자 이내로 작성
- 첫 글자는 소문자로 시작
- 마침표로 끝내지 않음
- 명령형으로 작성 (과거형 X)

### 범위 (선택사항)
- 변경사항이 영향을 미치는 부분을 명시
- 예: `feat(auth):`, `fix(user):`, `refactor(crew):`

### 본문 (선택사항)
- 변경사항의 상세한 설명
- 왜 변경했는지에 대한 이유
- 각 줄은 72자 이내

### 푸터 (선택사항)
- Breaking Changes가 있는 경우 `BREAKING CHANGE:`로 시작
- 이슈 번호가 있는 경우 `Closes #123` 형태로 작성

## ✅ 좋은 커밋 메시지 예시

```
feat(user): 사용자 프로필 수정 기능 구현

- 사용자 이름, 이메일, 프로필 이미지 수정 가능
- 프로필 변경 시 이메일 인증 요구
- 프로필 변경 히스토리 저장

Closes #45
```

```
fix(auth): 로그인 시 세션 만료 문제 해결

- 세션 타임아웃 설정을 30분에서 2시간으로 연장
- 리프레시 토큰 갱신 로직 개선
- 세션 만료 시 자동 로그아웃 처리

Resolves #67
```

```
refactor(crew): 크루 생성 로직 개선

- 크루 생성 시 중복 검사 로직 강화
- 크루 멤버 초대 프로세스 단순화
- 예외 처리 개선

BREAKING CHANGE: CrewService.createCrew() 메서드 시그니처 변경
```

## ❌ 피해야 할 커밋 메시지

- `수정함`, `버그 수정`, `업데이트` (너무 모호함)
- `WIP`, `작업 중` (불완전한 상태)
- `테스트` (테스트용 커밋)
- `임시`, `temp` (임시 커밋)

## 🔧 Git Hooks 설정 (권장)

프로젝트에 커밋 메시지 검증을 위한 Git Hooks를 설정하는 것을 권장합니다:

```bash
# commitlint 설치
npm install -g @commitlint/cli @commitlint/config-conventional

# .commitlintrc.js 파일 생성
echo "module.exports = {extends: ['@commitlint/config-conventional']};" > .commitlintrc.js
```

## 📚 참고 자료

- [Conventional Commits](https://www.conventionalcommits.org/)
- [Angular Commit Message Guidelines](https://github.com/angular/angular/blob/main/CONTRIBUTING.md#-commit-message-format)


