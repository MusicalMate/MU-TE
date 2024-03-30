package backend.musicalmate.oauth.kakao;

import backend.musicalmate.controller.OauthController;
import backend.musicalmate.oauth.OauthMember;
import backend.musicalmate.oauth.OauthMemberClient;
import backend.musicalmate.oauth.OauthServerType;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoMemberClient implements OauthMemberClient {

    private final KakaoApiClient kakaoApiClient;
    private static final Logger logger = LoggerFactory.getLogger(KakaoMemberClient.class);

    @Override
    public OauthServerType supportServer(){
        return OauthServerType.fromName("KAKAO");
    }
    @Override
    public OauthMember fetch(String accessToken){
        //logger.info("a "+);
        //AccessToken 가지고 회원 정보 받아오기(KakaoMemberResponse에 가서 내가 정한 형태로 받아오는 거임)
        KakaoMemberResponse kakaoMemberResponse = kakaoApiClient.fetchMember("Bearer "+accessToken);
        //OauthMember 객체로 반환해주기
        logger.info("kakao:response " +kakaoMemberResponse);
        return kakaoMemberResponse.toDomain();
    }
}
