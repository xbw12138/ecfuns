<?php
$response = array();
require("../db_config.php");
$conn=mysql_connect($mysql_server_name,$mysql_username,$mysql_password) or die("error connecting") ;
mysql_query("set names 'utf8'"); 
mysql_select_db($mysql_database);
if (isset($_POST['user_phone']) &&isset($_POST['qr_id'])) {
    $user_phone = $_POST['user_phone'];
    $qr_id = $_POST['qr_id'];
    $qr_url="http://115.159.26.120/ecfun/qr/msg.php?user_phone=".$user_phone."&qr_id=".$qr_id;
    $result = "update qr_information set qr_url='$qr_url' where qr_id='$qr_id'";
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
