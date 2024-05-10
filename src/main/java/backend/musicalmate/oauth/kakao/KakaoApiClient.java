package backend.musicalmate.oauth.kakao;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

//HTTP Interface Client 사용
public interface KakaoApiClient {

    @GetExchange(url="https://kapi.kakao.com/v2/user/me")
    KakaoMemberResponse fetchMember(@RequestHeader(name = AUTHORIZATION) String bearerToken);
}
