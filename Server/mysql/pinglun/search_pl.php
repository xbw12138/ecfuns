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
	$rowz=mysql_fetch_row(mysql_query("select count(*) from  pl_information where pl_num='$pl_num'"));
	$num=$rowz[0]-$page*10;
	if($num>=0){
		$sql="select pl_time,pl_content,user_phone,pl_zan,pl_id from pl_information where pl_num='$pl_num' limit $num , 10";
		if($result=mysql_query($sql)){
			while($row=mysql_fetch_array($result)){
				$sqls="select user_name,user_head from user_information where user_phone='$row[2]'";
				$results=mysql_query($sqls);
				$rows=mysql_fetch_row($results);
				$array[] = array( 
				'message'=>'YES',
				'pl_time'=>$row[0], 
				'pl_content'=>$row[1],
				'pl_zan'=>$row[3],
				'pl_id'=>$row[4],
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
		$jj=$num+10;
		$sql="select pl_time,pl_content,user_phone,pl_zan,pl_id from pl_information where pl_num='$pl_num' limit 0 , $jj";
		if($result=mysql_query($sql)){
			while($row=mysql_fetch_array($result)){
				$sqls="select user_name,user_head from user_information where user_phone='$row[2]'";
				$results=mysql_query($sqls);
				$rows=mysql_fetch_row($results);
				$array[] = array( 
				'message'=>'YES',
				'pl_time'=>$row[0], 
				'pl_content'=>$row[1],
				'pl_zan'=>$row[3],
				'pl_id'=>$row[4],
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
		'pl_zan'=>'',
		'pl_id'=>'',
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