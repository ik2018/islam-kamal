<!-- header -->
<?php
	session_start();
	$noNavbar = '';
	$pageTitle = "Login";
include "connect.php";

include "includes/templates/header.php";
	if(isset($_SESSION['username']))
	{
		header('location:index.php');
	}
	 if($_SERVER['REQUEST_METHOD']=='POST'){
	 	$user = $_POST['user_name'];
	 	$pass = $_POST['password'];
	 	$hasedpass = sha1($pass);
	 	$stmt = $con->prepare("select userID, Username, password from user where Username =? and password = ? and GroupID = 0" );
	 	$stmt->execute(array($user, $hasedpass));
	 	$row = $stmt->fetch();
	 	print_r($row);
	 	if($stmt->rowCount()>0){
	 		$_SESSION['username'] = $user;
	 		$_SESSION['id'] = $row['userID'];
	 		header('location:index.php');
	 	}
	 }
 ?>

<!-- content -->
<form class="form center-block login_form" action="<?php echo $_SERVER['PHP_SELF'] ?>" method="POST">
	<h2 class="text-center"> Login</h2>
	<input class="form-control form-group" type="text" name="user_name" placeholder="Username" autocomplete="off">
	<input class="form-control form-group" type="password" name="password" placeholder="password" autocomplete="new-password">
	<input class="btn btn-primary btn-block form-group" type="submit" value="login">
	
</form>
<form class="form center-block login_form" action="signup.php" method="POST">
	<input class="btn btn-primary btn-block form-group" type="submit" value="Sign Up">
</form>

<!-- footer -->
<?php include 'includes/templates/footer.php'; ?>