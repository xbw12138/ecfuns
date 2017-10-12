<?php
$response = array();
require("../db_config.php");
$conn=mysql_connect($mysql_server_name,$mysql_username,$mysql_password) or die("error connecting") ;
mysql_query("set names 'utf8'"); 
mysql_select_db($mysql_database);
if (isset($_POST['keep_id'])&&isset($_POST['oilcount'])) {
    $keep_id = $_POST['keep_id'];
    $oilcount = $_POST['oilcount'];
    $sql="update keep_information set oilcount='$oilcount' where keep_id='$keep_id'";
	$result=mysql_query($sql);
   if ($result) {
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