<?php
$response = array();
require("db_config.php");
$conn=mysql_connect($mysql_server_name,$mysql_username,$mysql_password) 

or die("error connecting") ;
mysql_query("set names 'utf8'"); 
mysql_select_db($mysql_database);

if (isset($_POST['user_phone'])) {
	$user_phone = $_POST['user_phone'];
	$sql="select qr_id,qr_url,is_finish from qr_information where user_phone='$user_phone'";
	if($result=mysql_query($sql)){
		while($row=mysql_fetch_array($result)){
			$array[] = array( 
			'message'=>'YES',
			'qr_id'=>$row[0],
			'qr_url'=>$row[1],
			'is_finish'=>$row[2],
			
			);
		}
		echo json_encode($array);
	}else {
		$array = array( 
		'message'=>'NO',
		'success'=>0, 
		); 
        echo json_encode($array);
    }
}else {
	$array = array( 
	'message'=>'NO',
	'success'=>0, 
	); 
	echo json_encode($array);
}










?>