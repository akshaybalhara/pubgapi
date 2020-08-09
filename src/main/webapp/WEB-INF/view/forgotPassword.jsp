<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Reset Password | RewardzPlot</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <style>
	    .reset-password {margin: 10px;}
		input[type=password], select {width: 100%; padding: 6px 6px; margin: 5px 0; display: inline-block; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box;}
		input[type=submit] {background-color: #4CAF50; color: white; padding: 14px 20px; border: none; border-radius: 4px; cursor: pointer;}
		input[type=submit]:hover {background-color: #45a049;}
		input[type=submit]:disabled { background: #dddddd;}
		div.polaroid {padding: 20px;border: 1px solid #BFBFBF;background-color: white;box-shadow: 0px 0px 7px #aaaaaa;}
		.form-actions{text-align: center;padding: 6px;}
		.overlay {position: fixed;top: 0;bottom: 0;left: 0;right: 0;background: rgba(0, 0, 0, 0.7);transition: opacity 500ms;visibility: visible;opacity: 1;display:none;}
		.popup {margin: 70px auto; padding: 20px; background: #fff; border-radius: 5px; width: 30%; position: relative; transition: all 5s ease-in-out;}
		.loader { border: 5px solid #f3f3f3; border-radius: 50%; border-top: 5px solid #3498db; margin-top: 50%; margin-left: 30%;
		    width: 30px; height: 30px; -webkit-animation: spin 2s linear infinite; animation: spin 2s linear infinite; z-index: 10;
		    background: transparent; padding: 0;
		}
		.child-container {width: 100%; margin: 0 auto; padding: 18px 0; background: #222; }
		.header { width: 100%; text-align: center;  margin: 0 auto; font-family: 'Roboto', sans-serif; font-size: 12px;}
		.header h2 { width: 100%; text-align: center; color: #fff; font-size: 16px;}
		.header img {width: 50%;}
		
		/* Safari */
		@-webkit-keyframes spin {
		  0% { -webkit-transform: rotate(0deg); }
		  100% { -webkit-transform: rotate(360deg); }
		}
		
		@keyframes spin {
		  0% { transform: rotate(0deg); }
		  100% { transform: rotate(360deg); }
		}
    </style>
  </head>
  <body style="width: 100%; margin: 0 auto;">
     <div class="main-container">
      <div class="child-container">
      	<div class="header">
      	<img src="https://rewardzplot.com/wp-content/uploads/2020/07/rp-full-name-logo.png" alt="Logo-rewardzplot" >
        <h2>Reset Password</h2>
      	</div>
      </div>
      <div class="reset-password polaroid">
      	<form id ="resetPasswordForm" >
			<input type="hidden" id="userId" name="userId" value="${userId}" />
			<input type="hidden" id="pin" name="pin" value="${pin}"/>
			<label for="newPassword">New Password:</label>
			<input type="password" id="newPassword" name="newPassword" title="New password" />
			
			<label for="confirmPassword">Confirm Password:</label>
			<input type="password" id="confirmPassword" name="confirmPassword" title="Confirm new password" />
			
			<div class="form-actions">
			<input id="submitBtn" type="submit" value="Change Password" title="Change password" />
			</div>
		</form>
		<div id="messageTxt" style="text-align: center;"></div>
      </div>
		<div class="overlay">
		      <div id="resetPassDiv"></div>
		  </div>
	</div>


<script type="text/javascript">
var ctx = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
$(document).ready(function() {
	$("#messageTxt").hide();
	$("#submitBtn").prop("disabled",true);
	$("#confirmPassword").keyup(checkPasswordMatch);
   /*  Submit form using Ajax */
   $("#resetPasswordForm").submit(function(e) {
      //Prevent default submission of form
      e.preventDefault();
      $("#resetPassDiv").addClass("loader");
      $("div.overlay").css("display","block");
      fire_ajax_submit();
   });
});

function checkPasswordMatch() {
    var password = $("#newPassword").val();
    var confirmPassword = $("#confirmPassword").val();
    if ((password.length == 0 || confirmPassword.length == 0) || password != confirmPassword){
    	$("#messageTxt").show();
    	$("#messageTxt").html("Passwords does not match!");
    	$("#submitBtn").prop("disabled",true);
    }else if(password.length < 8 || confirmPassword.length < 8){
    	$("#messageTxt").show();
    	$("#messageTxt").html("Password must have atleast 8 characters.!");
    	$("#submitBtn").prop("disabled",true);
    }else{
    	$("#messageTxt").show();
    	$("#messageTxt").html("Passwords matched!");
    	$("#submitBtn").removeAttr("disabled");
    }    
}

function fire_ajax_submit(){
	var formElement = $("#resetPasswordForm");
	var requestData = formElement.serialize();
	
	console.log(requestData);
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: ctx+"/user-auth/updatePassword",
        data: requestData,
        cache: false,
        timeout: 600000,
        success: function (data) {
            console.log("SUCCESS : ", data);
            if(data.succeeded){
            	$("#resetPassDiv").removeClass("loader");
            	$("div.overlay").css("display","none");
            	$("#resetPasswordForm").hide();
            	$("#messageTxt").show();
            	$("#messageTxt").html("<h2 style='font-size: 12px;color: green;'>"+data.statusMessage+"</h2>");
            }else{
            	$("div.overlay").css("display","none");
            	$("#resetPassDiv").removeClass("loader");
            	$("#resetPasswordForm").hide();
            	$("#messageTxt").show();
            	$("#messageTxt").html("<h2 style='font-size: 12px;color: red;'>Something went wrong!</h2>");
            }
        },
        error: function (e) {
            console.log("ERROR : ", e);
            $("div.overlay").css("display","none");
            $("#resetPassDiv").removeClass("loader");
            $("#resetPasswordForm").hide();
        	$("#messageTxt").show();
        	$("#messageTxt").html("<h2 style='font-size: 12px;color: red;'>Something went wrong!</h2>");
        }
    });
}

</script>
  </body>
</html>