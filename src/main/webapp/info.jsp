<html>
<head>
  <!--Import Google Icon Font-->
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <!--Import materialize.css-->
  <link type="text/css" rel="stylesheet" href="css/materialize.min.css"  media="screen,projection"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <meta charset="utf-8">
  <title>raspberry pi door opener</title>
</head>
<body>
    <div class="card-panel ${backgroundcolor}" style="width: 500px;  margin: auto; margin-top: 100px; height:325px;">
    	<i class="material-icons" style="width: 100%; height:40%; margin-top:20%">
    		<p class="${textcolor} lighten-1" style="font-size:96px; text-align: center; margin-top:0">${message}</p>
    	</i>
		<p class="white-text" style="font-size:24px; text-align: center;">${message2}</p>
    </div>
    
    <!--Import jQuery before materialize.js-->
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="js/materialize.min.js"></script>
</body>
</html>