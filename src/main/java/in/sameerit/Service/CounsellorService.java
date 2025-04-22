package in.sameerit.Service;

import in.sameerit.dto.CounsellorDTO;

public interface CounsellorService {
	
	public CounsellorDTO login(CounsellorDTO counsellorDTO);

	public boolean checkUniqueEmail(String email);

	public boolean registerCounsellor(CounsellorDTO counsellorDTO);

}
