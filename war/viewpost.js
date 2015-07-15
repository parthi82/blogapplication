$(document).ready(function() {
   
    console.log('running js');
    var $creationdate = $('#creationdate');
    var $editeddate = $('#editeddate');
    var $postTxt = $('#postTxt');
    var $edit = $('#edit');	
    var $cancel = $('#cancel');
    var $save = $('#save');  
    var $txtarea = $('#txtarea');
    $cancel.hide();
    $save.hide();
    $txtarea.hide();
    
    var d1 = new Date(Number($creationdate.html()));
    var d2 = new Date(Number($editeddate.html()));
    $creationdate.html(d1.toLocaleString());
    $editeddate.html(d2.toLocaleString());
    
    $('#back').click(function() {
	    window.history.back();
	});
    
	$edit.click(function() {
		
		$postTxt.hide();
		$txtarea.show();
		$txtarea.val($postTxt.html());
		$edit.hide();
		$cancel.show();
		$save.show();
		var button_lock = false;
		$save.click(function() {
			document.getElementById("save").disabled = true;
			if(!button_lock) {
				button_lock = true;
				var req =$.ajax({
					method: "PUT",
					url: "/post",
					data: {postTxt: $txtarea.val(), id: $('#postid').html()}
				});
				req.done(function(){
					 window.location='/post/'+ $('#postid').html();
					 document.getElementById("save").disabled = true;
					 button_lock = false;
				});
				
			}
			
		});
		
		$cancel.click(function(){
			 document.getElementById("save").disabled = false;
			$edit.show();
			$cancel.hide();
			$save.hide();
			$txtarea.val('');
			$txtarea.hide();
			$postTxt.show();
		});
		
	});
		
});
	