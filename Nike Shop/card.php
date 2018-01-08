<?php 
session_start();

if(isset($_SESSION['username']))
	{
		
include "connect.php";
include "includes/templates/header.php";
include "includes/templates/navbar.php";
echo "<div class='card_items'>";
$do = isset($_GET['do'])? $_GET['do']:'manage';
if ($do == 'manage')
		{?>
			<div class="container"></h2>
				<div class="table-responsive">
					<table class="main-table text-center table table-bordered">
						<tr>
							<td>Product Name</td>
							<td>Price</td>
							<td></i>Quantity</td>
                            <td>total price</td>
							<td>Control</td>
						</tr>
					<?php


					/* retrive User data and display*/
						$stmt = $con->prepare("select * from ordered_prod where userID = ? and state = 0");
						$stmt->execute(array($_SESSION['id']));
						$rows = $stmt->fetchAll();

						$stmt2 = $con->prepare("select * from product");
						$stmt2->execute();
						$rows2 = $stmt2->fetchAll();
						foreach ($rows as $row) {
                            echo "<tr>";
                            foreach ($rows2 as $row2){
 								if($row['prodID']===$row2['prodID']){
                                    echo '<form class="form-horizontal " action="?do=update" method="POST">';
 									echo "<td>". $row2['prodName'] ."</td>";
                                    
                                    echo "<td>". $row2['prodPrice'] ."</td>";
                                    echo "<td><input type='text' value=". $row['quantity'] ." name='quantity1'></td>";
                                    echo "<td>". $row['totalPrice'] ."</td>";
                                    break;
 							}}
 							
 							echo "<td>
                                    
                                    <input type=hidden value=". $row['totalPrice']." name='totalPrice'>
                                    <input type=hidden value=". $row2['prodPrice']." name='prodPrice'>
                                    <input type=hidden value=". $row['orderID']." name='orderID'>
									<input type ='submit' value='edit' class='btn btn-success'>
                                    </form>
                                    <!--delete button -->
									<a class='btn btn-danger delete-btn' data-toggle='confirmation' href='?do=Delete&oid=". $row['orderID']."'><i class='fa fa-trash-o' aria-hidden='true'></i> Delete</a>
                                    <!--buy button -->
                                    <a class='btn btn-info delete-btn' data-toggle='confirmation' href='buy.php?do=buy&oid=". $row['orderID']."'><i class='fa fa-check' aria-hidden='true'></i> Buy Now</a>
                                    
								</td>";
							echo "</tr>";
						}

					?>
					</table>

				</div>
				<div class='text-center' >
                    <a class='btn btn-info delete-btn ' data-toggle='confirmation' href='buy.php?do=buyAll'><i class='fa fa-check' aria-hidden='true'></i> Buy All Now</a>


                    <a class='btn btn-success delete-btn' data-toggle='confirmation' href='buy.php'><i class='fa fa-history' aria-hidden='true'></i> your history</a>
					</div>
			</div>
			
		<?php
	}elseif ($do =='update') {
				
				if($_SERVER['REQUEST_METHOD']=='POST'){
					$ID = $_POST['orderID'];
					$quantity = $_POST['quantity1'];
                    $prodPrice = $_POST['prodPrice'];
					$totalPrice = (float)$prodPrice * (int)$quantity;
					
						$stmt = $con->prepare("update ordered_prod set quantity = ?, totalPrice = ? where orderID = ?");
						$stmt->execute(array($quantity, $totalPrice, $ID));
                
                    header("refresh:0;url=card.php?do=manage");

					}
					
				}elseif ($do == 'Delete') {
				$oid =(isset($_GET['oid'])&&is_numeric($_GET['oid']))? intval($_GET['oid']): 0 ;
            if($oid!=0){
                $stmt = $con->prepare("select * from ordered_prod where orderID=". $oid."");

			 	$stmt->execute();
			 	$count = $stmt->rowCount();
			 	if($count > 0){
			 		$stmt = $con->prepare("delete from ordered_prod where orderID =?");

			 		$stmt->execute(array("$oid"));
            }
				
			 	}
        header("refresh:0;url=card.php?do=manage");
			}

echo "</div>";
include "includes/templates/footer.php";
}
else{
    header('location:sign.php');
}
?>