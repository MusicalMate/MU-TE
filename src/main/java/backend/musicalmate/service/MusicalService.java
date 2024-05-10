package backend.musicalmate.service;

import backend.musicalmate.Member.MusicalMember;
import backend.musicalmate.domain.repository.MusicalMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MusicalService {
    private final MusicalMemberRepository memberRepository;

    public MusicalMember findMusical(String title){
        MusicalMember musicalMember=memberRepository.findBymusicalTitle(title);
        return musicalMember;
    }
}
