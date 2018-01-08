<?php 

error_reporting(0);
	session_start();
	if(isset($_SESSION['username'])){
		$pageTitle = "Dashboard";
		include 'init.php';
		include $tml . 'footer.php';
	}
	else{
		header('location:index.php');
		exit();
	}

	
?>

