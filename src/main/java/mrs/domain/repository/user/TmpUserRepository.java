package mrs.domain.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mrs.domain.model.TmpUser;

public interface TmpUserRepository extends JpaRepository<TmpUser, String>{
	Optional<TmpUser> findByUuidEquals(String uuid);
}
