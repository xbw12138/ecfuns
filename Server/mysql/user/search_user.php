<?php
$response = array();
require("../db_config.php");
$conn=mysql_connect($mysql_server_name,$mysql_username,$mysql_password) or die("error connecting") ;
mysql_query("set names 'utf8'"); 
mysql_select_db($mysql_database);
if (isset($_POST['user_phone'])) {
    $user_phone = $_POST['user_phone'];
    $sql="select     user_name,user_signtime,user_head from user_information where user_phone='$user_phone'";
	$result=mysql_query($sql);
    if ($row=mysql_fetch_row($result)) {
	$array = array( 
	'message'=>'YES',
	'user_name'=>$row[0], 
	'user_signtime'=>$row[1], 
        'user_head'=>$row[2], 
	); 
        echo json_encode($array);

    } else {
	$array = array( 
	'message'=>'NO',
	'success'=>0, 
	); 
        echo json_encode($array);
    }
} else {
	$array = array( 
	'message'=>'NO',
	'success'=>0, 
	); 
echo json_encode($array);
}


?>