package backend.musicalmate.domain.repository;

import backend.musicalmate.Member.MusicalMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicalMemberRepository extends JpaRepository<MusicalMember, Long> {
    MusicalMember findBymusicalTitle(String title);
}
