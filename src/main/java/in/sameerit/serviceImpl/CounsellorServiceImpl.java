package in.sameerit.serviceImpl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sameerit.Service.CounsellorService;
import in.sameerit.dto.CounsellorDTO;
import in.sameerit.entity.CounsellorEntity;
import in.sameerit.repo.CounsellorRepository;

@Service
public class CounsellorServiceImpl implements CounsellorService{
	
	@Autowired
	private CounsellorRepository counsellorRepo;

	@Override
	public CounsellorDTO login(CounsellorDTO counsellorDTO) {

		CounsellorEntity entity = counsellorRepo.findByCounsellorEmailAndCounsellorPassword(
				counsellorDTO.getCounsellorEmail(), counsellorDTO.getCounsellorPassword());

		if (entity != null) {
			CounsellorDTO dto = new CounsellorDTO();
			BeanUtils.copyProperties(entity, dto);
			return dto;
		}
		return null;
	}

	@Override
	public boolean checkUniqueEmail(String email) {
		CounsellorEntity entity = counsellorRepo.findByCounsellorEmail(email);
		return entity == null;
	}

	@Override
	public boolean registerCounsellor(CounsellorDTO counsellorDTO) {
		CounsellorEntity entity = new CounsellorEntity();
		BeanUtils.copyProperties(counsellorDTO, entity);
		CounsellorEntity save = counsellorRepo.save(entity);
		return save.getCounsellorId() != null;
	}



}
