<?php
$response = array();
require("../db_config.php");
$conn=mysql_connect($mysql_server_name,$mysql_username,$mysql_password) or die("error connecting") ;
mysql_query("set names 'utf8'"); 
mysql_select_db($mysql_database);
if (isset($_POST['user_phone'])) {
    $user_phone = $_POST['user_phone'];
    $sql="select     gas_num,gas_station,gas_type,order_time,is_finish,order_id,send_time from     order_information where user_phone='$user_phone'";
	if($result=mysql_query($sql)){
		while($row=mysql_fetch_array($result)){
			$array[] = array( 
			'message'=>'YES',
			'gas_num'=>$row[0], 
			'gas_station'=>$row[1], 
			'gas_type'=>$row[2],
			'order_time'=>$row[3], 
			'is_finish'=>$row[4], 
			'order_id'=>$row[5],
			'send_time'=>$row[6],
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

} else {
	$array = array( 
	'message'=>'NO',
	'success'=>0, 
	); 
	echo json_encode($array);
}


?>