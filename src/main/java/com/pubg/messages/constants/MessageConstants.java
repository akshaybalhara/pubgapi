package com.pubg.messages.constants;

public interface MessageConstants {

	public static final String LOGIN_SUCCESS_CODE = "AUT_000";
	public static final String LOGIN_FAILURE_CODE = "AUT_001";
	public static final String LOGIN_FAILURE_MSG = "Invalid Credentials! Please try again.";
	
	public static final String SOMETHING_WENT_WRONG = "SMT_001";
	public static final String SOMETHING_WENT_WRONG_MSG = "Something went wrong!";
	public static final String INVALID_OLD_PASS_CODE = "INV_001";
	public static final String INVALID_OLD_PASS_MSG = "Invalid old password!";
	public static final String NO_USER_EXIST = "INV_002";
	public static final String NO_USER_EXIST_MSG = "No such user exist.";
	
	public static final String ADMIN_INSERT_MATCH_CODE = "AIM_001";
	public static final String ADMIN_INSERT_MATCH_MSG = "Match has been created successfully.";
	
	//Static values used in DB
	public static final String INACTIVE = "Inactive";
	public static final String ACTIVE = "Active";
	public static final String USER_ROLE = "User";
	public static final String PENDING = "Pending";
	public static final String SENT = "Sent";
	
	//HTML Templates
	public static final String ACTIVATE_ACCOUNT_TEMPLATE_BEGIN = "<div class=\"Body-wrap-1\" style=\"width:100%; margin: auto; background-color: rgba(94,94,94,0.1); padding-bottom: 5%;\">"
			+"<div class=\"Body-wrap-1a\" style=\"width:100%; height: auto; margin: auto; background-color: #0a0a0a;padding: 10px;text-align: center;\">\r\n" + 
			"        <img src=\"https://rewardzplot.com/wp-content/uploads/2020/07/rp-full-name-logo.png\" alt=\"Logo-rewardzplot\" style=\"width:40%; inline:block;\" >\r\n" + 
			"        <h2 style=\"font-family:'Roboto', sans-serif; color: #ffffff; width:50%; margin: auto; margin:2% auto;\">Account Activation</h2>\r\n" + 
			"      </div>"
			+"<div class=\"Body-wrap-1b\" style=\"width:100%; align-content: center;\r\n" + 
			"        align-items: center;  height: auto; margin: auto;\">\r\n" + 
			"        <p style=\"font-family:'Roboto', sans-serif; color: #5e5e5e; font-size: 15px; text-align:center; margin: 8%; line-height: 1.6;\">\r\n" + 
			"          Welcome to RewardzPlot, You can earn cash easily by your skills in many games.<br>\r\n" + 
			"\r\n" + 
			"          We organise many cool tournaments in which you earn big reward.<br>\r\n" + 
			"\r\n" + 
			"          Live stream your gameplay in just one click, directly from your phone.<br>\r\n" + 
			"\r\n" + 
			"          Invite your friends to join you on RewardzPlot, and start earning.<br>\r\n" + 
			"\r\n" + 
			"          Find everything on the tip of your fingers. Click-and-g0!<br>\r\n" + 
			"\r\n" + 
			"          Withdrawl your earnings much more faster (within some working Hours).<br>\r\n" + 
			"\r\n" + 
			"          Your contact information is sometimes used to contact you when necessary.<br><br>\r\n" + 
			"\r\n" + 
			"          (You can activate your Rewardzplot account By Clicking on Activate account Button.)\r\n" + 
			"        </p>\r\n" + 
			"          <div class=\"\" style=\"width:100%; height:auto; margin:auto;text-align:center;\">\r\n" + 
			"            <a href=\""+"http://3.128.4.163:8080/pubgroom-api/user-reg/verify-account/";
	
			public static final String ACTIVATE_ACCOUNT_TEMPLATE_END = "\"> Click here to verify your account </a>" + 
			"          </div>\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"      </div>\r\n" + 
			"\r\n" + 
			"    </div>"
			+"<div class=\"body-wrap-2\" style=\"width:100%; margin:0px auto;\">\r\n" + 
			"      <p style=\"font-family:'Roboto', sans-serif; color: #a0a0a0; font-size: 12px; text-align:center; margin: 5%; line-height: 1.3;\">We hope you enjoyed this email.\r\n" + 
			"         If you'd rather not receive future emails from Rewardz Plot, unsubscribe here.\r\n" + 
			"        rewardz Plot, Inc. is a subsidiary of rewardzplot.com, is a registered trademark of RewardzPlot.\r\n" + 
			"        This message was produced and distributed by Rewardzplot, Inc. and its affiliates.\r\n" + 
			"        © 2020, Rewardz Plot, Inc. or its affiliates. All rights reserved. Read our Privacy Policy.\r\n" + 
			"      </p>\r\n" + 
			"    </div>";
	
	
	
	
}

