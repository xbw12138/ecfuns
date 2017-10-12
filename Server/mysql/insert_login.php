<?php
$response = array();
require("db_config.php");
$conn=mysql_connect($mysql_server_name,$mysql_username,$mysql_password) or die("error connecting") ;
mysql_query("set names 'utf8'"); 
mysql_select_db($mysql_database);
if (isset($_POST['user_phone']) && isset($_POST['user_pwd'])&&isset($_POST['user_signtime'])) {
    $user_phone = $_POST['user_phone'];
    $user_pwd = $_POST['user_pwd'];
    $user_signtime = $_POST['user_signtime'];	
    $result = "INSERT INTO user_information(user_phone, user_pwd, user_signtime) VALUES('$user_phone', '$user_pwd', '$user_signtime')";
    $results = "INSERT INTO qr_information(user_phone) VALUES('$user_phone')";
    $resultss = "INSERT INTO qr_information(user_phone) VALUES('$user_phone')";
    $resultsss = "INSERT INTO qr_information(user_phone) VALUES('$user_phone')";
mysql_query($result);
mysql_query($results);
mysql_query($resultss);
mysql_query($resultsss);
if ($result&&$results&&$resultss&&$resultsss) {
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
