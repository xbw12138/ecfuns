<?php
$response = array();
require("../db_config.php");
$conn=mysql_connect($mysql_server_name,$mysql_username,$mysql_password) or die("error connecting") ;
mysql_query("set names 'utf8'"); 
mysql_select_db($mysql_database);
if (isset($_POST['user_phone'])&&isset($_POST['page'])) {
    $user_phone = $_POST['user_phone'];
	$page=(int)$_POST['page'];
	//$sqls="select user_name,user_head from user_information where user_phone='$user_phone'";
	//$results=mysql_query($sqls);
	//$rows=mysql_fetch_row($results);
	//$user_name=$rows[0];
	//$user_head=$rows[1];
	//$num=100;
	//$sqlz=;
	//$resultz=;
	$rowz=mysql_fetch_row(mysql_query("select count(*) from talk_information"));
	$num=$rowz[0]-$page*10;
	if($num>=0){
		$sql="select talk_time,talk_content,talk_url,user_phone,talk_id,talk_zan,phonety from talk_information limit $num , 10";
		if($result=mysql_query($sql)){
			while($row=mysql_fetch_array($result)){
				$sqls="select user_name,user_head from user_information where user_phone='$row[3]'";
				$rowzzz=mysql_fetch_row(mysql_query("select count(*) from pl_information where pl_num='$row[4]'"));
				$results=mysql_query($sqls);
				$rows=mysql_fetch_row($results);
				$array[] = array( 
				'message'=>'YES',
				'talk_time'=>$row[0], 
				'talk_content'=>$row[1],
				'talk_url'=>$row[2],
				'user_phone'=>$row[3],
				'talk_id'=>$row[4],
				'talk_zan'=>$row[5],
				'talk_ty'=>$row[6],
				'pl_nums'=>$rowzzz[0],
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
		$sql="select talk_time,talk_content,talk_url,user_phone,talk_id,talk_zan,phonety from talk_information limit 0, $jj";
		if($result=mysql_query($sql)){
			while($row=mysql_fetch_array($result)){
				$sqls="select user_name,user_head from user_information where user_phone='$row[3]'";
				$rowzzz=mysql_fetch_row(mysql_query("select count(*) from pl_information where pl_num='$row[4]'"));
				$results=mysql_query($sqls);
				$rows=mysql_fetch_row($results);
				$array[] = array( 
				'message'=>'YES',
				'talk_time'=>$row[0], 
				'talk_content'=>$row[1],
				'talk_url'=>$row[2], 
				'user_phone'=>$row[3],
				'talk_id'=>$row[4],
				'talk_zan'=>$row[5],
				'talk_ty'=>$row[6],
				'pl_nums'=>$rowzzz[0],
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
		'talk_time'=>'', 
		'talk_content'=>'',
		'talk_url'=>'', 
		'user_phone'=>'',
		'talk_id'=>'',
		'talk_zan'=>'',
		'talk_ty'=>'',
		'pl_nums'=>'',
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