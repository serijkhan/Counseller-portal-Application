package in.sameerit.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import in.sameerit.Service.EnquiryService;
import in.sameerit.dto.DashboardDTO;
import in.sameerit.dto.EnquiryDTO;
import in.sameerit.dto.EnquiryFilterDTO;
import in.sameerit.entity.CounsellorEntity;
import in.sameerit.entity.EnquiryEntity;
import in.sameerit.repo.CounsellorRepository;
import in.sameerit.repo.EnquiryRepository;

@Service
public class EnquiryServiceImpl implements EnquiryService{
	
	@Autowired
	private EnquiryRepository enqRepo;

	@Autowired
	private CounsellorRepository counsellorRepo;

	@Override
	public DashboardDTO dashboardResponse(Integer counsellorId) {
		List<EnquiryEntity> entityList = enqRepo.findByCounsellorCounsellorId(counsellorId);
		DashboardDTO dto = new DashboardDTO();
		
		long openCount = entityList.stream().filter(enq -> enq.getStatus().equals("Open")).collect(Collectors.toList())
				.size();
		long closedCount = entityList.stream().filter(enq -> enq.getStatus().equals("Enrolled"))
				.collect(Collectors.toList()).size();
		long lostCount = entityList.stream().filter(enq -> enq.getStatus().equals("Lost")).collect(Collectors.toList())
				.size();
		dto.setTotalEnquiries((long) entityList.size());
		dto.setOpenEnquiries(openCount);
		dto.setEnrolledEnquiries(closedCount);
		dto.setLostEnquiries(lostCount);
		return dto;
	}

	@Override
	public boolean addEnquiries(EnquiryDTO dto, Integer counsellorId) {
		EnquiryEntity entity = new EnquiryEntity();
		BeanUtils.copyProperties(dto, entity);
		Optional<CounsellorEntity> byId = counsellorRepo.findById(counsellorId);
		if (byId.isPresent()) {
			CounsellorEntity counsellor = byId.get();
			entity.setCounsellor(counsellor);
		}
		EnquiryEntity save = enqRepo.save(entity);
		return save.getEnqId() != null;
	}

	@Override
	public List<EnquiryDTO> getEnquiries(Integer counsellorId) {
		List<EnquiryEntity> enqList = enqRepo.findByCounsellorCounsellorId(counsellorId);
		List<EnquiryDTO> dtoList = new ArrayList<>();
		for (EnquiryEntity enquiryEntity : enqList) {
			EnquiryDTO dto = new EnquiryDTO();
			BeanUtils.copyProperties(enquiryEntity, dto);
			dtoList.add(dto);
		}
		return dtoList;
	}

	@Override
	public List<EnquiryDTO> getEnquiries(Integer counsellorId, EnquiryFilterDTO filterDTO) {
		EnquiryEntity entity = new EnquiryEntity();
		if (filterDTO.getClassMode() != null && !filterDTO.getClassMode().isEmpty()) {
			System.out.println("Filtering by class mode: " + filterDTO.getClassMode());
			entity.setClassMode(filterDTO.getClassMode());
		}
		if (filterDTO.getCourse() != null && !filterDTO.getCourse().isEmpty()) {
			System.out.println("Filtering by course: " + filterDTO.getCourse());
			entity.setCourse(filterDTO.getCourse());
		}
		if (filterDTO.getStatus() != null && !filterDTO.getStatus().isEmpty()) {
			System.out.println("Filtering by status: " + filterDTO.getStatus());
			entity.setStatus(filterDTO.getStatus());
		}
		CounsellorEntity counsellor = new CounsellorEntity();
		counsellor.setCounsellorId(counsellorId);
		entity.setCounsellor(counsellor);
		Example<EnquiryEntity> of = Example.of(entity);
		List<EnquiryEntity> enqList = enqRepo.findAll(of);
		List<EnquiryDTO> enquiryDTO = new ArrayList<>();
		for (EnquiryEntity enquiryEntity : enqList) {
			EnquiryDTO dto = new EnquiryDTO();
			BeanUtils.copyProperties(enquiryEntity, dto);
			enquiryDTO.add(dto);
		}
		return enquiryDTO;
	}

	@Override
	public EnquiryDTO getEnquiryById(Integer enqId) {
		Optional<EnquiryEntity> byId = enqRepo.findById(enqId);
		if (byId.isPresent()) {
			EnquiryEntity enquiryEntity = byId.get();
			EnquiryDTO enquiryDTO = new EnquiryDTO();
			BeanUtils.copyProperties(enquiryEntity, enquiryDTO);
			return enquiryDTO;
		}
		return null;
	}



}
