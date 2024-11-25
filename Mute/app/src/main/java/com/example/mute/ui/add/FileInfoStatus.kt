package com.example.mute.ui.add

enum class FileInputStatus(val description: String) {
    EMPTY("초기상태"),
    FILE_TITLE_NOT_ENTERED("파일 제목이 입력해주세요."),
    MUSICAL_TITLE_NOT_ENTERED("뮤지컬 제목을 입력해주세요."),
    PERFORMANCE_TIME_NOT_SELECTED("공연 시간을 선택해주세요."),
    ACTOR_NOT_SELECTED("배우를 선택해주세요."),
    COMPLETE("완료")
}