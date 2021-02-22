//Entity클래스, DB와 맞닿은 핵심 클래스, Entity클래스를 기준으로 테이블이 생성되고 스키마가 변경됨
package com.myapp.web.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity // 테이블과 링크될 클래스임을 나타냄(기본값으로 클래스의 카멜케이스 이름(ex:publicData)을 언더스코어 네이밍(_)으로 테이블 이름을 매칭 ex) SalesManager.java -> sales_manager table
public class Posts extends BaseTimeEntity{ // 실제 DB의 테이블과 매칭될 클래스(보통 Entity 클래스라고도 함) JPA를 사용하면 DB데이터에 작업할 때 실제 쿼리를 날리기보다, 이 Entity클래스의 수정을 통해 작업

    @Id // 해당 테이블의 PK필드(Primary Key)를 나타냄
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK생성 규칙을 나타냄, 부트 2.0에서는 GenerationType.IDENTITY 옵션을 추가해야 auto_increment(자동증가)가 됨
    private Long id;

    @Column(length = 500, nullable = false) // 테이블의 칼럼을 나타냄 굳이 선언 안해도 해당 클래스의 필드는 모두 칼럼이 됨, 사용하는 이유는 기본값 이외 추가로 변경이 필요한 옵션이 있으면 사용 ex) 문자열의 경우 VARCHAR(255)가 기본값인데 사이즈를 500으로 늘리고 싶거나 타입을 변경하고 싶을 때 사용
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder // 해당 클래스의 빌더 패턴 클래스 생성, 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함(빌더패턴 관련자료 : https://mommoo.tistory.com/54)
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
