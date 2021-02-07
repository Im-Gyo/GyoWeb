package com.myapp.web.springboot.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 컨트롤러를 JSON을 반환하는 컨트롤러로 만들어줌
public class HelloController {
    @GetMapping("/hello") // http메소드인 get요청을 받을 수 있는 API 생성
    public String hello(){
        return "hello"; // /hello로 요청이 오면 문자열 hello를 반환
    }
}
