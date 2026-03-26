<script lang="ts">
  export let storeInfo: {
    storeName: string
    storeCode: string
    posNo: string
    currentVersion: string
  }

  let userId = ''
  let password = ''
  let isLoading = false
  let errorMessage = ''
  let passwordVisible = false

  async function handleLogin() {
    if (!userId.trim()) {
      errorMessage = '사용자 ID를 입력해 주세요.'
      return
    }
    if (!password.trim()) {
      errorMessage = '비밀번호를 입력해 주세요.'
      return
    }

    isLoading = true
    errorMessage = ''

    // 실제 환경에서는 Tauri invoke('login', { userId, password }) 호출
    await new Promise((r) => setTimeout(r, 1800))

    // 임시: 로그인 실패 시뮬레이션
    isLoading = false
    errorMessage = '서버에 연결할 수 없습니다. BF 서버 상태를 확인해 주세요.'
  }

  function handleKeydown(e: KeyboardEvent) {
    if (e.key === 'Enter') handleLogin()
  }
</script>

<div class="login-bg">
  <!-- 배경 장식 원형 광원 -->
  <div class="glow-orb glow-orb-1"></div>
  <div class="glow-orb glow-orb-2"></div>

  <div class="login-card">
    <!-- 상단 로고 및 앱명 -->
    <div class="brand-header">
      <div class="brand-icon">
        <svg width="28" height="28" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
          <path d="M12 2L2 7L12 12L22 7L12 2Z" stroke="white" stroke-width="2" stroke-linejoin="round"/>
          <path d="M2 17L12 22L22 17" stroke="white" stroke-width="2" stroke-linejoin="round"/>
          <path d="M2 12L12 17L22 12" stroke="white" stroke-width="2" stroke-linejoin="round"/>
        </svg>
      </div>
      <span class="brand-name">AstemsSync</span>
    </div>

    <!-- 매장 정보 배지 -->
    <div class="store-badge">
      <div class="store-badge-icon">
        <svg width="14" height="14" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
          <path d="M3 9L12 2L21 9V21H15V15H9V21H3V9Z" stroke="currentColor" stroke-width="2" stroke-linejoin="round"/>
        </svg>
      </div>
      <div class="store-badge-info">
        <span class="store-name">{storeInfo.storeName}</span>
        <span class="store-meta">코드: {storeInfo.storeCode} &nbsp;|&nbsp; POS: {storeInfo.posNo}번 &nbsp;|&nbsp; v{storeInfo.currentVersion}</span>
      </div>
    </div>

    <!-- 타이틀 -->
    <div class="login-title-block">
      <h1 class="login-title">로그인</h1>
      <p class="login-subtitle">업데이트 관리를 위해 계정 정보를 입력해 주세요.</p>
    </div>

    <!-- 입력 폼 -->
    <div class="form-group">
      <label for="userId" class="form-label">사용자 ID</label>
      <div class="input-wrapper">
        <span class="input-icon">
          <svg width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M20 21V19C20 17.9391 19.5786 16.9217 18.8284 16.1716C18.0783 15.4214 17.0609 15 16 15H8C6.93913 15 5.92172 15.4214 5.17157 16.1716C4.42143 16.9217 4 17.9391 4 19V21" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            <circle cx="12" cy="7" r="4" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </span>
        <input
          id="userId"
          type="text"
          class="form-input"
          placeholder="아이디를 입력하세요"
          bind:value={userId}
          on:keydown={handleKeydown}
          autocomplete="username"
          disabled={isLoading}
        />
      </div>
    </div>

    <div class="form-group">
      <label for="password" class="form-label">비밀번호</label>
      <div class="input-wrapper">
        <span class="input-icon">
          <svg width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <rect x="3" y="11" width="18" height="11" rx="2" ry="2" stroke="currentColor" stroke-width="2"/>
            <path d="M7 11V7C7 5.67392 7.52678 4.40215 8.46447 3.46447C9.40215 2.52678 10.6739 2 12 2C13.3261 2 14.5979 2.52678 15.5355 3.46447C16.4732 4.40215 17 5.67392 17 7V11" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </span>
        <input
          id="password"
          type={passwordVisible ? 'text' : 'password'}
          class="form-input"
          placeholder="비밀번호를 입력하세요"
          bind:value={password}
          on:keydown={handleKeydown}
          autocomplete="current-password"
          disabled={isLoading}
        />
        <button
          type="button"
          class="toggle-password"
          on:click={() => (passwordVisible = !passwordVisible)}
          tabindex="-1"
          aria-label="비밀번호 표시 전환"
        >
          {#if passwordVisible}
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              <path d="M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              <line x1="1" y1="1" x2="23" y2="23" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
            </svg>
          {:else}
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M1 12S5 4 12 4s11 8 11 8-4 8-11 8S1 12 1 12z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              <circle cx="12" cy="12" r="3" stroke="currentColor" stroke-width="2"/>
            </svg>
          {/if}
        </button>
      </div>
    </div>

    <!-- 오류 메시지 -->
    {#if errorMessage}
      <div class="error-banner" role="alert">
        <svg width="14" height="14" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
          <circle cx="12" cy="12" r="10" stroke="currentColor" stroke-width="2"/>
          <line x1="12" y1="8" x2="12" y2="12" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
          <line x1="12" y1="16" x2="12.01" y2="16" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
        </svg>
        <span>{errorMessage}</span>
      </div>
    {/if}

    <!-- 로그인 버튼 -->
    <button
      id="btn-login"
      type="button"
      class="btn-primary"
      on:click={handleLogin}
      disabled={isLoading}
    >
      {#if isLoading}
        <span class="spinner"></span>
        <span>인증 중...</span>
      {:else}
        <span>로그인</span>
      {/if}
    </button>

    <!-- 버전 정보 -->
    <p class="version-info">AstemsSync v{storeInfo.currentVersion}</p>
  </div>
</div>

<style>
  .login-bg {
    position: relative;
    width: 100vw;
    height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    background: var(--color-bg);
    overflow: hidden;
  }

  .glow-orb {
    position: absolute;
    border-radius: 50%;
    filter: blur(80px);
    pointer-events: none;
  }
  .glow-orb-1 {
    width: 450px;
    height: 450px;
    background: rgba(79, 110, 247, 0.18);
    top: -100px;
    right: -80px;
  }
  .glow-orb-2 {
    width: 350px;
    height: 350px;
    background: rgba(150, 90, 240, 0.12);
    bottom: -80px;
    left: -60px;
  }

  .login-card {
    position: relative;
    z-index: 10;
    width: 420px;
    background: var(--color-surface);
    border: 1px solid var(--color-border);
    border-radius: 20px;
    padding: 36px 40px 32px;
    box-shadow:
      0 4px 6px -1px rgba(0, 0, 0, 0.4),
      0 20px 60px -10px rgba(0, 0, 0, 0.6),
      inset 0 1px 0 rgba(255, 255, 255, 0.06);
  }

  .brand-header {
    display: flex;
    align-items: center;
    gap: 10px;
    margin-bottom: 24px;
  }
  .brand-icon {
    width: 40px;
    height: 40px;
    background: linear-gradient(135deg, var(--color-primary), #7c6bef);
    border-radius: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
    box-shadow: 0 4px 12px var(--color-primary-glow);
  }
  .brand-name {
    font-size: 1.1rem;
    font-weight: 700;
    color: var(--color-text);
    letter-spacing: 0.01em;
  }

  .store-badge {
    display: flex;
    align-items: center;
    gap: 10px;
    background: var(--color-surface-2);
    border: 1px solid var(--color-border);
    border-left: 3px solid var(--color-primary);
    border-radius: 10px;
    padding: 12px 14px;
    margin-bottom: 28px;
  }
  .store-badge-icon {
    color: var(--color-primary);
    flex-shrink: 0;
    line-height: 0;
  }
  .store-badge-info {
    display: flex;
    flex-direction: column;
    gap: 3px;
    min-width: 0;
  }
  .store-name {
    font-size: 0.95rem;
    font-weight: 600;
    color: var(--color-text);
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
  .store-meta {
    font-size: 0.72rem;
    color: var(--color-text-muted);
    letter-spacing: 0.01em;
  }

  .login-title-block {
    margin-bottom: 26px;
  }
  .login-title {
    font-size: 1.45rem;
    font-weight: 700;
    color: var(--color-text);
    margin-bottom: 4px;
  }
  .login-subtitle {
    font-size: 0.8rem;
    color: var(--color-text-muted);
    line-height: 1.5;
  }

  .form-group {
    margin-bottom: 16px;
  }
  .form-label {
    display: block;
    font-size: 0.8rem;
    font-weight: 500;
    color: var(--color-text-muted);
    margin-bottom: 7px;
    letter-spacing: 0.02em;
  }
  .input-wrapper {
    position: relative;
    display: flex;
    align-items: center;
  }
  .input-icon {
    position: absolute;
    left: 13px;
    color: var(--color-text-muted);
    line-height: 0;
    pointer-events: none;
  }
  .form-input {
    width: 100%;
    background: var(--color-surface-2);
    border: 1px solid var(--color-border);
    border-radius: 10px;
    padding: 11px 40px 11px 40px;
    font-size: 0.9rem;
    color: var(--color-text);
    font-family: inherit;
    outline: none;
    transition: border-color 0.2s, box-shadow 0.2s;
  }
  .form-input:focus {
    border-color: var(--color-primary);
    box-shadow: 0 0 0 3px var(--color-primary-glow);
  }
  .form-input:disabled {
    opacity: 0.5;
    cursor: not-allowed;
  }
  .form-input::placeholder {
    color: rgba(139, 148, 158, 0.5);
    font-size: 0.85rem;
  }
  .toggle-password {
    position: absolute;
    right: 12px;
    background: none;
    border: none;
    cursor: pointer;
    color: var(--color-text-muted);
    padding: 4px;
    line-height: 0;
    transition: color 0.2s;
  }
  .toggle-password:hover {
    color: var(--color-text);
  }

  .error-banner {
    display: flex;
    align-items: center;
    gap: 8px;
    background: rgba(248, 81, 73, 0.1);
    border: 1px solid rgba(248, 81, 73, 0.3);
    border-radius: 8px;
    padding: 10px 13px;
    margin-bottom: 16px;
    color: var(--color-error);
    font-size: 0.8rem;
    animation: shake 0.3s ease;
  }
  @keyframes shake {
    0%, 100% { transform: translateX(0); }
    25% { transform: translateX(-4px); }
    75% { transform: translateX(4px); }
  }

  .btn-primary {
    width: 100%;
    background: linear-gradient(135deg, var(--color-primary), #6a5af9);
    border: none;
    border-radius: 10px;
    padding: 13px;
    font-size: 0.95rem;
    font-weight: 600;
    color: white;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 8px;
    font-family: inherit;
    transition: opacity 0.2s, transform 0.1s, box-shadow 0.2s;
    box-shadow: 0 4px 15px var(--color-primary-glow);
    margin-top: 4px;
    letter-spacing: 0.01em;
  }
  .btn-primary:hover:not(:disabled) {
    opacity: 0.92;
    box-shadow: 0 6px 20px var(--color-primary-glow);
    transform: translateY(-1px);
  }
  .btn-primary:active:not(:disabled) {
    transform: translateY(0);
  }
  .btn-primary:disabled {
    opacity: 0.6;
    cursor: not-allowed;
  }

  .spinner {
    width: 16px;
    height: 16px;
    border: 2px solid rgba(255, 255, 255, 0.3);
    border-top-color: white;
    border-radius: 50%;
    animation: spin 0.7s linear infinite;
    flex-shrink: 0;
  }
  @keyframes spin {
    to { transform: rotate(360deg); }
  }

  .version-info {
    text-align: center;
    font-size: 0.72rem;
    color: rgba(139, 148, 158, 0.45);
    margin-top: 18px;
    letter-spacing: 0.02em;
  }
</style>
