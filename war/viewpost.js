$(document).ready(function() {
    console.log('running js');
    var $edit = $('#edit');	
    var $cancel = $('#cancel');
    var $save = $('#save');
    var $postTxt = $('#postTxt');
    var $txtarea = $('#txtarea');
    $cancel.hide();
    $save.hide();
    $txtarea.hide();
    
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
		$save.click(function() {
			var req =$.ajax({
				method: "PUT",
				url: "/post",
				data: {postTxt: $txtarea.val(), id: $('#postid').html()}
			});
			req.done(function(){
				 location.reload();
			});
		});
		
		$cancel.click(function(){
			$edit.show();
			$cancel.hide();
			$save.hide();
			$txtarea.val('');
			$txtarea.hide();
			$postTxt.show();
		});
		
	});
		
});
	