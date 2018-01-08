<?php
session_start();
$log = '<a href="sign.php"><i class="fa fa-sign-in" aria-hidden="true"></i> Sign in</a>';

$cardlink = "sign.php";
if(isset($_SESSION['username']))
	{
    
    
    
    $stmt = $con->prepare("select * from product");
						$stmt->execute();
						$rows = $stmt->fetchAll();

	$stmt2 = $con->prepare("select * from category");
						$stmt2->execute();
						$rows2 = $stmt2->fetchAll();
    
    
		$log ='<ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                      <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">  <i class="fa fa-user" aria-hidden="true"></i> '.$_SESSION['username'].' <span class="caret"></span></a>
                      <ul class="dropdown-menu">
            
                        <li><a href="logout.php"><i class="fa fa-sign-out" aria-hidden="true"></i> Logout</a></li>
                      </ul>
                    </li>
                  </ul>';
    $cardlink = "card.php";
	}
?>
<nav class="navbar navbar-default navbar-fixed-top">
		  <div class="container">
		    <div class="navbar-header">
		      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
		        <span class="sr-only">Toggle navigation</span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		      </button>
		      <a class="navbar-brand" href="index.php"><img width=200 height=70 src="images/index.jpg"></a>
		    </div>
		    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<!---  -->
		      <ul class="nav navbar-nav navbar-left"  >
		      <li><a data-target="all">All </a></li>
               
		      <?php
					foreach ($rows2 as $row) {
						echo "<li><a data-target='".$row['catName']."'>".$row['catName']."</a></li>";

					}?>
    
                <li>
                    <?php echo $log; ?>
                </li>  
                </ul>
    
                  
		    </div><!-- /.navbar-collapse -->
                <div class="carddiv">
                  <a href="<?php echo $cardlink ?>"><i class="fa fa-shopping-cart  fa-2x"></i>
                        <span class="total">
                            <?php
                            if(isset($_POST['A2C'])){

                            	if($_POST['prodID'] != null&&$_POST['prodPrice'])
                            	{
                            		$stmt11 = $con->prepare("insert into ordered_prod(userID,quantity,prodID, totalPrice) values(?,1,?,?)");
							 $stmt11->execute(array($_SESSION['id'],$_POST['prodID'],$_POST['prodPrice']));
                                
                                $stmt22 = $con->prepare("SELECT sum(totalPrice) ,count(*) FROM ordered_prod WHERE userID =? and state = 0");
                                    $stmt22->execute(array($_SESSION['id']));
                                    $rows22 = $stmt22->fetch();

                                    echo'<span class="tp">('.$rows22['1'].')</span>';
                            		echo '<span class="tpr"> price: '.$rows22['0'].' EGP</span>';

                            	}else{
                            		echo'<span class="tp">\(0\)</span>';
                            		echo '<span class="tpr"> price: 0 EGP</span>';
                            	
                                }}else{
                                $stmt22 = $con->prepare("SELECT sum(totalPrice) ,count(*) FROM ordered_prod WHERE userID =? and state = 0");
                                    $stmt22->execute(array($_SESSION['id']));
                                    $rows22 = $stmt22->fetch();

                                    echo'<span class="tp">('.$rows22['1'].')</span>';
                            		echo '<span class="tpr"> price: '.$rows22['0'].' EGP</span>';
                            }
                            
                            ?>
                            
                        </span>
                        </a>
                  </div>
		  </div><!-- /.container-fluid -->
		</nav>