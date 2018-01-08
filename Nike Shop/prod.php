<!DOCTYPE html>

<?php 
    session_start();
	include "connect.php"; 
    
    $prodID =(isset($_GET['pid'])&&is_numeric($_GET['pid']))? intval($_GET['pid']): 0 ;
    if($prodID !=0){
        $stmt = $con->prepare("select * from product where prodID =? limit 1");

		 	$stmt->execute(array("$prodID"));
		 	$rowP = $stmt->fetch();
		 	$count = $stmt->rowCount();
		 	if($count > 0){
                $stmt2 = $con->prepare("select * from category");
						$stmt2->execute();
						$rows2 = $stmt2->fetchAll();

        
			         
?>

	<body>
<?php include "includes/templates/header.php"; ?>
		<!-- start navbar -->
     <?php include "includes/templates/navbar.php"; ?>
		<!-- end navbar -->
		<!-- start content -->
		<section class="prodView">
			<div class="container">
				<div class="row">
					<div class="col-sm-4">
						<div class="prod-img">
							<img src="<?php echo $rowP['img_Path']?>">
						</div>
					</div>

					<div class="col-sm-8">
						<div class="prod-info">
						<h2 class="text-center">Product Data</h2>
							<div>
								<span class="lable">name</span><span class="pro_name"> <?php echo $rowP['prodName']?></span>
							</div>
							<div>
								<span class="lable">price</span><span class="pro_price"> <?php echo $rowP['prodPrice']?> EG</span>
							</div>
							<div >
								<span class="lable">description</span><span class="pro_descrip">  <?php echo $rowP['prodDescription']?></span>
							</div>

							<div >
								<span class="lable">quantity</span><span class="pro_descrip">  <?php echo $rowP['prodQuantity']?></span>
							</div>
							<div class="card">
		                        <a class="btn btn-primary" href="" >Add To Card</a>
							</div>
					</div>

					

				</div>
			</div>

            <?php }
        else{
            echo "no product";
        }}else{
        echo "no any product found";
    }

            
include "includes/templates/footer.php";
            ?>