<?php 
	session_start();

	$pageTitle = "Adminstration";

	include 'init.php';
	if(isset($_SESSION['username'])){
		

		$do = isset($_GET['do'])? $_GET['do']:'manage';
		# start manage page ... 
		if ($do == 'manage')
		{?>
			<div class="container">
				<h2 class="text-center">Products Managment</h2>
				 <a class="btn btn-primary" style="margin-left:200;" href="?do=add"><i class="fa fa-plus"></i> New Product</a> <br/>

				<div class="table-responsive">
					<table class="main-table text-center table table-bordered">
						<tr>
							<td>#ID</td>
							<td>Product Name</td>
							<td>Price</td>
							<td></i>Quantity</td>
							<td></i>Describe</td>
							<td></i>Category ID</td>
							<td>Control</td>
						</tr>
					<?php


					/* retrive User data and display*/
						$stmt = $con->prepare("select * from product");
						$stmt->execute();
						$rows = $stmt->fetchAll();

						$stmt2 = $con->prepare("select * from category");
						$stmt2->execute();
						$rows2 = $stmt2->fetchAll();
						foreach ($rows as $row) {
							echo "<tr>";
							echo "<td>". $row['prodID'] ."</td>";
							echo "<td>". $row['prodName'] ."</td>";
 							echo "<td>". $row['prodPrice'] ."</td>";
 							echo "<td>". $row['prodQuantity'] ."</td>";
 							echo "<td>". $row['prodDescription'] ."</td>";
 							foreach ($rows2 as $row2){
 								if($row['catID']===$row2['catID']){
 									echo "<td>". $row2['catName'] ."</td>";
 									break;
 								}
 								
 							}
 							
 							echo "<td>
									<a class='btn btn-success' href='?do=edit&uid=".$row['prodID']."'><i class='fa fa-pencil-square-o' aria-hidden='true'></i> Edit</a>
									<a class='btn btn-danger delete-btn' data-toggle='confirmation' href='members.php?do=Delete&uid=". $row['prodID'] ."'><i class='fa fa-trash-o' aria-hidden='true'></i> Delete</a>
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

			<h2 class="text-center">Add new Product</h2>
			<div class="container">
				<div class="form prodForm" action="" method="POST">
					<form class="form-horizontal " action="?do=insert" method="POST" enctype="multipart/form-data">

						<div class="form-group">
							<label class="col-sm-offset-2 col-sm-2 control-label" >Product Name</label>
							<div class="col-sm-6">
								<input class="form-control input-lg" type="text" name="ProdName" required="required">
							</div>
						</div>

							
						<div class="form-group">
							<label class="col-sm-offset-2 col-sm-2 control-label" >Product price</label>
							<div class="col-sm-6">
								<input class="form-control input-lg" type="text" name="ProdPrc" required="required">
							</div>
						</div>


						<div class="form-group">
							<label class="col-sm-offset-2 col-sm-2 control-label" >Product Quantity</label>
							<div class="col-sm-6">
								<input class="form-control input-lg" type="text" name="ProdQ" required="required">
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-offset-2 col-sm-2 control-label" >Product description</label>
							<div class="col-sm-6">
								<textarea class="form-control input-lg" type="text" name="ProdDesc" required="required"></textarea>
							</div>
						</div>


						<div class="form-group">
							<label class="col-sm-offset-2 col-sm-2 control-label" >Category</label>
							<div class="col-sm-6">
								<select name="category">
							<?php
							$stmt2 = $con->prepare("select * from category");
								$stmt2->execute();
								$rows2 = $stmt2->fetchAll();
								foreach ($rows2 as $row) {
								  echo "<option value='".$row['catID']."'>".$row['catName']."</option>";
								}
								?>
								</select>
							</div>
						</div>


						<div class="form-group">
							<label class="col-sm-offset-2 col-sm-2 control-label" >Product photo</label>
							<div class="col-sm-6">
								<input class="form-control input-lg" type="file" name="proPhoto" >
							</div>
						</div>

						<div class="form-group">
							<div class="col-sm-offset-4 col-sm-6">
								<input class="btn btn-primary btn-block btn-lg" type="submit" value="Add New Memebr">
							</div>
						</div>
					</form>
				</div>
				</div>


		 	<?php

		}
		# end add page ...

		# start insert page ...
		elseif ($do == 'insert') {
				echo "<h2 class='text-center'>insert Product</h2>";
	echo "<div class='container'>";
				if($_SERVER['REQUEST_METHOD']=='POST'){
					$ProdName = $_POST['ProdName'];
					$ProdPrc = $_POST['ProdPrc'];
					$ProdQ = $_POST['ProdQ'];
					$ProdDesc = $_POST['ProdDesc'];
					$category = $_POST['category'];
					//photo cintent inserted in folder and phto path into database
					$dirPhoto = "uploaded/".$_FILES['proPhoto']['name'];
					move_uploaded_file($_FILES['proPhoto']['tmp_name'], $dirPhoto);
					$path = "http://localhost/shop/admin/".$dirPhoto;
					$errors = array();
					if(empty($ProdName)){
						$errors[] = "Username can't be empty";
					}elseif(!is_numeric($ProdPrc)){
						$errors[] = "price must be numeric value";
					}

					foreach ($errors as $value) {
						echo "<div class='alert alert-danger'>". $value  . "</div>";
					}

					if(empty($errors)){
						$stmt = $con->prepare("insert into product(prodName, prodPrice, prodQuantity, prodDescription, catID, img_Path) values(?, ?, ?, ?, ?, ?)");
						$stmt->execute(array($ProdName, $ProdPrc, $ProdQ, $ProdDesc, $category, $path));

						echo '<div class="alert alert-success">'.$stmt->rowCount().' Product inserted</div>';
						redirectHome("insert complated",'back', 5);
					}
					echo "</div>";	
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