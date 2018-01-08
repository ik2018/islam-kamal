<?php 

error_reporting(0);
	session_start();

	$pageTitle = "Adminstration";

	include 'init.php';
	if(isset($_SESSION['username'])){
		

		$do = isset($_GET['do'])? $_GET['do']:'manage';
		# start manage page ... 
		if ($do == 'manage')
		{?>
			<div class="container">
				<h2 class="text-center">Add New Member</h2>
				<div class="table-responsive">
				<a class="btn btn-primary" href="?do=add"><i class="fa fa-plus"></i> New Memeber</a>
					<table class="main-table text-center table table-bordered">
						<tr>
							<td>#ID</td>
							<td>Username</td>
							<td><i class="fa fa-envelope"></i> Email</td>
							<td>Fullname</td>
							<td><i class="fa fa-clock-o" aria-hidden="true"></i> Register Date</td>
							<td>Control</td>
						</tr>
					<?php

					/* retrive User data and display*/
						$stmt = $con->prepare("select * from user where groupID != 1");
						$stmt->execute();
						$rows = $stmt->fetchAll();

						foreach ($rows as $row) {
							echo "<tr>";
							echo "<td>". $row['userID'] ."</td>";
 							echo "<td>". $row['Username'] ."</td>";
 							echo "<td>". $row['Email'] ."</td>";
 							echo "<td>". $row['Fullname'] ."</td>";
 							echo "<td> null </td>";
 							echo "<td>
									<a class='btn btn-success' href='?do=edit&uid=".$row['userID']."'><i class='fa fa-pencil-square-o' aria-hidden='true'></i> Edit</a>
									<a class='btn btn-danger delete-btn' data-toggle='confirmation' href='members.php?do=Delete&uid=". $row['userID'] ."'><i class='fa fa-trash-o' aria-hidden='true'></i> Delete</a>
								</td>";
							echo "</tr>";
						}

					?>
					</table>
					

				</div>

			</div>
			
		<?php
	}

		# start add page ...
		elseif ($do == 'add'){
			?>

			 <div class="container">
				<h2 class="text-center">Add New Member</h2>
				<form class="form-horizontal " action="?do=insert" method="POST">
					<div class="form-group">
						<label class="col-sm-offset-2 col-sm-2 control-label">Username</label>
						<div class="col-sm-6">
							<input class="form-control" type="text" name="user_name" required="required" autocomplete="off" value="<?php echo $row['Username'] ?>">
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-offset-2 col-sm-2 control-label">Full Name</label>
						<div class="col-sm-6">
							<input class="form-control" type="text" name="fullname" required="required" autocomplete="off" value="<?php echo $row['Fullname'] ?>">
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-offset-2 col-sm-2 control-label" >Password</label>
						<div class="col-sm-6">
							<input class="form-control" type="password" name="password" autocomplete="new-password" required="required">
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-offset-2 col-sm-2 control-label">Email</label>
						<div class="col-sm-6">
							<input class="form-control" type="email" name="email" value="<?php echo $row['Email'] ?>" required="required">
						</div>
					</div>

					<div class="form-group">
						<div class="col-sm-offset-4 col-sm-6">
							<input class="btn btn-primary btn-block" type="submit" value="Add New Memebr">
						</div>
					</div>
				</form>
			</div>

		 	<?php

		}
		# end add page ...

		# start insert page ...
		elseif ($do == 'insert') {
			echo '<h2 class="text-center">insert New Member</h2>';
				echo "<div class='container'>";

				if($_SERVER['REQUEST_METHOD']=='POST'){

					$user_name = $_POST['user_name'];
					$ID = $_POST['ID'];
					$email = $_POST['email'];
					$fullname = $_POST['fullname'];
					$pass = $_POST['password'];
					$hpass = sha1($pass);
					$errors = array();
					if(empty($user_name)){
						$errors[] = "Username can't be empty";
					}if(strlen($user_name)<3){
						$errors[] = "<strong>Username</strong> must be large than <strong>3</strong> characters";
					}if(strlen($pass)<3 || strlen($pass)>20){
						$errors[] = "<strong>Password</strong> must be at least between <strong>3 to 20</strong> characters as maximum";
					}if(empty($email)) {
						$errors[] = "<strong>Email</strong> can't be empty";

					}if(empty($fullname)) {
						$errors[] = "<strong>Fullname</strong> can't be empty";
					}

					foreach ($errors as $value) {
						echo "<div class='alert alert-danger'>". $value  . "</div>";
					}

					if(empty($errors)){

						if(checkItem('Username', "user", $user_name)>0){
							echo "<div class='alert alert-danger'>This Username Is Already Exist!<br> try anather Username</div>";
							redirectHome("ERROR !",3);


						}else{
							$stmt = $con->prepare("insert into user(Username, Fullname, Email, password) values(:zuser,:zfull,:zemail,:zpass)");
							$stmt->execute(array(
								'zuser' => $user_name,
							 	'zfull' =>$fullname,
							 	'zemail' => $email,
							 	'zpass' => $hpass
						 	));
							echo "<div class='alert alert-info'>". $stmt->rowCount() ." row inserted. </div>";

						}
						
					}
					
				}else{
					redirectHome("You can't access this page!", 5);
				}

				echo "</div>";
			}



		# end insert page ...
		

		# start edit page ...
		elseif ($do == 'edit') {

			$userid =(isset($_GET['uid'])&&is_numeric($_GET['uid']))? intval($_GET['uid']): 0 ;
			$stmt = $con->prepare("select * from user where userID =? limit 1");

		 	$stmt->execute(array("$userid"));
		 	$row = $stmt->fetch();
		 	$count = $stmt->rowCount();
		 	if($count > 0){?>

			 <div class="container">
				<h2 class="text-center">Edit Profile</h2>
				<form class="form-horizontal " action="?do=update" method="POST">
					<input type="hidden" value="<?php echo $row['password'] ?>=" name="oldpass">
					<input type="hidden" value="<?php echo $row['userID'] ?>=" name="ID">
					<div class="form-group">
						<label class="col-sm-offset-2 col-sm-2 control-label">Username</label>
						<div class="col-sm-6">
							<input class="form-control" type="text" name="user_name" required="required" autocomplete="off" value="<?php echo $row['Username'] ?>">
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-offset-2 col-sm-2 control-label">Full Name</label>
						<div class="col-sm-6">
							<input class="form-control" type="text" name="fullname" required="required" autocomplete="off" value="<?php echo $row['Fullname'] ?>">
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-offset-2 col-sm-2 control-label" >Password</label>
						<div class="col-sm-6">
							<input class="form-control" type="password" name="newpass" autocomplete="new-password">
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-offset-2 col-sm-2 control-label">Email</label>
						<div class="col-sm-6">
							<input class="form-control" type="email" name="email" value="<?php echo $row['Email'] ?>" required="required">
						</div>
					</div>

					<div class="form-group">
						<div class="col-sm-offset-4 col-sm-6">
							<input class="btn btn-primary btn-block" type="submit" value="save">
						</div>
					</div>
				</form>
			</div>

		 	<?php

		 	}else{

		 		redirectHome("You can't access this page!", 5);

		 	}
		 	# end edit page ...


		 	# start update page ...
			}elseif ($do == 'update') {
				echo '<h2 class="text-center">update info</h2>';
				echo "<div class='container'>";
				

				if($_SERVER['REQUEST_METHOD']=='POST'){
					$ID = $_POST['ID'];
					$user_name = $_POST['user_name'];
					$email = $_POST['email'];
					$fullname = $_POST['fullname'];
					$pass = $_POST['newpass'];
					$errors = array();
					if(empty($user_name)){
						$errors[] = "Username can't be empty";
					}if(empty($email)) {
						$errors[] = "Email can't be empty";

					}if(empty($fullname)) {
						$errors[] = "Fullname can't be empty";
					}
					if(empty($pass)) {
						$errors[] = "password can't be empty";
					}else{
						$pass = sha1($pass);
					}

					foreach ($errors as $value) {
						echo "<div class='alert alert-danger'>". $value  . "</div>";
					}

					if(empty($errors)){
						$stmt = $con->prepare("update user set Username = ?, Fullname = ?, Email = ?, password= ? where userID = ?");
						$stmt->execute(array($user_name, $fullname, $email, $pass ,$ID));
						redirectHome("<div class='alert alert-info'>". $stmt->rowCount() ." row updated. </div>",'back');

					}
					
				}else{
						redirectHome("You can't access this page!", 5);
					}

				echo "</div>";
			}
			elseif ($do == 'Delete') {
				$userid =(isset($_GET['uid'])&&is_numeric($_GET['uid']))? intval($_GET['uid']): 0 ;

				echo "$userid";
				$stmt = $con->prepare("select * from user where userID =? limit 1");

			 	$stmt->execute(array("$userid"));
			 	$count = $stmt->rowCount();
			 	if($count > 0){
			 		$stmt = $con->prepare("delete from user where userID =?");

			 		$stmt->execute(array("$userid"));
			 	}
				echo "<div class='container text-center'>
					<h2>Delete Member</h2>";
					
					redirectHome("<div class='alert alert-primary'>".$stmt->rowCount()." Member deleted</div>",'back');
				echo "</div>";
			}


		else{
			header("location:?do=manage");
		}


		include $tml . 'footer.php';
	}else{
		redirectHome("<div class'alert alert-danger'>You can't access this page!</div>");
	}

	
?>