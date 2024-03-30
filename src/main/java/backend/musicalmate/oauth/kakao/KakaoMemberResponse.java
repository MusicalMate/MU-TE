package backend.musicalmate.oauth.kakao;

import backend.musicalmate.oauth.OauthId;
import backend.musicalmate.oauth.OauthMember;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;

import static backend.musicalmate.oauth.OauthServerType.KAKAO;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoMemberResponse(
    Long id,
    boolean hasSignedUp,
    LocalDateTime connectedAt,
    KakaoAccount kakaoAccount
){
    public OauthMember toDomain(){
        return OauthMember.builder()
                .oauthId(new OauthId(KAKAO))
                .nickname(kakaoAccount.profile.nickname)
                .email(kakaoAccount.email)
                .picture(kakaoAccount.profile.profileImageUrl)
                .build();
    }
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record KakaoAccount(
            boolean profileNeedsAgreement,
            boolean profileNicknameNeedsAgreement,
            boolean profileImageNeedsAgreement,
            String email,
            String name,
            Profile profile,
            boolean nameNeedsAgreement,
            //String name,
            boolean emailNeedsAgreement,
            boolean isEmailValid,
            boolean isEmailVerified,
            //String email,
            LocalDateTime ciAuthenticatedAt
    ){
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public record Profile(
                String nickname,
                String thumbnailImageUrl,
                String profileImageUrl,
                //String profileImageUrl,
                boolean isDefaultImage
        ){

        }
    }

}
