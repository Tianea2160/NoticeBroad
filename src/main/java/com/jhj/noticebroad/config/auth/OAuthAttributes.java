package com.jhj.noticebroad.config.auth;

import com.jhj.noticebroad.domain.user.Role;
import com.jhj.noticebroad.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }


    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        if(registrationId.equals("Naver")){
            return ofNaver(userNameAttributeName, attributes);
        }

        return ofGoogle(userNameAttributeName, attributes);
    }

    //google login을 할때 사용하는 것
    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .name((String)attributes.get("name"))
                .email((String)attributes.get("email"))
                .picture((String)attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    //naver login을 할때 사용하는 것
    public static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes){
        //naver 에서는 json으로 정보를 반환한다. 따라서 자바에서는 이를 사용하기 위해서 Map을 사용하는데 데이터에 접근을 할때 response라는 필드를 통해서
        //원하는 정보를 찾는다.
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profileImage"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                //여기서 게스트로 권한을 원래주는데 이는 실습용도이고 실제로 글 생성, 수정, 삭제를 하기 위해서는 USER로 설정해야한다.
                .role(Role.USER)
                .build();
    }

}
