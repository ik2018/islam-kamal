<?php

	//set page title 
	function getTitle(){
		global $pageTitle;
		if(isset($pageTitle))
		{
			echo $pageTitle;
		}else{
			echo 'Default';
		}
	}


	// redirect function version 2.0
	// redirect to homepage when an error accours 
	//theMsg
	function redirectHome ($theMsg, $url=null, $seconds = 3) {
		if($url === null)
		{
			$url='index.php';
			$link = 'Dashboard';
		}elseif($url== 'back'){

			if(isset($_SERVER['HTTP_REFERER'])&&$_SERVER['HTTP_REFERER']!==''){
				$url = $_SERVER['HTTP_REFERER'];
			
			}else{

				$url = 'index.php';
				$link = 'Dashboard';
			}
		}else{
			$link = $url;
		}
		echo $theMsg;
		echo "<div class='alert alert-info'>You Will Direct to <strong>$link</strong> Direct In $seconds s...</div>";
		echo "</div>";
		header("refresh:$seconds;url=$url");
		exit();
	}


	#check item function version 1.0
	function checkItem ($select , $from ,$value) {
		global $con;
		$stmt = $con->prepare("select $select from $from where $select = ?");
		$stmt->execute(array($value));
		$count = $stmt->rowCount();
		return $count;
	}



