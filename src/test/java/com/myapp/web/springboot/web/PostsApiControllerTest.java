package com.myapp.web.springboot.web;

import com.myapp.web.springboot.domain.posts.Posts;
import com.myapp.web.springboot.domain.posts.PostsRepository;
import com.myapp.web.springboot.web.dto.PostsSaveRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // @SpringBootApplication을 찾아서 테스트를 위한 빈들을 다 생성함 그리고 @MockBean으로 정의된 빈을 찾아서 교체함, WebEnvironment.RANDOM_PORT : 내장 톰캣 사용
public class PostsApiControllerTest {

    @LocalServerPort // 실제 주입되는 포트번호 확인
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    public void Posts_등록된다() throws Exception {
        //given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder() // 빌더패턴으로 생성자객체 생성
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts"; // port를 포함한 url정보

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class); // ResponseEntity는 개발자가 직접 결과 데이터와 HTTP 상태 코드를 직접 제어할 수 있는 클래스로 404나 500같은 HTTP 상태 코드를 전송하고 싶은 데이터와 함께 전송할 수 있음, postForEntity() 메서드는 ResponseEntity<T> 객체로 데이터를 받을 수 있음

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK); // getStatusCode() : http status code 확인
        assertThat(responseEntity.getBody()).isGreaterThan(0L); // getBody() : 실제 데이터 정보 확인

        List<Posts> all = postsRepository.findAll(); //
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }
}
