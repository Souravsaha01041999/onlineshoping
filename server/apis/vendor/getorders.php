<?php
require_once('concention.php');
if($_SERVER["REQUEST_METHOD"]=="POST")
{
    $id=$_POST['sp_id'];
    $pass=$_POST['pass'];
    $date=$_POST['date'];
    
    $sql="SELECT * FROM `shop` WHERE shop_id='$id'";
    $res=mysqli_query($con,$sql);
    $row=mysqli_fetch_assoc($res);
    $data="[";
    
    if($row['password']=="$pass")
    {
        $sql="SELECT * FROM `order` WHERE shopid='$id' AND date='$date'";
        $res=mysqli_query($con,$sql);
        while($row=mysqli_fetch_assoc($res))
            {
                $data=$data.json_encode($row).',';    
            }
            $data=substr($data,0,strlen($data)-1);
            $data=$data."]";
            echo $data;
    }
    else
    {
        echo "n";       //n IS FOR PASSWORD NOT MATCH
    }
    
}
?>