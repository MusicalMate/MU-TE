package backend.musicalmate.domain.repository;

import backend.musicalmate.Member.ActorMember;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ActorMemberRepository extends JpaRepository<ActorMember, Long> {
    ActorMember findByactorName(String name);
}
