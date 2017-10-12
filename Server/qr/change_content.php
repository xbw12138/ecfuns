<?php
$response = array();
require("db_config.php");
$conn=mysql_connect($mysql_server_name,$mysql_username,$mysql_password) 

or die("error connecting") ;
mysql_query("set names 'utf8'"); 
mysql_select_db($mysql_database);

if (isset($_POST['qr_url'])&&isset($_POST['qr_content'])) {
    $qr_url = $_POST['qr_url'];
    $qr_content = $_POST['qr_content'];
    $sqls="update qr_information set qr_content='$qr_content' where qr_url='$qr_url'";
    $result=mysql_query($sqls);
    if($result){
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