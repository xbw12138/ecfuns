<?php
$response = array();
require("db_config.php");
$conn=mysql_connect($mysql_server_name,$mysql_username,$mysql_password) 

or die("error connecting") ;
mysql_query("set names 'utf8'"); 
mysql_select_db($mysql_database);

if (isset($_GET['qr_id'])) {
    $qr_id = $_GET['qr_id'];
    $sqls="select is_use from qr_information where qr_id='$qr_id'";
    $rows = mysql_fetch_array(mysql_query($sqls));
    if($rows[0]=='N'){
	$sql="update qr_information set is_use='Y' where qr_id='$qr_id'";
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