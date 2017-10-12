<?php
$response = array();
require("db_config.php");
$target_path  = "./upload/";//接收文件目录
$target_path = $target_path . basename( $_FILES['uploadedfile']['name']);
if(move_uploaded_file($_FILES['uploadedfile']['tmp_name'], $target_path)) {
   echo "The file ".  basename( $_FILES['uploadedfile']['name']). " has been uploaded";

	$str=basename( $_FILES['uploadedfile']['name']);//获取文件名
  	$response["status"] = 1;
	$url="http://115.159.26.120/ecfun/image_bg_upload/upload/{$str}";//获取文件url路径
	$response["imageUrl"] = $url;
        echo json_encode($response);
	
}  else{
	$response["status"] = 0;
	$response["message"] = "upload_fail";
        $response["imageUrl"] = "null";
        echo json_encode($response);
}
//$input = file_get_contents('php://input'); 
//file_put_contents('c:/c.txt', $input); //$original为服务器上的文件
//$base_path = "./uploads/"; //接收文件目录
//$target_path = $base_path . basename ( $_FILES ['uploadfile'] ['name'] );
//if (move_uploaded_file ( $_FILES ['uploadfile'] ['tmp_name'], $target_path )) {
//	$array = array ("code" => "1", "message" => $_FILES ['uploadfile'] ['name'] );
//	echo json_encode ( $array );
//} else {
//	$array = array ("code" => "0", "message" => "There was an error uploading the file, please try again!" . $_FILES ['uploadfile'] ['error'] );
//	echo json_encode ( $array );
//}
?>
