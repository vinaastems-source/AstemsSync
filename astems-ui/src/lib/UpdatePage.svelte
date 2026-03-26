<script lang="ts">
  import { onMount } from 'svelte'
  import { listen } from '@tauri-apps/api/event'
  import { invoke } from '@tauri-apps/api/core'

  interface Props {
    storeInfo: {
      storeName: string
      storeCode: string
      posNo: string
      currentVersion: string
    }
  }

  let { storeInfo }: Props = $props()

  let progress = $state(0)
  let statusText = $state('업데이트 확인 중...')
  let detailMessage = $state('BF 서버와 연결을 시도하고 있습니다.')
  let isCompleted = $state(false)

  onMount(() => {
    let unlistenFn: (() => void) | undefined

    const setupListener = async () => {
      unlistenFn = await listen('update-progress', (event: any) => {
        const { percentage, status, message } = event.payload as { percentage: number, status: string, message: string }
        progress = percentage
        statusText = status
        detailMessage = message
        
        if (percentage >= 100) {
          isCompleted = true
        }
      })
      
      // 리스너가 준비된 후 Rust 사이드에 업데이트 시작 명령을 내림
      await invoke('start_update')
    }

    setupListener()

    return () => {
      if (unlistenFn) unlistenFn()
    }
  })
</script>

<div class="update-container">
  <div class="glow-orb"></div>

  <div class="update-card">
    <div class="card-header">
      <div class="pulse-icon" class:completed={isCompleted}>
        {#if isCompleted}
          <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="3" stroke-linecap="round" stroke-linejoin="round">
            <polyline points="20 6 9 17 4 12"></polyline>
          </svg>
        {:else}
          <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M21 2v6h-6"></path>
            <path d="M3 12a9 9 0 0 1 15-6.7L21 8"></path>
            <path d="M3 22v-6h6"></path>
            <path d="M21 12a9 9 0 0 1-15 6.7L3 16"></path>
          </svg>
        {/if}
      </div>
      <div>
        <h2 class="title">{isCompleted ? '업데이트 완료' : '시스템 업데이트'}</h2>
        <p class="subtitle">{storeInfo.storeName} (POS {storeInfo.posNo})</p>
      </div>
    </div>

    <div class="status-box">
      <div class="status-top">
        <span class="status-label">{statusText}</span>
        <span class="percentage">{Math.round(progress)}%</span>
      </div>
      
      <div class="progress-track">
        <div class="progress-fill" style="width: {progress}%">
          <div class="progress-glow"></div>
        </div>
      </div>
      
      <p class="detail-text">{detailMessage}</p>
    </div>

    <div class="info-list">
      <div class="info-item">
        <span class="label">현재 버전</span>
        <span class="value">v{storeInfo.currentVersion}</span>
      </div>
      <div class="info-item">
        <span class="label">상태</span>
        <span class="value" class:text-primary={!isCompleted}>{isCompleted ? '최적 상태' : '업데이트 진행 중'}</span>
      </div>
    </div>

    {#if isCompleted}
      <button class="btn-refresh" onclick={() => window.location.reload()}>
        프로그램 재시작
      </button>
    {/if}
  </div>
</div>

<style>
  .update-container {
    position: relative;
    width: 480px;
    height: 700px;
    display: flex;
    align-items: center;
    justify-content: center;
    background: #0d1117;
    overflow: hidden;
  }

  .glow-orb {
    position: absolute;
    width: 300px;
    height: 300px;
    background: rgba(79, 110, 247, 0.12);
    filter: blur(80px);
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
  }

  .update-card {
    position: relative;
    z-index: 10;
    width: 360px;
    background: #161b22;
    border: 1px solid rgba(255, 255, 255, 0.08);
    border-radius: 20px;
    padding: 40px 30px;
    box-shadow: 0 20px 50px rgba(0, 0, 0, 0.5);
  }

  .card-header {
    display: flex;
    align-items: center;
    gap: 15px;
    margin-bottom: 40px;
  }

  .pulse-icon {
    width: 48px;
    height: 48px;
    background: rgba(79, 110, 247, 0.1);
    border-radius: 12px;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #4f6ef7;
    animation: rotate 3s linear infinite;
  }
  
  .pulse-icon.completed {
    background: rgba(63, 185, 80, 0.1);
    color: #3fb950;
    animation: none;
  }

  @keyframes rotate {
    from { transform: rotate(0deg); }
    to { transform: rotate(360deg); }
  }

  .title {
    font-size: 1.2rem;
    font-weight: 700;
    color: #e6edf3;
    margin-bottom: 2px;
  }

  .subtitle {
    font-size: 0.75rem;
    color: #8b949e;
  }

  .status-box {
    margin-bottom: 30px;
  }

  .status-top {
    display: flex;
    justify-content: space-between;
    align-items: flex-end;
    margin-bottom: 12px;
  }

  .status-label {
    font-size: 0.85rem;
    font-weight: 600;
    color: #e6edf3;
  }

  .percentage {
    font-size: 1.1rem;
    font-weight: 800;
    color: #4f6ef7;
  }

  .progress-track {
    width: 100%;
    height: 6px;
    background: rgba(255, 255, 255, 0.05);
    border-radius: 3px;
    overflow: hidden;
    margin-bottom: 15px;
  }

  .progress-fill {
    height: 100%;
    background: linear-gradient(90deg, #4f6ef7, #7c6bef);
    border-radius: 3px;
    transition: width 0.3s ease-out;
    position: relative;
  }

  .progress-glow {
    position: absolute;
    top: 0;
    right: 0;
    width: 20px;
    height: 100%;
    background: white;
    filter: blur(10px);
    opacity: 0.3;
  }

  .detail-text {
    font-size: 0.7rem;
    color: #8b949e;
    line-height: 1.5;
  }

  .info-list {
    background: rgba(255, 255, 255, 0.02);
    border-radius: 12px;
    padding: 12px;
    margin-bottom: 25px;
  }

  .info-item {
    display: flex;
    justify-content: space-between;
    padding: 8px 0;
  }
  .info-item:not(:last-child) {
    border-bottom: 1px solid rgba(255, 255, 255, 0.03);
  }

  .label {
    font-size: 0.7rem;
    color: #8b949e;
  }

  .value {
    font-size: 0.7rem;
    font-weight: 600;
    color: #e6edf3;
  }

  .text-primary {
    color: #4f6ef7;
  }

  .btn-refresh {
    width: 100%;
    padding: 12px;
    background: #238636;
    color: white;
    border: none;
    border-radius: 10px;
    font-weight: 600;
    cursor: pointer;
    transition: background 0.2s;
  }
  .btn-refresh:hover {
    background: #2ea043;
  }
</style>
