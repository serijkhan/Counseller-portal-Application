package in.sameerit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sameerit.entity.EnquiryEntity;

public interface EnquiryRepository  extends JpaRepository<EnquiryEntity, Integer>{
	
	public List<EnquiryEntity> findByCounsellorCounsellorId(Integer counsellorId);
	
	

}
