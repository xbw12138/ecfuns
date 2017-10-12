<?php
$response = array();
require("../db_config.php");
$conn=mysql_connect($mysql_server_name,$mysql_username,$mysql_password) or die("error connecting") ;
mysql_query("set names 'utf8'"); 
mysql_select_db($mysql_database);
if (isset($_POST['order_id'])&&isset($_POST['is_finish'])) {
    $order_id = $_POST['order_id'];
    $is_finish = $_POST['is_finish'];
    $sql="update order_information set is_finish='$is_finish' where order_id='$order_id'";
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