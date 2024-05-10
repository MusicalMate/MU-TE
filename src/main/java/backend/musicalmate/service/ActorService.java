package backend.musicalmate.service;

import backend.musicalmate.Member.ActorMember;
import backend.musicalmate.domain.repository.ActorMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActorService {
    private final ActorMemberRepository actorMemberRepository;
    public ActorMember findActor(String name){
        ActorMember actorMember = actorMemberRepository.findByactorName(name);
                //.orElseGet(()-> null);

        return actorMember;
    }
}
