package in.sameerit.Service;

import java.util.List;

import in.sameerit.dto.DashboardDTO;
import in.sameerit.dto.EnquiryDTO;
import in.sameerit.dto.EnquiryFilterDTO;

public interface EnquiryService {
	
	public DashboardDTO dashboardResponse(Integer counsellorId);

	public boolean addEnquiries(EnquiryDTO dto, Integer counsellorId);

	public List<EnquiryDTO> getEnquiries(Integer counsellorId);

	public List<EnquiryDTO> getEnquiries(Integer counsellorId, EnquiryFilterDTO filterDTO);

	public EnquiryDTO getEnquiryById(Integer studentId);

}
