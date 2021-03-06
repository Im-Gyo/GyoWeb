//Service에서 비즈니스 로직을 처리하는 것이 아닌 트랜잭션과 도메인간 순서 보장 역할을 함(비즈니스 처리는 Domain에서 함, 기존에 service로 처리하던 방식은 트랜잭션 스크립트라고 함)
package com.myapp.web.springboot.service;

import com.myapp.web.springboot.domain.posts.Posts;
import com.myapp.web.springboot.domain.posts.PostsRepository;
import com.myapp.web.springboot.web.dto.PostsListResponseDto;
import com.myapp.web.springboot.web.dto.PostsResponseDto;
import com.myapp.web.springboot.web.dto.PostsSaveRequestDto;
import com.myapp.web.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional // 이 메서드에 트랜잭션 기능이 적용된 프록시 객체가 생성
    public long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){ // 수정, 여기서 DB에 쿼리를 날리는 부분이 없다. 이유는 JPA의 영속성 컨텍스트 때문(영속성컨텍스트 : 엔티티를 영구 저장하는 환경, JPA의 핵심 내용은 엔티티가 영속성 컨텍스트에 포함되어 있냐 아니냐로 갈림) 이런 개념을 더티 체킹이라고 함
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id)); // 값이 없을 경우 예외처리(Optional)

        posts.update(requestDto.getTitle(), requestDto.getContent()); // Entity에 업데이트 값 변경

        return id;
    }

    public PostsResponseDto findById(Long id){ // 조회
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true) // (readOnly = true) : 트랜잭션 범위는 유지하되 조회 기능만 남겨두어 조회 속도 개선(조회만 가능)
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream() // postsRepository 결과로 넘어온 Posts의 Stream을 map을 통해 PostsListResponseDto 변환 -> List로 반환하는 메소드
                .map(PostsListResponseDto::new) // .map(posts -> new PostsListResponseDto(posts))와 동일
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete (Long id){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        postsRepository.delete(posts); // JpaRepository에서 이미 delete 메소드를 지원하고 있으니 이를 활용, 엔티티를 파라미터로 삭제할 수도 있고 deleteById메소드를 이용하면 id로 삭제할 수도 있음, 존재하는 Posts인지 확인을 위해서 엔티티 조회 후 그대로 삭제함
    }
}
