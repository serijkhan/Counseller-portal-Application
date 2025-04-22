package in.sameerit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sameerit.entity.CounsellorEntity;

public interface CounsellorRepository extends JpaRepository<CounsellorEntity, Integer> {
	
	public CounsellorEntity findByCounsellorEmailAndCounsellorPassword(String email, String password);

	public CounsellorEntity findByCounsellorEmail(String email);

}
