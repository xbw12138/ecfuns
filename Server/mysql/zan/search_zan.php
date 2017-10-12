<?php
$response = array();
require("../db_config.php");
$conn=mysql_connect($mysql_server_name,$mysql_username,$mysql_password) or die("error connecting") ;
mysql_query("set names 'utf8'"); 
mysql_select_db($mysql_database);
if (isset($_POST['page'])&&isset($_POST['pl_num'])) {
	$page=(int)$_POST['page'];
	$pl_num=(int)$_POST['pl_num'];
	//$sqls="select user_name,user_head from user_information where user_phone='$user_phone'";
	//$results=mysql_query($sqls);
	//$rows=mysql_fetch_row($results);
	//$user_name=$rows[0];
	//$user_head=$rows[1];
	//$num=100;
	//$sqlz=;
	//$resultz=;
	$rowz=mysql_fetch_row(mysql_query("select count(*) from pl_information"));
	$num=$rowz[0]-$page*10;
	if($num>=0){
		$sql="select pl_time,pl_content,user_phone from pl_information where pl_num='$pl_num' limit $num , 10";
		if($result=mysql_query($sql)){
			while($row=mysql_fetch_array($result)){
				$sqls="select user_name,user_head from user_information where user_phone='$row[2]'";
				$results=mysql_query($sqls);
				$rows=mysql_fetch_row($results);
				$array[] = array( 
				'message'=>'YES',
				'pl_time'=>$row[0], 
				'pl_content'=>$row[1],
				'user_head'=>$rows[1], 
				'user_name'=>$rows[0],
				); 
			}
			echo json_encode($array);
		}else {
			$array = array( 
			'message'=>'NO',
			'success'=>0, 
			); 
			echo json_encode($array);
		}
		
	}else if($num>-10){
		$sql="select pl_time,pl_content,user_phone from pl_information where pl_num='$pl_num' limit 0 , 10";
		if($result=mysql_query($sql)){
			while($row=mysql_fetch_array($result)){
				$sqls="select user_name,user_head from user_information where user_phone='$row[2]'";
				$results=mysql_query($sqls);
				$rows=mysql_fetch_row($results);
				$array[] = array( 
				'message'=>'YES',
				'pl_time'=>$row[0], 
				'pl_content'=>$row[1],
				'user_head'=>$rows[1], 
				'user_name'=>$rows[0],
				); 
			}
			echo json_encode($array);
		}else {
			$array = array( 
			'message'=>'NO',
			'success'=>0, 
			); 
			echo json_encode($array);
		}
		
	}else{
		$array[] = array( 
		'message'=>'FULL',
		'pl_time'=>'', 
		'pl_content'=>'',
		'user_head'=>'', 
		'user_name'=>'',
		); 
        echo json_encode($array);
	}
} else {
	$array = array( 
	'message'=>'NO',
	'success'=>0, 
	); 
	echo json_encode($array);
}


?>