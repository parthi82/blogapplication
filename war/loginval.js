
function validatelogin(data) {
		   
          var val1 = document.getElementById("login").value;
          console.log("val1 : " + val1);
          var reg1 = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
		  var reg2 = /^\w{4,39}$/;
          if(reg1.test(val1) || reg2.test(val1)){
            
			document.getElementById('state2').className = "form-group has-success has-feedback";
			document.getElementById('glyp2').className = "glyphicon glyphicon-ok form-control-feedback";
			document.getElementById('helpblock2').innerHTML = "";

          }
          else {
		    
			document.getElementById('state2').className += " has-error has-feedback";
			document.getElementById('glyp2').className = "glyphicon glyphicon-remove form-control-feedback";
            document.getElementById('helpblock2').innerHTML = "Invalid input";
			
          }

}

function validatePassword(data) {
            
           var val = document.getElementById("password").value;
		   var reg = /^[0-9a-zA-Z]{5,39}$/;
           if(reg.test(val)) {
                
				document.getElementById('state4').className = "form-group has-success has-feedback";
				document.getElementById('glyp4').className = "glyphicon glyphicon-ok form-control-feedback";
			    document.getElementById('helpblock4').innerHTML = "";
              
           }
           else {
                 
				 document.getElementById('state4').className += " has-error has-feedback";
				 document.getElementById('glyp4').className = "glyphicon glyphicon-remove form-control-feedback";
                 document.getElementById('helpblock4').innerHTML = "Invalid input";
				 
           }
            
       
}

$(document).ready(function(){
	
		$("#login").submit(function(event) {
				    
				    var $i = $('#Info');
					var val1 = document.getElementById("password").value; 
					var reg1 = /^[0-9a-zA-Z]{5,39}$/;
					var val2 = document.getElementById("login").value;
		            var reg2 = /\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+/;
		            var reg3 = /^\w{4,39}$/;
					var cnd1, cnd2;
					cnd1 = reg1.test(val1);
					cnd2 = reg2.test(val2) || reg3.test(val2) ;
					
				   if(!(cnd1 && cnd2)) {
					   $i.addClass("alert alert-danger")
					   $i.html("Invalid input!");
					   event.preventDefault();
					   
				   }
				   
		});
		
});