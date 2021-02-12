//뷰 템플릿의 영역, 외부요청과 응답에 대한 전반적인 영역을 이야기함.
package com.myapp.web.springboot.web;

import com.myapp.web.springboot.service.PostsService;
import com.myapp.web.springboot.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController // 기존 @Controller는 View를 반환하기 위해 사용, 하지만 데이터를 반환(JSON형태)해야하는 경우 @RestController 사용
public class PostsApiController {
    private final PostsService postsService;

    @PostMapping("/api/v1/posts") // Post로 RequestMapping(컨트롤러로 넘어오는 URL을 다른 뷰로 매핑)함
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }
}
