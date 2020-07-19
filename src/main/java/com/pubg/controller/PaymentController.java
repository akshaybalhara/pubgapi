package com.pubg.controller;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
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
	
	@PostMapping(value = "/pgresponse")
    public void getResponseRedirect(HttpServletRequest request,HttpServletResponse httpServletResponse, Model model) throws UnsupportedEncodingException {

        Map<String, String[]> mapData = request.getParameterMap();
        TreeMap<String, String> parameters = new TreeMap<String, String>();
        mapData.forEach((key, val) -> parameters.put(key, val[0]));
        String paytmChecksum = "";
        if (mapData.containsKey("CHECKSUMHASH")) {
            paytmChecksum = mapData.get("CHECKSUMHASH")[0];
        }
        String result;

        boolean isValideChecksum = false;
        System.out.println("RESULT : "+parameters.toString());
        try {
            isValideChecksum = validateCheckSum(parameters, paytmChecksum);
            if (isValideChecksum && parameters.containsKey("RESPCODE")) {
                if (parameters.get("RESPCODE").equals("01")) {
                    result = "Payment Successful";
                } else {
                    result = "Payment Failed";
                }
            } else {
                result = "Checksum mismatched";
            }
        } catch (Exception e) {
            result = e.toString();
        }
        model.addAttribute("result",result);
        parameters.remove("CHECKSUMHASH");
        model.addAttribute("parameters",parameters);
        
        httpServletResponse.setStatus(302);
        String redirectUrl = "http://localhost:8100/wallet?mapData="+Base64.getEncoder().encodeToString(parameters.toString().getBytes("utf-8"))+"&result="+result;
        httpServletResponse.setHeader("Location", redirectUrl);
        //return "redirect:" + redirectUrl;
    }



}
