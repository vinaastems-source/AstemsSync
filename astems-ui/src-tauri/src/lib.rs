use ini::Ini;
use serde::Serialize;
use std::path::Path;
use tauri::{AppHandle, Emitter};

#[derive(Serialize, Clone)]
pub struct StoreInfo {
  #[serde(rename = "storeName")]
  store_name: String,
  #[serde(rename = "storeCode")]
  store_code: String,
  #[serde(rename = "posNo")]
  pos_no: String,
  #[serde(rename = "currentVersion")]
  current_version: String,
}

#[derive(Serialize, Clone)]
struct ProgressPayload {
  percentage: u32,
  status: String,
  message: String,
}

#[tauri::command]
fn get_ini_data() -> Result<StoreInfo, String> {
  let paths = [
      Path::new("../Astems.ini"),
      Path::new("Astems.ini"),
      Path::new("../../Astems.ini"),
  ];

  let mut ini_path = None;
  for p in paths {
      if p.exists() {
          ini_path = Some(p.to_path_buf());
          break;
      }
  }

  let path = ini_path.ok_or_else(|| "Astems.ini 파일을 찾을 수 없습니다.".to_string())?;
  
  // 타입 에러 해결을 위해 Result 명시적 처리
  let conf = Ini::load_from_file(path).map_err(|e| e.to_string())?;
  
  let store_section = conf.section(Some("STORE")).ok_or_else(|| "STORE 섹션을 찾을 수 없습니다.".to_string())?;
  let pos_section = conf.section(Some("POS")).ok_or_else(|| "POS 섹션을 찾을 수 없습니다.".to_string())?;
  let version_section = conf.section(Some("VERSION")).ok_or_else(|| "VERSION 섹션을 찾을 수 없습니다.".to_string())?;

  Ok(StoreInfo {
    store_name: store_section.get("StoreName").unwrap_or("알수없는매장").to_string(),
    store_code: store_section.get("StoreCode").unwrap_or("S0000").to_string(),
    pos_no: pos_section.get("PosNo").unwrap_or("1").to_string(),
    current_version: version_section.get("CurrentVersion").unwrap_or("1.0.0").to_string(),
  })
}

#[tauri::command]
async fn login(user_id: String, password: String) -> Result<String, String> {
  println!("로그인 시도: ID={}, PW=****", user_id);
  
  tokio::time::sleep(std::time::Duration::from_millis(1000)).await;
  
  if password == "1234" {
    Ok("인증 성공".to_string())
  } else {
    Err("인증에 실패하였습니다. 비밀번호를 확인해 주세요.".to_string())
  }
}

#[tauri::command]
async fn start_update(app: AppHandle) -> Result<(), String> {
  tauri::async_runtime::spawn(async move {
    let steps = [
        (10, "데이터 확인", "BF 서버로부터 새로운 업데이트 정보를 조회하고 있습니다."),
        (30, "다운로드 중", "새로운 버전 v1.2.0 패키지를 다운로드 중입니다..."),
        (60, "무결성 검사", "다운로드된 파일의 해시값을 검증하고 있습니다."),
        (85, "압축 해제", "패키지 파일을 시스템에 적용하기 위해 압축을 해제합니다."),
        (100, "완료", "모든 시스템 업데이트가 성공적으로 완료되었습니다."),
    ];

    for (percentage, status, message) in steps {
      let _ = app.emit("update-progress", ProgressPayload {
        percentage,
        status: status.to_string(),
        message: message.to_string(),
      });
      tokio::time::sleep(std::time::Duration::from_millis(1200)).await;
    }
  });

  Ok(())
}

#[cfg_attr(mobile, tauri::mobile_entry_point)]
pub fn run() {
  tauri::Builder::default()
    .plugin(tauri_plugin_log::Builder::default().build())
    .invoke_handler(tauri::generate_handler![get_ini_data, login, start_update])
    .setup(|_app| {
      Ok(())
    })
    .run(tauri::generate_context!())
    .expect("Tauri 앱 실행 중 오류 발생");
}
