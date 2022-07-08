<?php
$f=fopen("ctrldata.txt",'r');
$s1=fread($f,filesize("ctrldata.txt"));
fclose($f);
echo $s1;
?>