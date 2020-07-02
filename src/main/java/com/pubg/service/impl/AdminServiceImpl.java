/**
 * 
 */
package com.pubg.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pubg.dto.MatchesDTO;
import com.pubg.dto.StatusDTO;
import com.pubg.exception.PUBGBusinessException;
import com.pubg.messages.constants.MessageConstants;
import com.pubg.repository.AdminRepository;
import com.pubg.repository.MatchesRepository;
import com.pubg.service.AdminService;
import com.pubg.service.BaseService;

/**
 * @author Anigam
 *
 */
@Service
public class AdminServiceImpl extends BaseService implements AdminService,MessageConstants{

	/**
	 * The Logger instance.
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AdminRepository adminRepository;

	@Override
	public StatusDTO processMatches(MatchesDTO matchesDTO) {
		// TODO Auto-generated method stub
		
		logger.info("Entering AnnualTrvlAlwnceServiceImpl.processAnnualTravelAllowanceRequest() method.");
		StatusDTO status = null;
		if(matchesDTO!=null && matchesDTO.getOperation()!=null){
			String operation = matchesDTO.getOperation();
			switch (operation) {
			case "Insert":
				adminRepository.insertMatch(matchesDTO);
				status = new StatusDTO(true,ADMIN_INSERT_MATCH_CODE,ADMIN_INSERT_MATCH_MSG);
				break;
//			case "Delete":
//				annualTrvlAlwnceRepository.updateMatch();
//				status = new StatusDTO(true,ANNUAL_TRVL_RECOMMENDED_CODE,ANNUAL_TRVL_RECOMMENDED_MSG);
//				break;	
//			case "Update":
//				annualTrvlAlwnceRepository.updateAnnualTravelAllowanceDetails(annualTrvlAlwnce);
//				status = new StatusDTO(true,ANNUAL_TRVL_ACCEPTED_CODE,ANNUAL_TRVL_ACCEPTED_MSG);
//				break;			
			default:
				throw new PUBGBusinessException(SOMETHING_WENT_WRONG, SOMETHING_WENT_WRONG_MSG);
			}			
		}
		logger.info("Exiting AnnualTrvlAlwnceServiceImpl.processAnnualTravelAllowanceRequest() method.");
		return status;
	}
	
}
