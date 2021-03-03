//인증된 사용자 정보만 필요함, 그 외에 필요한 정보들은 없으니 아래 3가지 필드만 선언
//User클래스를 사용하지 않고 따로 만든 이유는 엔티티 클래스에는 언제 다른 엔티티와 관계가 형성될 지 모르기 때문(예를 들어 @OneToMany, @ManyToMany 등 자식 엔티티를 갖고 있다면 직렬화 대상에 자식들까지 포함되어 이슈발생확률이 높음)
//그래서 직렬화 기능을 가진 세션 Dto를 하나 추가로 만듬
package com.myapp.web.springboot.config.auth.dto;


import com.myapp.web.springboot.domain.user.User;
import lombok.Getter;

@Getter
public class SessionUser {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }

}
