<?php
require_once('concention.php');
if($_SERVER["REQUEST_METHOD"]=="POST")
{
    $id=$_POST['id'];
    $pass=$_POST['pass'];
    
    
    $sql="SELECT * FROM `shop` WHERE shop_id='$id'";
    
    $res=mysqli_query($con,$sql);
    $row=mysqli_fetch_assoc($res);
    
    $data="[";
    
    if(strlen($row['shop_id'])>0)
    {
        if($row['password']=="$pass")
        {
            $data=$data.json_encode($row)."]";
            echo $data;
        }
        else{
            echo "1";        //PASSWORD NOT MATCH
        }
    }
    else
    {
        echo "0";    //USER NOT FOUND
    }
}
?>