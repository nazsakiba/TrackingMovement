<?php
$servername="localhost";
$username="sakiba_sakiba";
$password="GPS_tracker";
$database="sakiba_track";

 /*define('HOST','localhost:3306');
 define('USER','sakiba_sakiba');
 define('PASS','GPS_tracker');
 define('DB','sakiba_track');*/

$conn = new mysqli($servername, $username, $password);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 
?>