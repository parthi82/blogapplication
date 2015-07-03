       
       function validateName() {
       
         var val = document.getElementById("name").value; 
		 var reg = /^[A-Za-z]{3,}$/
         if(reg.test(val)) {
		   
			document.getElementById('state1').className = "form-group has-success has-feedback";
			document.getElementById('glyp1').className = "glyphicon glyphicon-ok form-control-feedback";
			document.getElementById('helpblock1').innerHTML = "";
            validName = true; 
			
         } 
         else {
		
			 document.getElementById('state1').className += " has-error has-feedback";
			 document.getElementById('glyp1').className = "glyphicon glyphicon-remove form-control-feedback";
             document.getElementById('helpblock1').innerHTML = "Invalid input";
			 validName = false;
			
         }      

       }      

       function validateEmail(data) {
		   
          var val = document.getElementById("email").value;
          var reg = /\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+/;
		  
          if(reg.test(val)){
            
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
       

       function validatePhone(data) {
            
           var val = document.getElementById("phone").value;
		   var reg = /^[0-9]{10,}/
           if(reg.test(val)) {
                
				document.getElementById('state3').className = "form-group has-success has-feedback";
				document.getElementById('glyp3').className = "glyphicon glyphicon-ok form-control-feedback";
			    document.getElementById('helpblock3').innerHTML = "";
              
           }
           else {
                 
				 document.getElementById('state3').className += " has-error has-feedback";
				 document.getElementById('glyp3').className = "glyphicon glyphicon-remove form-control-feedback";
                 document.getElementById('helpblock3').innerHTML = "Invalid input";
				 
           }
            
       }
	   
	   function validatePassword(data) {
            
           var val = document.getElementById("password").value;
		   var reg = /^[a-z0-9]{5,}/
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
		   
		   $("#signup").submit(function(event) {
			   
			    var $i = $('#Info');
				var val1 = document.getElementById("name").value; 
			    var reg1 = /^[A-Za-z]{3,}$/
				var val2 = document.getElementById("email").value;
	            var reg2 = /\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+/;
				var val3 = document.getElementById("phone").value;
			    var reg3 = /^[0-9]{10,}/   
			    var val4 = document.getElementById("password").value;
				var reg4 = /^[a-z0-9]{5,}/
				var cnd1, cnd2, cnd3;
				cnd1 = reg1.test(val1);
				cnd2 = reg2.test(val2);
				cnd3 = reg3.test(val3);
				cnd4 = reg4.test(val4);
			    if(!(cnd1 && cnd2 && cnd3 && cnd4)) {
					   $i.addClass("alert alert-danger");
					   $i.html("Invalid input!");
					   event.preventDefault();
			    }
		   });
		   
	});
	   