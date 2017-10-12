<?php
$response = array();
require("db_config.php");
$conn=mysql_connect($mysql_server_name,$mysql_username,$mysql_password) or die("error connecting") ;
mysql_query("set names 'utf8'"); 
mysql_select_db($mysql_database);
if (isset($_POST['page'])) {
	$page=(int)$_POST['page'];
	$rowz=mysql_fetch_row(mysql_query("select count(*) from info"));
	$num=$rowz[0]-$page*10;
	if($num>=0){
		$sql="select url,title,content from info limit $num , 10";
		if($result=mysql_query($sql)){
			while($row=mysql_fetch_array($result)){
				$array[] = array( 
				'message'=>'YES',
				'url'=>$row[0], 
		
				'title'=>$row[1], 
				'content'=>$row[2],
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
		$sql="select url,title,content from info limit 0, $jj";
		if($result=mysql_query($sql)){
			while($row=mysql_fetch_array($result)){
				$array[] = array( 
				'message'=>'YES',
				'url'=>$row[0], 
				
				'title'=>$row[1], 
				'content'=>$row[2],
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
		'url'=>$row[0], 
		'title'=>$row[1], 
		'content'=>$row[2],
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