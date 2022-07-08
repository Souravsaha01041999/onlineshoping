<?php
require_once('concention.php');
if($_SERVER["REQUEST_METHOD"]=="POST")
{
    $cata=$_POST['cata'];
    $sql="SELECT `shop_id`,`shop_name`,`image` FROM `shop` WHERE category='$cata'";
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