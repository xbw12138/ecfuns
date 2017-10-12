<?php

$str="haha"."\r\n";
$k=fopen("1.txt","a+");
fwrite($k,$str);
fclose($k);


?>