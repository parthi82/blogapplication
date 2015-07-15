$(document).ready(function(){
	
   $postform = $('#postform');
   $title = $('#title');
   $postTxt = $('#postTxt');
   $message = $('#message');
   $title.hide();
   $postTxt.hide();
   $message.hide();
   $createbutton = $('#createbutton');
   $cancel = $('#cancel');
   $createbutton.hide();
   $cancel.hide();
   $createpost = $('#createpost');
  
   
   
   $createpost.click(function(){
	   
	       $title.show();
	       $postTxt.show();
	       $createbutton.show();
	       $cancel.show();
	       var button_lock = false;
		   $createbutton.click(function(){
			  
			   if($title.val() && $postTxt.val()) {

				   if(!button_lock){
					   button_lock = true;
				   document.getElementById("createbutton").disabled = true;
				   var request = $.ajax({
						method: "POST",
						url: "/post",
						data: {title: $title.val(), postTxt: $postTxt.val()}
					});
					request.done(function(response){
						 
						 $title.val('');
						 $postTxt.val('');
						 $title.hide();
						 $postTxt.hide();
						 $createbutton.hide();
						 $cancel.hide();
						 
						 if(response !== "invalid") {
							 var location = "/post/" + response;
							 window.location = location;
						 }
						 else {
							 button_lock = false;
							 document.getElementById("createbutton").disabled = false;
							 $message.html("Oops the post wasn't created , try again!");
							 $message.show();
						 }
						 
						 
					});
					request.fail(function() {
						 $message.html("Oops the post wasn't created , try again!");
						 $message.show();
						 button_lock = false;
						 document.getElementById("createbutton").disabled = false;
					});
			   }
			   }
		   });
		   
		   $cancel.click(function(){
			     $title.val('');
				 $postTxt.val('');
				 $title.hide();
				 $postTxt.hide();
				 $createbutton.hide();
				 $cancel.hide();
		   });
   });
	
});