package com.myapp.web.springboot.web;

import com.myapp.web.springboot.config.auth.SecurityConfig;
import com.myapp.web.springboot.web.HelloController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class) // 테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행시킴(여기서는 SpringRunner실행자 사용, 즉 스프링부트 테스트와 Junit사이에 연결자 역할)

//excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)} : WebMvcTest는 @ControllerAdvice, @Controller를 읽는다, 즉 @Repository @Service @Component는 스캔대상이 아님, 그러니 SecurityConfig는 읽었지만 SecurityConfig를 생성하기 위해 필요한 customOAuth2UserService는 읽을 수 없어 스캔 대상에서 SecurityConfig를 제거함 (언제 삭제될 지 모르니 사용 안하는 걸 추천)
@WebMvcTest(controllers = HelloController.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)}) // Web(SpringMvc)에 집중할 수 있는 어노테이션, 선언할경우 @Controller, @ControllerAdvice 사용가능(@Service, @Component, @Repository 사용불가)
public class HelloControllerTest {

    @Autowired // 스프링이 관리하는 빈을 주입받음
    private MockMvc mvc; // 웹 API테스트 할 때 사용, HTTP GET,POST 등에 대한 API테스트 가능

    @WithMockUser(roles = "USER")
    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(MockMvcRequestBuilders.get("/hello")) // MockMvc를 통해 /hello 주소로 HTTP GET요청, 체이닝 지원
                .andExpect(status().isOk()) // mvc.perform의 결과를 검증, HTTP Header의 Status를 검증, 200,404,500 등의 상태검증, 여기선 참인지 거짓인지만 검증
                .andExpect(content().string(hello)); // mvc.perform의 결과를 검증, 응답 본문의 내용을 검증, Controller에서 "hello"를 리턴하기 때문에 이 값이 맞는지 검증
    }

    @WithMockUser(roles = "USER")
    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                MockMvcRequestBuilders.get("/hello/dto")
                                            .param("name", name) // API테스트할 때 사용될 요청 파라미터 설정(단 값은 String만 허용), 숫자/날짜 데이터도 등록할 때는 문자열로 변경해야함
                                            .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name))) // jsonPath : JSON응답값을 필드별로 검증, $를 기준으로 필드명을 명시
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}
