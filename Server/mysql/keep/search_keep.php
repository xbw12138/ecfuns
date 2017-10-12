<?php
$response = array();
require("../db_config.php");
$conn=mysql_connect($mysql_server_name,$mysql_username,$mysql_password) or die("error connecting") ;
mysql_query("set names 'utf8'"); 
mysql_select_db($mysql_database);
if (isset($_POST['user_phone'])) {
    $user_phone = $_POST['user_phone'];
    $sql="select    keep_id,light, shiftstate,enginestate,oilcount,colometer,carlevel,enginenum,platenum,logo,brand,send_time from     keep_information where user_phone='$user_phone'";
	if($result=mysql_query($sql)){
		while($row=mysql_fetch_array($result)){
			$array[] = array( 
			'message'=>'YES',
			'keep_id'=>$row[0], 
			'light'=>$row[1], 
			'shiftstate'=>$row[2], 
			'enginestate'=>$row[3], 
			'oilcount'=>$row[4], 
			'colometer'=>$row[5], 
			'carlevel'=>$row[6], 
			'enginenum'=>$row[7], 
			'platenum'=>$row[8], 
			'logo'=>$row[9], 
			'brand'=>$row[10], 
			'send_time'=>$row[11],
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