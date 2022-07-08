<?php
require_once('concention.php');
if($_SERVER["REQUEST_METHOD"]=="POST")
{
    $id=$_POST['id'];
    $sql="DELETE FROM `product` WHERE prd_id='$id'";
    mysqli_query($con,$sql);
    $id=$id.".jpg";
    unlink($id);
}
?>