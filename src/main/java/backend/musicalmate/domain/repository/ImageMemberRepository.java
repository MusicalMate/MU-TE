package backend.musicalmate.domain.repository;

import backend.musicalmate.Member.ImageMember;
import backend.musicalmate.Member.OauthMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageMemberRepository extends JpaRepository<ImageMember,Long> {
    ImageMember findByUploadImageList(OauthMember oauthMember);
}
