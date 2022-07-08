<?php
require_once('concention.php');
if($_SERVER["REQUEST_METHOD"]=="POST")
{
    $id=$_POST['pid'];
    
    $sql="SELECT * FROM `product` WHERE prd_id='$id'";
    $res=mysqli_query($con,$sql);
    $data="[";
    while($row=mysqli_fetch_assoc($res))
        {
            $data=$data.json_encode($row).',';
        }
       $data=substr($data,0,strlen($data)-1);
       $data=$data."]";
            echo $data;
}
?>