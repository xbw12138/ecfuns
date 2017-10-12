<?php
$response = array();
require("../db_config.php");
$conn=mysql_connect($mysql_server_name,$mysql_username,$mysql_password) or die("error connecting") ;
mysql_query("set names 'utf8'"); 
mysql_select_db($mysql_database);
if (isset($_POST['pl_id'])) {
    $pl_id = (int)$_POST['pl_id'];
	$resultz=mysql_query("select pl_zan from pl_information where pl_id='$pl_id'");
	if($resultz){
		$rowz=mysql_fetch_row($resultz);
		$result=mysql_query("update pl_information set pl_zan='$rowz[0]'+1 where pl_id='$pl_id'");
		$response["success"] = 1;
        $response["message"] = "YES";
        echo json_encode($response);
	}else {
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