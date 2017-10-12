<?php
$response = array();
require("db_config.php");
$conn=mysql_connect($mysql_server_name,$mysql_username,$mysql_password) or die("error connecting") ;
mysql_query("set names 'utf8'"); 
mysql_select_db($mysql_database);
if (isset($_POST['qr_id'])&&isset($_POST['is_finish'])&&isset($_POST['user_phone'])) {
    $qr_id = $_POST['qr_id'];
    $is_finish = $_POST['is_finish'];
	$user_phone = $_POST['user_phone'];
    $sql="update qr_information set is_finish='$is_finish' where qr_id='$qr_id'";
	$result=mysql_query($sql);
   if ($result) {
		if($is_finish=='N'){
			$url="http://115.159.26.120/ecfun/qr/finish.php?user_phone=".$user_phone."&qr_id=".$qr_id;
			$sqls="update qr_information set qr_url='$url' where qr_id='$qr_id'";
			$results=mysql_query($sqls);
			if ($results){
				
			}
		}else{
			$url="http://115.159.26.120/ecfun/qr/404.php";
			$sqls="update qr_information set qr_url='$url' where qr_id='$qr_id'";
			$results=mysql_query($sqls);
			if ($results){
				
			}
		}
        $response["success"] = 1;
        $response["message"] = "YES";
        echo json_encode($response);
    } else {
        $response["success"] = 0;
        $response["message"] = "NO";
        echo json_encode($response);
    }
} else {
    $response["success"] = 0;
    $response["message"] = "NO";
    echo json_encode($response);
}

?>