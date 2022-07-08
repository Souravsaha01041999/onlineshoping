<?php
require_once('concention.php');
if($_SERVER["REQUEST_METHOD"]=="POST")
{
    $id=$_POST['sid'];
    $cata=$_POST['cata'];
    $type=$_POST['type'];
    if($type!="a")
    {
    $sql="SELECT `prd_id`,`prd_name`,`price`,`image`,`avl`,`offer`,`type`,`qun` FROM `product` WHERE shop_id='$id' AND cata='$cata' AND type='$type'";
    }
    else
    {
        $sql="SELECT `prd_id`,`prd_name`,`price`,`image`,`avl`,`offer`,`type`,`qun` FROM `product` WHERE shop_id='$id' AND cata='$cata'";
    }
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