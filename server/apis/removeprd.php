<?php
require_once('concention.php');
if($_SERVER["REQUEST_METHOD"]=="POST")
{
    $ordid=$_POST['ordid'];
    $list=$_POST['list'];
    $prdid=$_POST['prdid'];
    $qun=$_POST['qun'];
    
    $sql="UPDATE `order` SET orderlist='$list' WHERE ordidtab='$ordid'";
    $res=mysqli_query($con,$sql);
    
    
    
    
    $sql="UPDATE `product` SET qun =qun + $qun WHERE prd_id='$prdid'";
    $res=mysqli_query($con,$sql);
    
    echo "done";
}
?>