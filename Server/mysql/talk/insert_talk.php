<?php
$response = array();
require("../db_config.php");
$conn=mysql_connect($mysql_server_name,$mysql_username,$mysql_password) or die("error connecting") ;
mysql_query("set names 'utf8'"); 
mysql_select_db($mysql_database);
if (isset($_POST['user_phone']) && isset($_POST['talk_time'])&&isset($_POST['talk_content'])&&isset($_POST['talk_url'])&&isset($_POST['talk_ty'])) {
    $user_phone = $_POST['user_phone'];
    $talk_time = $_POST['talk_time'];
    //$talk_title = $_POST['talk_title'];	
    $talk_content = $_POST['talk_content'];
    $talk_url = $_POST['talk_url'];
	$talk_ty = $_POST['talk_ty'];
    //$user_name = $_POST['user_name'];
	//$sqls="select user_head from user_information where user_phone='$user_phone'";
	//$results=mysql_query($sqls);
	//$rows=mysql_fetch_row($results);
	//$user_head=$rows[0];
    $result = "INSERT INTO talk_information(user_phone,talk_time,talk_content,talk_url,phonety) VALUES('$user_phone', '$talk_time',  '$talk_content', '$talk_url', '$talk_ty')";
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
