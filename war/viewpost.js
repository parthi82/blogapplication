$(document).ready(function() {
    console.log('running js');
    var $edit = $('#edit');	
    var $cancel = $('#cancel');
    var $save = $('#save');
    var $postTxt = $('#postTxt');
    $cancel.hide();
    $save.hide();
    
    $('#back').click(function() {
	    window.history.back();
	});
    
	$edit.click(function() {
		$postTxt.attr("contenteditable","true");
		$edit.hide();
		$cancel.show();
		$save.show();
		$save.click(function() {
			var req =$.ajax({
				method: "PUT",
				url: "/post",
				data: {postTxt: $postTxt.html(), id: $('#postid').html()}
			});
			req.done(function(){
				 location.reload();
			});
		});
		
		$cancel.click(function(){
			$edit.show();
			$cancel.hide();
			$save.hide();
		});
		
	});
		
});
	