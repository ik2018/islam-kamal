<!DOCTYPE html>
<?php 
error_reporting(0);
session_start();

$cardlink = "sign.php";
if(isset($_SESSION['username']))
	{
        $cardlink = "prod.php?pid=".$row['prodID']."";
	}
	include "connect.php"; 
    
    
	$stmt = $con->prepare("select * from product");
						$stmt->execute();
						$rows = $stmt->fetchAll();

	$stmt2 = $con->prepare("select * from category");
						$stmt2->execute();
						$rows2 = $stmt2->fetchAll();
include "includes/templates/header.php";
?>


	<?php include "includes/templates/navbar.php";

?>
		


<section class="slidershow"><?php include "includes/templates/slider.php"; ?></section>
            

		<section class="products">
			<div class="container">
				<div class="row" id="content_prod">
				<!--<?php
					foreach ($rows as $row) {
						$catName ='';
 							foreach ($rows2 as $row2){
 								if($row['catID']===$row2['catID']){
 									 $catName = $row2['catName'];
 									break;
 								}
 							}


						echo '<div class="col-md-3 col-sm-4 col-xs-6">';
						echo "<div class='pro text-center ". $catName."'>";
						echo "<div class='price text-center '>".$row['prodPrice']." EGP</div>";
						echo "<div class='img_pro'><img src='".$row['img_Path']."'></div>";
						echo "<p class='lead'>".$row['prodDescription']."</p>";
						echo "<div>
								<a class='btn btn-primary' href='prod.php?pid=".$row['prodID']."' method='POST'>view</a>
                                <form action='index.php' method='POST'>
                                <input type='hidden' value='".$row['prodID']."' name='prodID'>
                                <input type='hidden' value='".$row['prodPrice']."' name='prodPrice'>
								<input class='btn btn-primary' type='submit' value='Add To Card' name='A2C'>
                                
                                </form>
							</div>";
						echo "</div>
						</div>";
					}

					?>
-->
                    
				</div>
			</div>
		</section>
        
        

		<?php
include "includes/templates/footer.php";
?>