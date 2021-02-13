//Service에서 비즈니스 로직을 처리하는 것이 아닌 트랜잭션과 도메인간 순서 보장 역할을 함(비즈니스 처리는 Domain에서 함, 기존에 service로 처리하던 방식은 트랜잭션 스크립트라고 함)
package com.myapp.web.springboot.service;

import com.myapp.web.springboot.domain.posts.Posts;
import com.myapp.web.springboot.domain.posts.PostsRepository;
import com.myapp.web.springboot.web.dto.PostsResponseDto;
import com.myapp.web.springboot.web.dto.PostsSaveRequestDto;
import com.myapp.web.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional // 이 메서드에 트랜잭션 기능이 적용된 프록시 객체가 생성
    public long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }
}
