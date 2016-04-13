<!DOCTYPE html>
<html>
<head>
<style>
#header {
    background-color:black;
    color:white;
    text-align:center;
    padding:5px;
}
#nav {
    line-height:30px;
    background-color:#eeeeee;
    height:500px;
    width:100px;
    float:left;
    padding:5px;	      
}
#section {
    width:350px;
    float:left;
    padding:10px;	 	 
}
#footer {
    background-color:black;
    color:white;
    clear:both;
    text-align:center;
   padding:5px;	 	 
}
</style>

</head>
<body>

<div id="header">
<h1>Global Positioning System</h1>
</div>

<div id="nav">
<form action="http://nazsakiba.info/Track/getLoc.php">
  <input type="radio" name="tranport_mode" value="COMMUTE" checked> Bus<br>
  <input type="radio" name="tranport_mode" value="DRIVING"> Car<br>
  <input type="radio" name="tranport_mode" value="CYCLING"> Cycle<br>
  <input type="radio" name="tranport_mode" value="PEDESTRIAN"> Padestrian<br>
</form> 
</div>

<div id="section">
<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
   <script type="text/javascript">
   var markers = [
    
            {
			   "TMode": 'CYCLING',
               "time": '2016-03-20 01:28:45',
			   "lat": '59.2172732',
               "lng": '10.3080276'
           }
    
       ,
    
            {
               "TMode": 'CYCLING',
			   "time": '2016-03-20 01:28:45',
               "lat": '59.2172834',
               "lng": '10.3081747'
           }
    
       ,
    
            {
               "TMode": 'CYCLING',
			   "time": '2016-03-20 01:30:11',
               "lat": '59.2173104',
               "lng": '10.3080363'
           }
		,   
		   {
			   "TMode": 'CYCLING',
               "time": '2016-03-20 01:30:11',
			   "lat": '59.2173092',
               "lng": '10.3080352'
           }
    
       ,
    
            {
               "TMode": 'CYCLING',
			   "time": '2016-03-20 01:30:11',
               "lat": '59.2173503',
               "lng": '10.3080963'
           }
    
       ,
    
            {
               "TMode": 'CYCLING',
			   "time": '2016-03-20 01:30:12',
               "lat": '59.2172586',
               "lng": '10.3080314'
           }
		,   
		   {
			   "TMode": 'DRIVING',
               "time": '2016-03-20 01:52:39',
			   "lat": '59.2172838',
               "lng": '10.3081391'
           }
    
       ,
    
            {
               "TMode": 'DRIVING',
			   "time": '2016-03-20 01:59:19',
               "lat": '59.2172624',
               "lng": '10.3080295'
           }
    
       ,
    
            {
               "TMode": 'DRIVING',
			   "time": '2016-03-20 01:59:19',
               "lat": '59.2173229',
               "lng": '10.3077622'
           }
		,   
		   {
			   "TMode": 'COMMUTE',
               "time": '2016-03-20 03:12:01',
			   "lat": '59.2173055',
               "lng": '10.3077426'
           }
    
       ,
    
            {
               "TMode": 'COMMUTE',
			   "time": '2016-03-20 03:12:01',
               "lat": '59.2173743',
               "lng": '10.3080686'
           }
    
       ,
    
            {
               "TMode": 'COMMUTE',
			   "time": '2016-03-20 03:12:01',
               "lat": '59.2173707',
               "lng": '10.3080362'
           }
		,   
		   {
			   "TMode": 'COMMUTE',
               "time": '2016-03-20 03:12:01',
			   "lat": '59.2175153',
               "lng": '10.3079891'
           }
    
       ,
    
            {
               "TMode": 'COMMUTE',
			   "time": '2016-03-20 03:12:01',
               "lat": '59.2172414',
               "lng": '10.3076857'
           }
    
       ,
    
            {
               "TMode": 'COMMUTE',
			   "time": '2016-03-20 03:12:01',
               "lat": '59.2174707',
               "lng": '10.3080339'
           }
		,   
		   {
			   "TMode": 'COMMUTE',
               "time": '2016-03-20 03:12:01',
			   "lat": '59.2108578',
               "lng": '10.3072192'
           }
    
       ,
    
            {
               "TMode": 'COMMUTE',
			   "time": '2016-03-20 03:12:01',
               "lat": '59.2172785',
               "lng": '10.308013'
           }
    
      
    
   ];
   </script>
   <?php
 if (empty($_POST["tranport_mode"])) {
     $modeErr = "Tranport mode is required";
   } else {
     $tmode = test_input($_POST["tranport_mode"]);
	 
   }
?>
   <script type="text/javascript">
 
       window.onload = function () {
           var mapOptions = {
               center: new google.maps.LatLng(markers[0].lat, markers[0].lng),
               zoom: 15,
               mapTypeId: google.maps.MapTypeId.ROADMAP
           };
           var path = new google.maps.MVCArray();
           var service = new google.maps.DirectionsService();
 
           var infoWindow = new google.maps.InfoWindow();
           var map = new google.maps.Map(document.getElementById("dvMap"), mapOptions);
           var poly = new google.maps.Polyline({ map: map, strokeColor: '#FF8200' });
           var lat_lng = new Array();
           for (i = 0; i < markers.length; i++) {
               var data = markers[i]
               var myLatlng = new google.maps.LatLng(data.lat, data.lng);
               lat_lng.push(myLatlng);
               var marker = new google.maps.Marker({
                   position: myLatlng,
                   map: map,
                   title: data.title
               });
               (function (marker, data) {
                   google.maps.event.addListener(marker, "click", function (e) {
                       infoWindow.setContent(data.time);
                       infoWindow.open(map, marker);
                   });
               })(marker, data);
           }
           for (var i = 0; i < lat_lng.length; i++) {
               if ((i + 1) < lat_lng.length) {
                   var src = lat_lng[i];
                   var des = lat_lng[i + 1];
                   path.push(src);
                   poly.setPath(path);
                   service.route({
                       origin: src,
                       destination: des,
                       travelMode: google.maps.DirectionsTravelMode.WALKING
                   }, function (result, status) {
                       if (status == google.maps.DirectionsStatus.OK) {
                           for (var i = 0, len = result.routes[0].overview_path.length; i < len; i++) {
                               path.push(result.routes[0].overview_path[i]);
                           }
                       }
                   });
               }
           }
       }
   </script>
   <div id="dvMap" style="width: 1100px; height: 500px">
   </div>
</div>

<div id="footer">
GPS Village
</div>

</body>
</html>
