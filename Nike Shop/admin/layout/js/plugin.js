$(document).ready(function () {

	//add * for required fields
	$("input").each(function () {
		if($(this).attr("required")=== "required") {
			$(this).after("<span class='astrick'>*</span>");
		}
	});

	$('[data-toggle=confirmation]').confirmation({
  			rootSelector: '[data-toggle=confirmation]',
  			title: 'are you sure delete?',
  			popout: 'true',
  			btnOkClass: 'btn-xs btn-danger',
  			singleton: 'true',
	});
	


});