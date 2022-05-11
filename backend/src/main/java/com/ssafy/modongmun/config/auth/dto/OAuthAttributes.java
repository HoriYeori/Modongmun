package com.ssafy.modongmun.config.auth.dto;

import com.ssafy.modongmun.user.Role;
import com.ssafy.modongmun.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private Long userNumber;
    private String username;
//    private String email;
//    private String picture;

    @Builder
    public OAuthAttributes(
            Map<String, Object> attributes,
            String nameAttributeKey,
            Long userNumber,
            String username
//            String email,
//            String picture
    ) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.userNumber = userNumber;
        this.username = username;
//        this.email = email;
//        this.picture = picture;
    }

    public static OAuthAttributes of(
            String registrationId,
            String userNameAttributeName,
            Map<String, Object> attributes) {

        if ("naver".equals(registrationId))
            return ofNaver("id", attributes);
        else if ("kakao".equals(registrationId))
            return ofKakao("email", attributes);

        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>)attributes.get("response");

        return OAuthAttributes.builder()
                .username((String)response.get("name"))
//                .email((String)response.get("email"))
//                .picture((String)response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>)attributes.get("kakao_account");
        Map<String, String> profile = (Map<String, String>)response.get("profile");

        return OAuthAttributes.builder()
//                .userNumber(Long.valueOf((String)response.get("id")))
                .username(profile.get("nickname"))
//                .email((String)response.get("email"))
//                .picture(profile.get("profile_image_url"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .userNumber((Long)attributes.get("userNumber"))
                .username((String)attributes.get("name"))
//                .email((String).attributes.get("email"))
//                .picture((String)attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .userNumber(userNumber)
                .username(username)
//                .email(email)
//                .picture(picture)
                .role(Role.GUEST)
                .build();
    }

}
