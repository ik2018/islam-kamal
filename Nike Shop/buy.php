<?php
    session_start();
    if(isset($_SESSION['username']))
	{
        include "connect.php";
        include "includes/templates/header.php";
        include "includes/templates/navbar.php";
        echo $_SESSION['username'];
    }
    
    $do = isset($_GET['do'])? $_GET['do']:'history';
    if ($do == 'history')
    { ?>
        <div class="container">
				<h2 class="text-center">Product you buy</h2>
				<div class="table-responsive">
					<table class="main-table text-center table table-bordered">
						<tr>
							<td>Product Name</td>
							<td>Quantity</td>
                            <td>total price</td>
							<td>Date</td>
						</tr>
					<?php


					/* retrive User data and display*/
						$stmt = $con->prepare("select * from ordered_prod where userID = ? and state = 1 order by buy_date desc");
						$stmt->execute(array($_SESSION['id']));
						$rows = $stmt->fetchAll();

						$stmt2 = $con->prepare("select * from product");
						$stmt2->execute();
						$rows2 = $stmt2->fetchAll();
						foreach ($rows as $row) {
                            echo "<tr>";
                            foreach ($rows2 as $row2){
 								if($row['prodID']===$row2['prodID']){
 									echo "<td>". $row2['prodName'] ."</td>";
                                    echo "<td>". $row['quantity']."</td>";
                                    echo "<td>". $row['totalPrice'] ."</td>";
                                    echo "<td>". $row['buy_date'] ."</td>";
                                    break;
 							}}
							echo "</tr>";
						}

					?>
					</table>

				</div><?php
    }elseif ($do == 'buy'){
        $stmt = $con->prepare("update ordered_prod set state = 1 , buy_date = NOW() where userID = ? and orderID=?");
        $stmt->execute(array($_SESSION['id'],$_GET['oid']));
        
        echo '<div class="alert alert-success">'.$stmt->rowCount().' Product buy</div>';
        header("refresh:3;url=buy.php");
        
    }elseif ($do == 'buyAll'){
        $stmt = $con->prepare("update ordered_prod set state = 1 , buy_date = NOW() where userID = ? and state = 0");
        $stmt->execute(array($_SESSION['id']));
        
        echo '<div class="alert alert-success">'.$stmt->rowCount().' Product buy</div>';
        header("refresh:3;url=buy.php");
        
    }
    