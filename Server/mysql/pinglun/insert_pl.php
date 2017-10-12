<?php
$response = array();
require("../db_config.php");
$conn=mysql_connect($mysql_server_name,$mysql_username,$mysql_password) or die("error connecting") ;
mysql_query("set names 'utf8'"); 
mysql_select_db($mysql_database);
if (isset($_POST['user_phone']) && isset($_POST['pl_time'])&&isset($_POST['pl_content'])&&isset($_POST['pl_num'])) {
    $user_phone = $_POST['user_phone'];
    $pl_time = $_POST['pl_time'];	
    $pl_content = $_POST['pl_content'];
    $pl_num = $_POST['pl_num'];
    $result = "INSERT INTO pl_information(user_phone,pl_time,pl_content,pl_num) VALUES('$user_phone', '$pl_time',  '$pl_content', '$pl_num')";
mysql_query($result);
if ($result) {
		$re=mysql_fetch_array(mysql_query("select user_phone from talk_information where talk_id='$pl_num'"));
		if($user_phone!=$re[0]){
			$sqlsss="select user_token from user_information where user_phone='$re[0]'";
			$rowssss = mysql_fetch_array(mysql_query($sqlsss));
			$url="http://115.159.26.120/ecfun/mysql/push/push.php?id=1&token=".$rowssss[0];//推送用get请求完成
			access_url($url);
		}
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
function access_url($url) {  
    if ($url=='') return false;  
    $fp = fopen($url, 'r') or exit('Open url faild!');  
    if($fp){
    while(!feof($fp)) {  
        $file.=fgets($fp)."";
    }
    fclose($fp);  
    }
    return $file;
}
?>
