<?php
require_once('concention.php');
if($_SERVER["REQUEST_METHOD"]=="POST")
{
    $id=$_POST['id'];
    $avl=$_POST['avl'];
    
    
    $sql="UPDATE `product` SET avl='$avl' WHERE prd_id='$id'";
    
    $res=mysqli_query($con,$sql);
}
?>