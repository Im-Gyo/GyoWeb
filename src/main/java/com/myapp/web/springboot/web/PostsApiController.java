//뷰 템플릿의 영역, 외부요청과 응답에 대한 전반적인 영역을 이야기함.
package com.myapp.web.springboot.web;

import com.myapp.web.springboot.service.PostsService;
import com.myapp.web.springboot.web.dto.PostsResponseDto;
import com.myapp.web.springboot.web.dto.PostsSaveRequestDto;
import com.myapp.web.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController // 기존 @Controller는 View를 반환하기 위해 사용, 하지만 데이터를 반환(JSON형태)해야하는 경우 @RestController 사용
public class PostsApiController {
    private final PostsService postsService;

    //게시글 등록
    @PostMapping("/api/v1/posts") // Post로 RequestMapping(컨트롤러로 넘어오는 URL을 다른 뷰로 매핑)함
    public Long save(@RequestBody PostsSaveRequestDto requestDto){ //@RequestBody : HTTP요청의 body내용을 자바 객체로 매핑
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}") // 수정, @PutMapping : 기존의 정보를 수정할 때 주로 사용
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){ // @PathVariable : URL경로에 변수를 넣어줌
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}") // 조회
    public PostsResponseDto findById (@PathVariable Long id){
        return postsService.findById(id);
    }
}
