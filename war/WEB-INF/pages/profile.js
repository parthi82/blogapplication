
 
 function getUserDetails() {
    
	/*
    for (var prop in obj) {
	  if( obj.hasOwnProperty( prop ) ) {
		console.log("o." + prop + " = " + obj[prop]);
	  } 
	} */
	
	var request = $.ajax({
		   
			type: 'GET',
			url: '/getUserDetails'
							
	});
	
	request.done(function(data){
		
		if(data != null){
			
			$('.navbar-brand').html(data.name);
			
		}
		
				
	});						 
}
 
 function createUserPost() {
		
		var txt = $('#postTxt').val();
		console.log(txt);
		console.log(JSON.stringify(txt));
		
		/*var request = $.ajax({
			
			type: 'POST',
			url:  '/createUserPost',
			data: $("#postfield").serialize()
			
			
		}); */
		
		var  request = $.ajax({
			
						type: "POST",
						url: "/createUserPost",
						data: $("#postfield").serialize()  
						
		});	
		
		request.done(function(data) {
			
			 console.log(data)
			 data.forEach(function(obj){
				 $('#postcontainer').append('<div class="panel-group" id="accordion">' + '<div>');
				 $('#accordion').append('<div class="panel panel-default"><div class="panel-heading" id="headingOne"><h4 class="panel-title"><a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">' + obj.author +'</a></h4></div>');
			 });
			 
			 
		});
	}
	
 function getUserPosts() {
	 
	 var  request = $.ajax({
			
						type: "GET",
						url: "/getUserPosts"
						
		});	
		
		request.done(function(data) {
			console.log(data)
			
			data.forEach(function(obj){
				 $('#postcontainer').append('<div class="panel-group" id="accordion">' + '<div>');
				 $('#accordion').append('<div class="panel panel-default"> <div class="panel-heading" id="headingOne"><h4 class="panel-title"><a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">'+ obj.author + '</a></h4></div>' + '<div id="collapseOne" class="panel-collapse collapse in"><div class="panel-body">' + obj.postTxt + '</div></div></div>');
				  //$('#accordion').append();
			 });
			
		});
	 
 }


$(document).ready(function(){ 
	    getUserPosts();
        getUserDetails();		  
		$("#home").tab('show');
});