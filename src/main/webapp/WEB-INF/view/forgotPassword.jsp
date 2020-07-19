<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Reset Password | RewardzPlot</title>
  </head>
  <body>
    <h1>Change Password</h1>
		<form id ="resetPasswordForm">
			<input type="hidden" id="userId" name="userId" value="${userId}" />
			<input type="hidden" id="pin" name="pin" value="${pin}"/>
			<label for="newPassword">New Password:</label>
			<input type="password" id="newPassword" name="newPassword" title="New password" />
			
			<label for="confirmPassword">Confirm Password:</label>
			<input type="password" id="confirmPassword" name="confirmPassword" title="Confirm new password" />
			
			<p class="form-actions">
			<input type="submit" value="Change Password" title="Change password" />
			</p>
		</form>
		<div id="messageTxt">
		
		</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript">
var ctx = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
$(document).ready(function() {
	$("#messageTxt").hide();
   /*  Submit form using Ajax */
   $("#resetPasswordForm").submit(function(e) {
      //Prevent default submission of form
      e.preventDefault();
      fire_ajax_submit();
   });
});

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
            	$("#resetPasswordForm").hide();
            	$("#messageTxt").show();
            	$("#messageTxt").html("<h2>"+data.statusMessage+"</h2>");
            }else{
            	$("#resetPasswordForm").hide();
            	$("#messageTxt").show();
            	$("#messageTxt").html("<h2>Something went wrong!</h2>");
            }
        },
        error: function (e) {
            console.log("ERROR : ", e);
            $("#resetPasswordForm").hide();
        	$("#messageTxt").show();
        	$("#messageTxt").html("<h2>Something went wrong!</h2>");
        }
    });
}

</script>
  </body>
</html>