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
			 var t1 = document.createTextNode(ob.version);
			 td1.appendChild(t1);
			 row1.appendChild(td1);
			 
			 var td2 = document.createElement('TD');
			 var t2 = document.createTextNode(new Date(Number(ob.dateOfEdit)).toLocaleString());
			 td2.appendChild(t2);
			 row1.appendChild(td2);
			 

			 var td3 = document.createElement('TD');
			 var t3 = document.createTextNode(ob.editor);
			 td3.appendChild(t3);
			 row1.appendChild(td3);
			 
			 var td4 = document.createElement('TD');
			 var t4 = document.createTextNode(ob.postTxt);
			 td4.appendChild(t4);
			 row1.appendChild(td4);
			 
			 var td5 = document.createElement('TD');
			 var t5 = document.createElement('A');
			 var url = '/post/' +$('#UserPostsKey').html()+ '?version=' +ob.version;
			 t5.setAttribute("href", url);
			 var view = document.createTextNode("view");
			 t5.appendChild(view);
			 td5.appendChild(t5);
			 row1.appendChild(td5);
			 
			 document.getElementById('tbdy').appendChild(row1);
		 
			 
        });
		
		
	});
	
});
