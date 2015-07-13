$(document).ready(function(){
	
	var req =$.ajax({
		method: "POST",
		url: "/edithistory",
		data: {UserPostsKey: $('#UserPostsKey').html()}
	});
	req.done(function(data){
		 
		data.forEach(function(ob){
	    	  
			 var row1 =  document.createElement('TR');
			 var td1 = document.createElement('TD');
			 var t1 = document.createTextNode(ob.date);
			 td1.appendChild(t1);
			 row1.appendChild(td1);
			 
			 var td2 = document.createElement('TD');
			 var t2 = document.createTextNode(ob.editor);
			 td2.appendChild(t2);
			 row1.appendChild(td2);
			 

			 var td3 = document.createElement('TD');
			 var t3 = document.createTextNode(ob.txtBeforeEdit);
			 td3.appendChild(t3);
			 row1.appendChild(td3);
			 
			 var td4 = document.createElement('TD');
			 var t4 = document.createTextNode(ob.txtAfterEdit);
			 td4.appendChild(t4);
			 row1.appendChild(td4);
			 
			 document.getElementById('tbdy').appendChild(row1);
		 
			 
        });
		
		
	});
	
});
