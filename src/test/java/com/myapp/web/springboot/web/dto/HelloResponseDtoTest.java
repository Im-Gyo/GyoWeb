package com.myapp.web.springboot.web.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat; // assertj도 Junit에서 자동으로 라이브러리 등록(Junit과 비교장점:확실한 자동완성, 추가적으로 라이브러리가 필요없음)

public class HelloResponseDtoTest {

    @Test
    public void 롬복_기능_테스트() {
        //given : 테스트 전의 상태
        String name = "test";
        int amount = 1000;

        //when : 테스트 행위
        HelloResponseDto dto = new HelloResponseDto(name,amount);

        //then : 테스트 검증
        assertThat(dto.getName()).isEqualTo(name); // assertThat : assertj라는 테스트 검증 라이브러리의 검증 메소드, 검증하고싶은 대상을 메소드 인자로 받음, 메소드 체이닝 지원
        assertThat(dto.getAmount()).isEqualTo(amount); // isEqualTo : assertj의 동등 비교 메소드, assertThat에 있는 값과 isEqualTo의 값을 비교해서 같으면 성공
    }
}
