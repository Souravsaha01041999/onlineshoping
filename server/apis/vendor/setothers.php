<?php
require_once('concention.php');
if($_SERVER["REQUEST_METHOD"]=="POST")
{
    $id=$_POST['id'];
    $price=$_POST['price'];
    $qun=$_POST['qun'];
    $offer=$_POST['offer'];
    
    
    $sql="UPDATE `product` SET price='$price', qun='$qun', offer='$offer' WHERE prd_id='$id'";
    
    $res=mysqli_query($con,$sql);
}
?>