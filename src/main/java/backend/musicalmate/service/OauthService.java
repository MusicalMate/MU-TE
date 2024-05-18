package backend.musicalmate.service;

import backend.musicalmate.Member.OauthMember;
import backend.musicalmate.domain.repository.OauthMemberRepository;
import backend.musicalmate.oauth.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OauthService {
    private final OauthMemberClientComposite oauthMemberClientComposite;
    private final OauthMemberRepository oauthMemberRepository;

    public OauthMember login(OauthServerType oauthServerType, String accessToken){
        OauthMember oauthMember = oauthMemberClientComposite.fetch(oauthServerType,accessToken);
        OauthMember saved = oauthMemberRepository.findByemail(oauthMember.getEmail())
                .orElseGet(()-> oauthMemberRepository.save(oauthMember));
        return saved;
    }

    public OauthMember findUploadUser(Long userId){
        Optional<OauthMember> optionalOauthMember = oauthMemberRepository.findById(userId);
        if (optionalOauthMember.isPresent()) {
            OauthMember oauthMember = optionalOauthMember.get();
            return oauthMember;
        } else {
            return null;
        }
    }
}
