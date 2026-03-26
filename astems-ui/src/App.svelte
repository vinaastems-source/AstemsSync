<script lang="ts">
  import './app.css'
  import LoginPage from './lib/LoginPage.svelte'
  import UpdatePage from './lib/UpdatePage.svelte'
  import { invoke } from '@tauri-apps/api/core'
  import { onMount } from 'svelte'

  // Svelte 5 기반 반응형 상태 관리
  let currentView = $state('login') // 'login' | 'update'
  let storeInfo = $state({
    storeName: '로드 중...',
    storeCode: '-',
    posNo: '-',
    currentVersion: '-',
  })

  onMount(async () => {
    try {
      const data = await invoke('get_ini_data')
      storeInfo = data as any
    } catch (e) {
      console.error('설정 파일을 읽을 수 없습니다.', e)
      storeInfo.storeName = '초기화 실패 (Astems.ini)'
    }
  })

  function handleLoginSuccess() {
    currentView = 'update'
  }
</script>

{#if currentView === 'login'}
  <LoginPage {storeInfo} onLoginSuccess={handleLoginSuccess} />
{:else}
  <UpdatePage {storeInfo} />
{/if}
