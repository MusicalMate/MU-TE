package backend.musicalmate.service;

import backend.musicalmate.oauth.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthService {

    private final AuthCodeRequestUrlProviderComposite authCodeRequestUrlProviderComposite;
    private final OauthMemberClientComposite oauthMemberClientComposite;
    private final OauthMemberRepository oauthMemberRepository;

    public String getAuthCodeRequestUrl(OauthServerType oauthServerType){
        return authCodeRequestUrlProviderComposite.provide(oauthServerType);
    }

    public Long login(OauthServerType oauthServerType, String authCode){
        OauthMember oauthMember = oauthMemberClientComposite.fetch(oauthServerType,authCode);
        OauthMember saved = oauthMemberRepository.findByemail(oauthMember.email())
                .orElseGet(()-> oauthMemberRepository.save(oauthMember));
        return saved.user_id();
    }
}
