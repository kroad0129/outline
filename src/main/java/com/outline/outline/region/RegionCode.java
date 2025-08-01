package com.outline.outline.region;

import java.util.Map;
import java.util.stream.Collectors;

public class RegionCode {

    // 프론트에서 넘어오는 시군구 단위 법정동 이름 → 코드 매핑
    public static final Map<String, String> NAME_TO_CODE = Map.ofEntries(
            Map.entry("강원특별자치도 양양군", "1-1"),
            Map.entry("강원특별자치도 속초시", "1-1"),
            Map.entry("강원특별자치도 고성군", "1-1"),
            Map.entry("강원특별자치도 강릉시", "1-2"),
            Map.entry("강원특별자치도 동해시", "1-3"),
            Map.entry("강원특별자치도 삼척시", "1-3"),
            Map.entry("강원특별자치도 태백시", "1-4"),
            Map.entry("강원특별자치도 영월군", "1-4"),
            Map.entry("강원특별자치도 정선군", "1-4"),
            Map.entry("강원특별자치도 평창군", "1-5"),
            Map.entry("강원특별자치도 원주시", "1-6"),
            Map.entry("강원특별자치도 횡성군", "1-6"),
            Map.entry("강원특별자치도 철원군", "1-7"),
            Map.entry("강원특별자치도 춘천시", "1-8"),
            Map.entry("강원특별자치도 홍천군", "1-8"),

            Map.entry("대전광역시 유성구", "2-1"),
            Map.entry("대전광역시 대덕구", "2-1"),
            Map.entry("대전광역시 서구", "2-2"),
            Map.entry("대전광역시 중구", "2-3"),
            Map.entry("대전광역시 동구", "2-3"),

            Map.entry("충청북도 청주시", "3-1"),
            Map.entry("충청북도 제천시", "3-2"),
            Map.entry("충청북도 단양군", "3-2"),
            Map.entry("충청북도 옥천군", "3-3"),

            Map.entry("충청남도 천안시", "4-1"),
            Map.entry("충청남도 아산시", "4-1"),
            Map.entry("충청남도 당진시", "4-2"),
            Map.entry("충청남도 서산시", "4-2"),
            Map.entry("충청남도 태안군", "4-2"),
            Map.entry("충청남도 공주시", "4-3"),
            Map.entry("충청남도 계룡시", "4-3"),
            Map.entry("충청남도 보령시", "4-4"),

            Map.entry("세종특별자치시", "5-1"),

            Map.entry("광주광역시 광산구", "6-1"),
            Map.entry("광주광역시 수완동", "6-1"),
            Map.entry("광주광역시 첨단동", "6-1"),
            Map.entry("광주광역시 북구", "6-2"),
            Map.entry("광주광역시 남구", "6-3"),
            Map.entry("광주광역시 동구", "6-4"),
            Map.entry("광주광역시 서구", "6-5"),

            Map.entry("전라북도 전주시", "7-1"),
            Map.entry("전라북도 군산시", "7-2"),
            Map.entry("전라북도 익산시", "7-2"),
            Map.entry("전라북도 김제시", "7-3"),
            Map.entry("전라북도 부안군", "7-3"),
            Map.entry("전라북도 정읍시", "7-3"),
            Map.entry("전라북도 남원시", "7-4"),
            Map.entry("전라북도 임실군", "7-4"),

            Map.entry("전라남도 여수시", "8-1"),
            Map.entry("전라남도 순천시", "8-1"),
            Map.entry("전라남도 광양시", "8-1"),
            Map.entry("전라남도 장성군", "8-2"),
            Map.entry("전라남도 담양군", "8-2"),
            Map.entry("전라남도 영암군", "8-3"),
            Map.entry("전라남도 강진군", "8-3"),
            Map.entry("전라남도 장흥군", "8-3"),
            Map.entry("전라남도 나주시", "8-4"),
            Map.entry("전라남도 화순군", "8-4"),
            Map.entry("전라남도 목포시", "8-5"),
            Map.entry("전라남도 무안군", "8-5"),
            Map.entry("전라남도 함평군", "8-5"),
            Map.entry("전라남도 해남군", "8-6"),
            Map.entry("전라남도 진도군", "8-6"),
            Map.entry("전라남도 곡성군", "8-7"),
            Map.entry("전라남도 구례군", "8-7"),

            Map.entry("울산광역시 남구", "9-1"),
            Map.entry("울산광역시 중구", "9-1"),
            Map.entry("울산광역시 동구", "9-2"),
            Map.entry("울산광역시 북구", "9-2"),
            Map.entry("울산광역시 울주군", "9-3"),

            Map.entry("부산광역시 서면", "10-1"),
            Map.entry("부산광역시 해운대구", "10-2"),
            Map.entry("부산광역시 수영구", "10-3"),
            Map.entry("부산광역시 광안리", "10-3"),
            Map.entry("부산광역시 기장군", "10-4"),
            Map.entry("부산광역시 동구", "10-5"),
            Map.entry("부산광역시 부산역", "10-5"),
            Map.entry("부산광역시 동래구", "10-6"),
            Map.entry("부산광역시 중구", "10-7"),
            Map.entry("부산광역시 영도구", "10-7"),
            Map.entry("부산광역시 사상구", "10-8"),
            Map.entry("부산광역시 하단", "10-8"),
            Map.entry("부산광역시 서구", "10-8"),
            Map.entry("부산광역시 연제구", "10-9"),
            Map.entry("부산광역시 연산동", "10-9"),
            Map.entry("부산광역시 남구", "10-10"),
            Map.entry("부산광역시 경성대", "10-10"),
            Map.entry("부산광역시 부경대", "10-10"),
            Map.entry("부산광역시 금정구", "10-11"),
            Map.entry("부산광역시 부산대", "10-11"),
            Map.entry("부산광역시 북구", "10-12"),
            Map.entry("부산광역시 덕천", "10-12"),
            Map.entry("부산광역시 명지", "10-13"),
            Map.entry("부산광역시 강서구", "10-13"),

            Map.entry("대구광역시 중구", "11-1"),
            Map.entry("대구광역시 동성로", "11-1"),
            Map.entry("대구광역시 수성구", "11-2"),
            Map.entry("대구광역시 범어동", "11-2"),
            Map.entry("대구광역시 서구", "11-3"),
            Map.entry("대구광역시 성서", "11-3"),
            Map.entry("대구광역시 달성군", "11-3"),
            Map.entry("대구광역시 동구", "11-4"),
            Map.entry("대구광역시 팔공산", "11-4"),
            Map.entry("대구광역시 남구", "11-5"),
            Map.entry("대구광역시 앞산", "11-5"),
            Map.entry("대구광역시 북구", "11-6"),

            Map.entry("경상북도 경주시", "12-1"),
            Map.entry("경상북도 구미시", "12-2"),
            Map.entry("경상북도 김천시", "12-2"),
            Map.entry("경상북도 칠곡군", "12-2"),
            Map.entry("경상북도 문경시", "12-3"),
            Map.entry("경상북도 상주시", "12-3"),
            Map.entry("경상북도 포항시", "12-4"),
            Map.entry("경상북도 안동시", "12-5"),
            Map.entry("경상북도 영주시", "12-5"),

            Map.entry("경상남도 창녕군", "13-1"),
            Map.entry("경상남도 의령군", "13-1"),
            Map.entry("경상남도 함안군", "13-1"),
            Map.entry("경상남도 남해군", "13-2"),
            Map.entry("경상남도 김해시", "13-3"),
            Map.entry("경상남도 장유", "13-3"),
            Map.entry("경상남도 진주시", "13-4"),
            Map.entry("경상남도 사천시", "13-4"),
            Map.entry("경상남도 통영시", "13-5"),
            Map.entry("경상남도 거제시", "13-5"),
            Map.entry("경상남도 마산", "13-6"),
            Map.entry("경상남도 창원시", "13-6"),
            Map.entry("경상남도 진해", "13-6"),
            Map.entry("경상남도 밀양시", "13-7"),
            Map.entry("경상남도 양산시", "13-7"),

            Map.entry("제주특별자치도 제주시", "14-1"),
            Map.entry("제주특별자치도 서귀포시", "14-2")
    );

    public static final Map<String, String> CODE_TO_NAME = NAME_TO_CODE.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey, (a, b) -> a));
}
