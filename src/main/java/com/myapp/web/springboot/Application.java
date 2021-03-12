package com.myapp.web.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing // JPA Auditing 활성화, 테스트 시 @WebMvcTest가 EnableJpaAuditing, SpringBootApplication 를 둘 다 스캔에서 분리함(JpaConfig 패키지로 이동)
@SpringBootApplication //스프링부트의 자동설정, Bean읽기와 생성을 자동으로 설정
public class Application {  //프로젝트의 메인 클래스
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
