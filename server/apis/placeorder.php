<?php
require_once('concention.php');
if($_SERVER["REQUEST_METHOD"]=="POST")
{
    $orderid=$_POST['ordid'];
    $shopid=$_POST['shopid'];
    $lists=$_POST['list'];
    $name=$_POST['cstname'];
    $address=$_POST['addr'];
    $phone=$_POST['phone'];
    $pin=$_POST['pin'];
    $date=$_POST['date'];
    $cmd=$_POST['cmd'];
    
    $sql="INSERT INTO `order` (`ordidtab`, `shopid`, `orderlist`, `address`, `phone`, `pincode`, `date`, `status`,`cast_name`) VALUES ('$orderid', '$shopid', '$lists', '$address', '$phone', '$pin', '$date', 'pending','$name')";
     $res=mysqli_query($con,$sql);
     
     $sql="SELECT * FROM `order` WHERE ordidtab='$orderid'";
     $res=mysqli_query($con,$sql);
     
     $row=mysqli_fetch_assoc($res);
     if(strlen($row['ordidtab'])>0)
     {
        if(intval(substr_count($cmd,";"))>1)
        {
            $arr=explode(";",$cmd);
            for($i=0;$i<count($arr);$i=$i+1)
            {
                mysqli_query($con,$arr[$i]);
            }
        }
        else
        {
            mysqli_query($con,$cmd);
        }
        echo "1";
     }
     else
     {
         echo "0";
     }
}
?>