<?php
$s=$_GET["d"];
$f=fopen("ctrldata.txt",'w') or die("not open");
fwrite($f,$s);
fclose($f);
?>