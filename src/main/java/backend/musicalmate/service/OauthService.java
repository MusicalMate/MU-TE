package backend.musicalmate.service;

import backend.musicalmate.Member.OauthMember;
import backend.musicalmate.oauth.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthService {
    private final OauthMemberClientComposite oauthMemberClientComposite;
    private final OauthMemberRepository oauthMemberRepository;

    public Long login(OauthServerType oauthServerType, String accessToken){
        OauthMember oauthMember = oauthMemberClientComposite.fetch(oauthServerType,accessToken);
        OauthMember saved = oauthMemberRepository.findByemail(oauthMember.email())
                .orElseGet(()-> oauthMemberRepository.save(oauthMember));
        return saved.user_id();
    }
}
