//Posts클래스로 DB를 접근하게 해줄 interface, 보통 DAO라고 불리는 DB Layer 접근자 JPA에서는 Repository라고 부르고 인터페이스로 생성
package com.myapp.web.springboot.domain.posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


// 인터페이스 생성 후 JpaRepository<Posts, Long>를 상속하면 기본적인 CRUD메소드가 자동으로 생성됨
// 주의할 점은 Entity클래스와 기분 Entity Repository는 함께 위치해야함
public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC") // SpringDataJpa에서 제공하지 않는 메소드는 이와 같이 쿼리로 작성해도 됨.
    List<Posts> findAllDesc();
}
