<?php
$response = array();
require("db_config.php");
$conn=mysql_connect($mysql_server_name,$mysql_username,$mysql_password) or die("error connecting") ;
mysql_query("set names 'utf8'"); 
mysql_select_db($mysql_database);
if (isset($_POST['user_phone'])&&isset($_POST['user_pwd'])) {
$user_phone = $_POST['user_phone'];
$user_pwd = $_POST['user_pwd'];
$sql="select user_phone,user_pwd from user_information where user_phone='$user_phone'";
$result = mysql_query($sql);
if(!mysql_num_rows($result))
{
	$response["success"] = 0;
        $response["message"] = "USERNO";
        echo json_encode($response);
}
else
{
	$row=mysql_fetch_assoc($result);
	if($user_pwd!=$row['user_pwd'])
	{
		$response["success"] = 0;
        	$response["message"] = "PWDNO";
        	echo json_encode($response);
	}
	else
	{
		$response["success"] = 1;
       	 	$response["message"] = "YES";
        	echo json_encode($response);
	}
}
}else {
    $response["success"] = 0;
    $response["message"] = "NO";
    echo json_encode($response);
}

?>