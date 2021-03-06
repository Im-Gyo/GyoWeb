package com.myapp.web.springboot.web;

import com.myapp.web.springboot.config.auth.LoginUser;
import com.myapp.web.springboot.config.auth.dto.SessionUser;
import com.myapp.web.springboot.service.PostsService;
import com.myapp.web.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;
    
    
    // 기존에 httpSession.getAttribute("user")로 가져오던 세션정보값 개선, 어느 컨트롤러에서든지 @LoginUser만 사용하면 세션 정보를 가져올 수 있음
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){ // Model : 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장, postsService.findAllDesc()로 가져온 결과를 posts로 index.mustache에 전달
        model.addAttribute("posts", postsService.findAllDesc());

        /*SessionUser user = (SessionUser)httpSession.getAttribute("user"); // CustomOAuth2Service에서 로그인 성공시 세션에 SessionUser를 저장, 즉,로그인 성공시httpSession.getAttribute("user") 에서 값을 가져올 수 있음*/

        if(user != null){ // 세션에 저장된 값이 있을 때만 model에 userName으로 등록, 세션에 저장된 값이 없으면 로그인 버튼이 보이게 됨
            model.addAttribute("userName", user.getName());
        }
        return "index";
    } // 머스테치 스타터로 컨트롤러에서 문자열을 반환할 때 앞의 경로와 뒤의 파일 확장자는 자동으로 지정됨

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
