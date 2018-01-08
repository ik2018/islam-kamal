<?php

include "connect.php"; 
    
    if($_POST['do']=="all"){
        
    
	$stmt = $con->prepare("select * from product");
						$stmt->execute();
						$rows = $stmt->fetchAll();
    }else{
        $stmt = $con->prepare("select * from product , category where product.catID = category.catID and category.catName = ?");
						$stmt->execute(array($_POST['do']));
						$rows = $stmt->fetchAll();
    }

	$stmt2 = $con->prepare("select * from category");
						$stmt2->execute();
						$rows2 = $stmt2->fetchAll();

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
						echo "<div class='img_pro'><img src='".$row['img_Path']."' alt='mobile mz'></div>";
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

