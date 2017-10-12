<?php
$response = array();
require("../db_config.php");
$conn=mysql_connect($mysql_server_name,$mysql_username,$mysql_password) or die("error connecting") ;
mysql_query("set names 'utf8'"); 
mysql_select_db($mysql_database);
if (isset($_POST['user_phone']) && isset($_POST['light'])&&isset($_POST['shiftstate'])&&isset($_POST['enginestate'])&&isset($_POST['oilcount'])&&isset($_POST['colometer'])&&isset($_POST['carlevel'])&&isset($_POST['enginenum'])&&isset($_POST['platenum'])&&isset($_POST['logo'])&&isset($_POST['brand'])&&isset($_POST['send_time'])) {
    $user_phone = $_POST['user_phone'];
    $light = $_POST['light'];
    $shiftstate = $_POST['shiftstate'];	
    $enginestate = $_POST['enginestate'];
    $oilcount = $_POST['oilcount'];
    $colometer = $_POST['colometer'];
    $carlevel = $_POST['carlevel'];
    $enginenum = $_POST['enginenum'];	
    $platenum = $_POST['platenum'];
    $logo = $_POST['logo'];
    $brand = $_POST['brand'];
    $send_time = $_POST['send_time'];
    $result = "INSERT INTO keep_information(user_phone, light, shiftstate,enginestate,oilcount,colometer,carlevel,enginenum,platenum,logo,brand,send_time) VALUES('$user_phone', '$light', '$shiftstate', '$enginestate', '$oilcount', '$colometer', '$carlevel', '$enginenum', '$platenum', '$logo', '$brand', '$send_time')";
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
