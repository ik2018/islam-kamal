<?php 
error_reporting(0);
	session_start();
    include "connect.php";
    $stmt = $con->prepare("delete * from ordered_prod where userID = ? and state = 0");
    $stmt->execute(array($_SESSION['id']));
	session_unset();
	session_destroy();
	header('location:index.php');
	exit();