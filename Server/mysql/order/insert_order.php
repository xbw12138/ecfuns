<?php
$response = array();
require("../db_config.php");
$conn=mysql_connect($mysql_server_name,$mysql_username,$mysql_password) or die("error connecting") ;
mysql_query("set names 'utf8'"); 
mysql_select_db($mysql_database);
if (isset($_POST['user_phone']) && isset($_POST['order_time'])&&isset($_POST['gas_station'])&&isset($_POST['gas_type'])&&isset($_POST['gas_num'])&&isset($_POST['send_time'])) {
    $user_phone = $_POST['user_phone'];
    $order_time = $_POST['order_time'];
    $gas_station = $_POST['gas_station'];	
    $gas_type = $_POST['gas_type'];
    $gas_num = $_POST['gas_num'];
    $send_time = $_POST['send_time'];
    $url="http://115.159.26.120/androidapp_matrix/qr/finish.php?user_phone=".$user_phone."&send_time=".$send_time;
    $result = "INSERT INTO order_information(user_phone, order_time, gas_station,gas_type,gas_num,send_time,url) VALUES('$user_phone', '$order_time', '$gas_station', '$gas_type', '$gas_num', '$send_time','$url')";
mysql_query($result);
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
