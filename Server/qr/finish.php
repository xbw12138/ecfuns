<?php
$response = array();
require("db_config.php");
$conn=mysql_connect($mysql_server_name,$mysql_username,$mysql_password) 

or die("error connecting") ;
mysql_query("set names 'utf8'"); 
mysql_select_db($mysql_database);

if (isset($_GET['user_phone']) && isset($_GET['qr_id'])) {
    $user_phone = $_GET['user_phone'];
    $qr_id = $_GET['qr_id'];
	$sqlx="select qr_content from qr_information where qr_id='$qr_id'";
	$resultx=mysql_query($sqlx);
	if ($rowx=mysql_fetch_row($resultx)) {
		$array = array( 
		'0'=>"悄悄话：    ".$rowx[0], 			
		); 
		echo "<BR>"."<BR>";
		foreach($array as $a){
			echo $a."<BR>";
	   }
	}else {
		echo "<center>";
		echo "<BR>"."<BR>"."获取数据失败"."<BR>";
		echo "</center>";
	}
    
} else {
    echo "<center>";
	echo "<BR>"."<BR>"."传入非法参数"."<BR>";
	echo "</center>";
}
?>