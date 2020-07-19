package com.pubg.controller;

import java.util.TreeMap;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.paytm.pg.merchant.CheckSumServiceHelper;
import com.pubg.dto.StatusDTO;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/payment")
public class PaymentController {

	@PostMapping(value = "/generateChecksum")
	public @ResponseBody StatusDTO generateChecksum(@RequestBody TreeMap<String, String> paymentParams) throws Exception {

		paymentParams.forEach((k, v) -> System.out.println("Key: "+k+" Value: "+v));
		String checksum = getCheckSum(paymentParams);
		System.out.println(checksum);
		StatusDTO status = new StatusDTO(true, "checksum_001", checksum);
		return status;
	}
	
	@PostMapping(value = "/verifyChecksum/{paytmChecksum}")
	public @ResponseBody StatusDTO verifyChecksum(@RequestBody TreeMap<String, String> paymentParams, @PathVariable String paytmChecksum) throws Exception {

		paymentParams.forEach((k, v) -> System.out.println("Key: "+k+" Value: "+v));
		boolean flag = validateCheckSum(paymentParams,paytmChecksum);
		StatusDTO status =null;
		if(flag) {
			status = new StatusDTO(true, "checksum_ver_001", "valid");
		}else {
			status = new StatusDTO(false, "checksum_ver_002", "invalid");
		}
		
		return status;
	}

	private boolean validateCheckSum(TreeMap<String, String> parameters, String paytmChecksum) throws Exception {
		return CheckSumServiceHelper.getCheckSumServiceHelper().verifycheckSum("R@yj&DfNeE3TPTP6",parameters, paytmChecksum);
	}


	private String getCheckSum(TreeMap<String, String> parameters) throws Exception {
		return CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum("R@yj&DfNeE3TPTP6", parameters);
	}



}
