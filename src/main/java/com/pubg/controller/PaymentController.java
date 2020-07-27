package com.pubg.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.paytm.pg.merchant.CheckSumServiceHelper;
import com.pubg.dto.MatchesDTO;
import com.pubg.dto.StatusDTO;
import com.pubg.dto.WalletDTO;
import com.pubg.entity.JoinedMatchesEntity;
import com.pubg.entity.PaymentEntity;
import com.pubg.entity.WalletEntity;
import com.pubg.service.AdminService;
import com.pubg.service.MatchesService;
import com.pubg.service.PaymentService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private MatchesService matchService;
	
	@Autowired
	private AdminService adminService;

	@PostMapping(value = "/generateChecksum")
	public @ResponseBody StatusDTO generateChecksum(@RequestBody TreeMap<String, String> paymentParams) throws Exception {

		paymentParams.forEach((k, v) -> System.out.println("Key: "+k+" Value: "+v));
		String checksum = getCheckSum(paymentParams);
		System.out.println(checksum);
		StatusDTO status = new StatusDTO(true, "checksum_001", checksum);
		return status;
	}
	
	@PostMapping(value = "/payViaWallet")
	public @ResponseBody PaymentEntity joinViaWallet(@RequestBody TreeMap<String, String> paymentParams) throws Exception {
		paymentParams.forEach((k, v) -> System.out.println("Key: "+k+" Value: "+v));
		String userId = paymentParams.get("userId");
		int matchId = Integer.parseInt(paymentParams.get("matchId"));
		int amount = Integer.parseInt(paymentParams.get("amount"));
		WalletEntity walletEntity = adminService.getWalletBalance(userId);
		PaymentEntity paymentEntity = new PaymentEntity();
		if(walletEntity.getBalance() > amount) {
			paymentEntity = paymentService.addWalletTransactionDetails(userId,matchId,amount);
			if(paymentEntity.getPaymentStatus().equals("SUCCESS")) {
				JoinedMatchesEntity entity = new JoinedMatchesEntity();
	        	entity.setMatchId(matchId);
	        	entity.setUserId(userId);
	        	entity.setFeeStatus("Paid");
	        	entity.setSubmissionDate(new Date());
	        	entity.setStatus(null);
	        	matchService.joinAMatch(entity);
	        	MatchesDTO matchesDTO = new MatchesDTO();
	        	matchesDTO.setMatchId(matchId);
	        	matchesDTO.setOperation("Update");
	        	adminService.processMatches(matchesDTO);
			}
		}else {
			paymentEntity.setAmount(amount);
			paymentEntity.setBankName("RewardzPlot Wallet");
			paymentEntity.setPaymentMode("Wallet");
			paymentEntity.setPaymentStatus("Failed");
			paymentEntity.setResponseMsg("Insufficent wallet balance. Please use another option to complete this payment.");
		}
		return paymentEntity;
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
        String ctx = "default";
        boolean isValideChecksum = false;
        System.out.println("RESULT : "+parameters.toString());
        try {
            isValideChecksum = validateCheckSum(parameters, paytmChecksum);
            if (isValideChecksum && parameters.containsKey("RESPCODE")) {
                if (parameters.get("RESPCODE").equals("01")) {
                    result = "Payment Successful";
                    ctx = "success";
                } else {
                    result = "Payment Failed";
                    ctx = "failed";
                }
            } else {
                result = "Checksum mismatched";
                ctx = "failed";
            }
        } catch (Exception e) {
            result = e.toString();
        }
        parameters.remove("CHECKSUMHASH");
        System.out.println("Result: "+result);
        //Adding payment details to DB
        String matchId = parameters.get("ORDERID").split("_")[1];
        String userId = parameters.get("ORDERID").split("_")[0];
        paymentService.addPaymentTransactionDetails(parameters,userId,matchId);
        if(matchId!=null && !matchId.isEmpty() && ctx.equals("success")) {
        	JoinedMatchesEntity entity = new JoinedMatchesEntity();
        	entity.setMatchId(Integer.parseInt(matchId));
        	entity.setUserId(userId);
        	entity.setFeeStatus("Paid");
        	entity.setSubmissionDate(new Date());
        	entity.setStatus(null);
        	matchService.joinAMatch(entity);
        	MatchesDTO matchesDTO = new MatchesDTO();
        	matchesDTO.setMatchId(Integer.parseInt(matchId));
        	matchesDTO.setOperation("Update");
        	adminService.processMatches(matchesDTO);
        }else if((matchId==null || matchId.isEmpty()) && ctx.equals("success")) {
        	WalletDTO walletDTO = new WalletDTO();
        	walletDTO.setOperation("Addition");
        	walletDTO.setAmount((int)Double.parseDouble(parameters.get("TXNAMOUNT")));
        	walletDTO.setUserId(userId);
        	adminService.updateWallet(walletDTO);
        }
        httpServletResponse.setStatus(302);
        String redirectUrl = "http://localhost/"+ctx+"?"+mapToQueryString(parameters);
        httpServletResponse.setHeader("Location", redirectUrl);
        //return "redirect:" + redirectUrl;
    }
	

	public String mapToQueryString(Map<String,String> queryString) {
		StringBuilder sb = new StringBuilder();
		  for(Map.Entry<String, String> e : queryString.entrySet()){
		      if(sb.length() > 0){
		          sb.append('&');
		      }
		      try {
				sb.append(URLEncoder.encode(e.getKey(), "UTF-8")).append('=').append(URLEncoder.encode(e.getValue(), "UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		  }
		return sb.toString();
	}
	
	
}
