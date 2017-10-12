<?php
$response = array();
require("db_config.php");
$conn=mysql_connect($mysql_server_name,$mysql_username,$mysql_password) 
or die("error connecting") ;
mysql_query("set names 'utf8'"); 
mysql_select_db($mysql_database);

if (isset($_GET['user_phone']) && isset($_GET['send_time'])) {
    $user_phone = $_GET['user_phone'];
    $send_time = $_GET['send_time'];
$url="http://115.159.26.120/androidapp_matrix/qr/finish.php?user_phone=".$user_phone."&send_time=".$send_time;
	$sqls="select is_finish from order_information where url='$url'";
$row = mysql_fetch_array(mysql_query($sqls));

if($row[0]=='N')
{
	echo "相等";

}else{

echo "不相等";
}
}


?>