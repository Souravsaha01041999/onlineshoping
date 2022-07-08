<?php
require_once('concention.php');
if($_SERVER["REQUEST_METHOD"]=="POST")
{
    $no=$_POST['orid'];
    
    $sql="UPDATE `order` SET status = 'cancel' WHERE ordidtab='$no'";
    $res=mysqli_query($con,$sql);
}
?>