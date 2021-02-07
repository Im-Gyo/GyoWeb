package com.myapp.web.springboot.web;

import com.myapp.web.springboot.web.HelloController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class) // 테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행시킴(여기서는 SpringRunner실행자 사용, 즉 스프링부트 테스트와 Junit사이에 연결자 역할)
@WebMvcTest(controllers = HelloController.class) // Web(SpringMvc)에 집중할 수 있는 어노테이션, 선언할경우 @Controller, @ControllerAdvice 사용가능(@Service, @Component, @Repository 사용불가)
public class HelloControllerTest {

    @Autowired // 스프링이 관리하는 빈을 주입받음
    private MockMvc mvc; // 웹 API테스트 할 때 사용, HTTP GET,POST 등에 대한 API테스트 가능

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(MockMvcRequestBuilders.get("/hello")) // MockMvc를 통해 /hello 주소로 HTTP GET요청, 체이닝 지원
                .andExpect(status().isOk()) // mvc.perform의 결과를 검증, HTTP Header의 Status를 검증, 200,404,500 등의 상태검증, 여기선 참인지 거짓인지만 검증
                .andExpect(content().string(hello)); // mvc.perform의 결과를 검증, 응답 본문의 내용을 검증, Controller에서 "hello"를 리턴하기 때문에 이 값이 맞는지 검증
    }
}
