$(document).ready(function() {
	$("html").niceScroll({
		cursorcolor:"#3498db",
		cursorwidth: "10",
		cursorborderradius:"0"
	});
    
    var httpReq;
    
    function getInfo(barName){
        
        httpReq = new XMLHttpRequest();
        httpReq.onreadystatechange = function() {
            if (this.readyState == 0 ||this.readyState == 1||this.readyState == 2||this.readyState == 3 ){
                document.getElementById("content_prod").innerHTML = "<h3 class='alert alert-info'>جارى التحميل ...</h3>";
            }
            if (this.readyState == 4 && this.status == 200) {
             document.getElementById("content_prod").innerHTML = httpReq.responseText;
            }
          };
        
        httpReq.open("POST", "info.php", true);
        httpReq.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httpReq.send("do="+barName);
    }
    
    getInfo("all");

	$(".navbar-default .navbar-nav > li > a").on("click", function () {
        if($(this).data("target")=="all")
        {
        	getInfo("all");
        }else
        {
            getInfo($(this).data("target")).fadeIn(3000);
        }
    });
    var count= 0;
    var price = 0;
    
    $(".products .pro #A2C").on("click", function () {
        
        var prodID = $(this).data("target");
        $.post("ajaxCard.php", {prodID: prodID},function(data){
            $(".card .tp").text("Total Products: "+data);
        });
        //$(".card .tp").text("Total Products: "+count);
        //$(".card .tpr").text("Total Price: "+price+" EGP");
    });

});