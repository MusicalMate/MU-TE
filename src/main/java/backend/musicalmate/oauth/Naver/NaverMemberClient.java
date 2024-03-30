package backend.musicalmate.oauth.Naver;

import backend.musicalmate.oauth.OauthMember;
import backend.musicalmate.oauth.OauthMemberClient;
import backend.musicalmate.oauth.OauthServerType;
import backend.musicalmate.oauth.kakao.KakaoMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NaverMemberClient implements OauthMemberClient {
    private final NaverApiClient naverApiClient;

    @Override
    public OauthServerType supportServer(){
        return OauthServerType.NAVER;
    }

    @Override
    public OauthMember fetch(String accessToken){
        //AccessToken 가지고 회원 정보 받아오기(KakaoMemberResponse에 가서 내가 정한 형태로 받아오는 거임)
        NaverMemberResponse naverMemberResponse = naverApiClient.fetchMember("Bearer "+accessToken);
        //OauthMember 객체로 반환해주기
        return naverMemberResponse.toDomain();
    }
}
