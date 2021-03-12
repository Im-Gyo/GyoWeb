package com.myapp.web.springboot.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.web.springboot.domain.posts.Posts;
import com.myapp.web.springboot.domain.posts.PostsRepository;
import com.myapp.web.springboot.web.dto.PostsSaveRequestDto;
import com.myapp.web.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//JPA기능까지 한번에 테스트 할때는 @SpringBootTest와 TestRestTemplate을 사용한다.
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // @SpringBootApplication을 찾아서 테스트를 위한 빈들을 다 생성함 그리고 @MockBean으로 정의된 빈을 찾아서 교체함, WebEnvironment.RANDOM_PORT : 내장 톰캣 사용
public class PostsApiControllerTest {

    @LocalServerPort // 실제 주입되는 포트번호 확인
    private int port;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @Before // 매번 테스트가 시작되기 전에 MockMvc 인스턴스를 생성.
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles="USER") // 인증된 모의 사용자를 만들어서 사용, roles에 권한을 추가할 수 있음, 즉 이 어노테이션으로 ROLE_USER권한을 가진 사용자가 API를 요청하는 것과 동일한 효과
    public void Posts_등록된다() throws Exception {
        //given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder() // 빌더패턴으로 인스턴스에 엔티티값 저장
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts"; // port를 포함한 url정보

        //when
        mvc.perform(post(url) // 생성된 MockMvc를 통해 API를 테스트, 본문 영역은 문자열로 표현하기 위해 ObjectMapper를 통해 문자열 JSON으로 변환
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());
        /*ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class); // ResponseEntity는 개발자가 직접 결과 데이터와 HTTP 상태 코드를 직접 제어할 수 있는 클래스로 404나 500같은 HTTP 상태 코드를 전송하고 싶은 데이터와 함께 전송할 수 있음, postForEntity() 메서드는 ResponseEntity<T> 객체로 데이터를 받을 수 있음*/

        //then
/*        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK); // getStatusCode() : http status code 확인
        assertThat(responseEntity.getBody()).isGreaterThan(0L); // getBody() : 실제 데이터 정보 확인*/

        List<Posts> all = postsRepository.findAll(); //
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles="USER")
    public void Posts_수정된다() throws Exception {
        //given
        Posts savedPosts = postsRepository.save(Posts.builder() // 빌더패턴으로 인스턴스에 엔티티값 저장
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "Content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder() // 빌더패턴으로 인스턴스에 필드값 저장
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto); // HttpEntity : http프로토콜을 이용하는 통신의 헤더와 바디 관련 정보를 저장할 수 있도록 함(이를 상속받은 클래스로 RequestEntity와 ResponseEntity가 있음)

        //when
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());
        /*ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class); // exchange : http헤더를 새로 만들 수 있음*/

        //then
        /*assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);*/

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }
}