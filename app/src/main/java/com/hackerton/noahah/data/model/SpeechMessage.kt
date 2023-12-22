package com.hackerton.noahah.data.model

enum class SpeechMessage(val message: String) {
    SPLASH_MENT("히어디에프에 오신 것을 환영합니다."),
    MAIN_INIT_MENT("피디에프 업로드 화면입니다. 화면 한가운데를 클릭 후 원하는 파일을 선택해주세요"),
    MODE_AGAIN_MENT("모드 선택 화면입니다. 현재 점자 모드 또는 음성 모드가 존재합니다. 원하시는 모드를 말슴해주세요."),
    MODE_INIT_MENT("파일 업로드가 성공되었습니다. 모드 선택 화면입니다. 현재 점자 모드 또는 음성 모드가 존재합니다. 원하시는 모드를 말슴해주세요."),
    COMPLETE_BRAILLE("점자 모드로 변환이 완료되었습니다."),
    COMPLETE_VOICE("음성 모드로 변환이 완료되었습니다.")
}