<?php
	
	if(isset($_GET['filenames'])&&isset($_GET['content'])){
		$fname=$_GET['filenames'];
		$content=$_GET['content'];
		$k=fopen($fname.".txt","a+");
		fwrite($k,$content);
		fclose($k);		
	}




?>