package com.myapp.web.springboot.web;

import com.myapp.web.springboot.service.PostsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model){ // Model : 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장, postsService.findAllDesc()로 가져온 결과를 posts로 index.mustache에 전달
        model.addAttribute("posts", postsService.findAllDesc());
        return "index";
    } // 머스테치 스타터로 컨트롤러에서 문자열을 반환할 때 앞의 경로와 뒤의 파일 확장자는 자동으로 지정됨

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }
}
