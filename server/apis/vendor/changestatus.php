<?php
require_once('concention.php');
if($_SERVER["REQUEST_METHOD"]=="POST")
{
    $id=$_POST['id'];
    $status=$_POST['status'];
    
    
    $sql="UPDATE `order` SET status='$status' WHERE ordidtab='$id'";
    
    $res=mysqli_query($con,$sql);
}
?>