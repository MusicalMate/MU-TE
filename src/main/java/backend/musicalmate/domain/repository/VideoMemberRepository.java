package backend.musicalmate.domain.repository;

import backend.musicalmate.Member.VideoMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoMemberRepository extends JpaRepository<VideoMember, Long> {

}
