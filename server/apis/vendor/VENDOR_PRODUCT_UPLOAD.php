<?php
require_once('concention.php');
if($_SERVER["REQUEST_METHOD"]=="POST")
{
    $prd_id=$_POST['prd_id'];
    $prd_name=$_POST['prd_name'];
    $price=$_POST['price'];
    $detail=$_POST['detail'];
    $type=$_POST['type'];   
    $shop_id=$_POST['shop_id'];    
    $cata=$_POST['cata'];     
    $qun=$_POST['qun'];
    
    $temp=basename($_FILES['file']['name']);
    $temp2="../product_image/".basename($_FILES['file']['name']);
    if(move_uploaded_file($_FILES['file']['tmp_name'],$temp2))
    {
        //IT WILL CHANGE
        $temp="https://souravsaha1234.000webhostapp.com/shopingshila/apis/product_image/".$temp;
        $sql="INSERT INTO `product` (`prd_id`, `prd_name`, `price`, `image`, `avl`, `offer`, `detail`, `type`, `shop_id`, `cata`, `qun`) VALUES ('$prd_id', '$prd_name', '$price', '$temp', '1', 'n', '$detail', '$type', '$shop_id', '$cata', '$qun')";
    mysqli_query($con,$sql);
    
    $sql="SELECT * FROM `product` WHERE prd_id='$prd_id'";
    $rs=mysqli_query($con,$sql);
    $row=mysqli_fetch_assoc($rs);
    if(strlen($row['prd_id'])>0)
    {
        echo "1";       //INSEART SUCCESS
    }
    else
    {
        echo "0";      //NOT INSEART
    }
    }
    else
    {
        echo "0";     //NOT INSEART
    }
    
}
?>