package backend.musicalmate.oauth.Naver;

import backend.musicalmate.oauth.OauthId;
import backend.musicalmate.Member.OauthMember;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import static backend.musicalmate.oauth.OauthServerType.NAVER;

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public record NaverMemberResponse(
        String resultcode,
        String message,
        Response response
) {
    public OauthMember toDomain(){
        return OauthMember.builder()
                .oauthId(new OauthId(NAVER))
                .nickname(response.nickname)
                .email(response.email)
                .picture(response.profileImageUrl)
                .build();
    }
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Response(
            String id,
            String nickname,
            String email,
            String profileImageUrl
    ){
    }
}
