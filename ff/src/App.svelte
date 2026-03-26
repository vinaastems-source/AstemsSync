<script lang="ts">
  import './app.css'
  import LoginPage from './lib/LoginPage.svelte'

  // Astems.ini 에서 읽어온 매장 정보 (실제 환경에서는 Tauri IPC 통해 FB로부터 수신)
  // 현재 단계에서는 목업(mock) 데이터 사용
  const storeInfo = {
    storeName: 'AstemsSync 테스트 매장',
    storeCode: 'S0001',
    posNo: '1',
    currentVersion: '1.0.0',
  }
</script>

<LoginPage {storeInfo} />
