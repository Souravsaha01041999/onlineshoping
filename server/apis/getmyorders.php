<?php
require_once('concention.php');
if($_SERVER["REQUEST_METHOD"]=="POST")
{
    $no=$_POST['phno'];
    
    $sql="SELECT `ordidtab`,`shop_name`,`status`,`orderlist`,`date` FROM `order`  INNER JOIN `shop` ON `order`.shopid = `shop`.shop_id WHERE `order`.phone='$no' ORDER BY `order`.date DESC";
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