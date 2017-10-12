<?php
$response = array();
require("db_config.php");
$target_path  = "./upload/";//接收文件目录
$target_path = $target_path . basename( $_FILES['uploadedfile']['name']);
if(move_uploaded_file($_FILES['uploadedfile']['tmp_name'], $target_path)) {
   echo "The file ".  basename( $_FILES['uploadedfile']['name']). " has been uploaded";

	$str=basename( $_FILES['uploadedfile']['name']);//获取文件名
  	$response["status"] = 1;
	$url="http://115.159.26.120/ecfun/image_head/upload/{$str}";//获取文件url路径
	$response["imageUrl"] = $url;
        echo json_encode($response);


	preg_match_all('/_(.*?)_/', $str, $matches);//匹配手机号
	$conn=mysql_connect($mysql_server_name,$mysql_username,$mysql_password) or die("error connecting") ;
	mysql_query("set names 'utf8'"); 
	mysql_select_db($mysql_database);
	$phone=$matches[1];//数组转字符串
	$sql="update talk_information set user_head='$url' where user_phone='$phone[0]'";//手机号查找图片
	$result=mysql_query($sql,$conn);
	$sqls="update user_information set user_head='$url' where user_phone='$phone[0]'";//手机号查找图片
	$results=mysql_query($sqls,$conn);
	if ($result&&$results) {
        	$response["status"] = 1;
        	$response["message"] = "success";
		$response["imageUrl"] = $url;
        	echo json_encode($response);
    	} else {
        	$response["status"] = 0;
        	$response["message"] = "insert_fail";
		$response["imageUrl"] = "null";
        	echo json_encode($response);
    	}
	
}  else{
	$response["status"] = 0;
	$response["message"] = "upload_fail";
        $response["imageUrl"] = "null";
        echo json_encode($response);
}
?>
