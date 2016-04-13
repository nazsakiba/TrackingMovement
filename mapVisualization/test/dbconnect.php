<?php
 define('HOST','localhost');
 define('USER','root');
 define('PASS','');
 define('DB','test');
 $con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
 
 //Creating sql query
 $sql = "SELECT * FROM markers";
 
 //getting result 
 $r = mysqli_query($con,$sql);
 
 //creating a blank array 
 $result = array();
 
/* //looping through all the records fetched
 while($row = mysqli_fetch_array($r)){
 
 //Pushing name and id in the blank array created 
 array_push($result,array(
 "id"=>$row['lat'],
 "name"=>$row['lng']
 ));
 }
 
 //Displaying the array in json format 
 echo json_encode(array('result'=>$result));
 */
 mysqli_close($con);
 ?>
 <!DOCTYPE html>
<html>
<head>
<script
src="http://maps.googleapis.com/maps/api/js">
</script>

<script>

<?php 
while($row = mysqli_fetch_array($r)){
 
 //Pushing name and id in the blank array created 
 array_push($result,array(
 ?>
var myCenter=new google.maps.LatLng(<?php $row['lat']?>,<?php $row['lng']?>);
 <?));
 }
 
 //Displaying the array in json format 
 echo json_encode(array('result'=>$result));
?> 

function initialize()
{
var mapProp = {
  center:myCenter,
  zoom:5,
  mapTypeId:google.maps.MapTypeId.ROADMAP
  };

var map=new google.maps.Map(document.getElementById("googleMap"),mapProp);

var marker=new google.maps.Marker({
  position:myCenter,
  });

marker.setMap(map);
}

google.maps.event.addDomListener(window, 'load', initialize);
</script>
</head>

<body>
<?php
 if (empty($_POST["tranport_mode"])) {
     $modeErr = "Tranport mode is required";
   } else {
     $tmode = test_input($_POST["tranport_mode"]);
   }
?>
<form action="http://nazsakiba.info/Track/getLoc.php">
  <input type="radio" name="tranport_mode" value="COMMUTE" checked> Bus<br>
  <input type="radio" name="tranport_mode" value="DRIVING"> Car<br>
  <input type="radio" name="tranport_mode" value="CYCLING"> Cycle<br>
  <input type="radio" name="tranport_mode" value="PEDESTRIAN"> Padestrian<br>
  
</form> 
<div id="googleMap" style="width:500px;height:380px;"></div>
</body>
</html>
