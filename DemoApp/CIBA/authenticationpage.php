<!DOCTYPE html>
<html lang="en">
<head>
<title>Pay Here</title>
 <!-- Meta-Tags -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta charset="utf-8">
    <meta name="keywords" content="Business Login Form a Responsive Web Template, Bootstrap Web Templates, Flat Web Templates, Android Compatible Web Template, Smartphone Compatible Web Template, Free Webdesigns for Nokia, Samsung, LG, Sony Ericsson, Motorola Web Design">

	
    <!-- //Meta-Tags -->
	
	<!-- css files -->
	<link href="css/cibacss.css" rel="stylesheet" type="text/css" media="all"/>
	<link href="css/xcss.css" rel="stylesheet" type="text/css" media="all"/>
	 
  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
 	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all"/>
<link href="scss/_variables.scss" rel="stylesheet" type="text/css" media="all"/>
<link href="scss/_bootswatch.scss" rel="stylesheet" type="text/css" media="all"/>

<script src="//cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.2/rollups/hmac-sha256.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.2/components/enc-base64-min.js"></script>
  	
 <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<link href="css/cibahome.css" rel="stylesheet" type="text/css" media="all"/>
<link href="css/split.css" rel="stylesheet" type="text/css" media="all"/>
<link href="css/payhere.css" rel="stylesheet" type="text/css" media="all"/>
<link href="css/nowterminal.css" rel="stylesheet" type="text/css" media="all"/>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!------ Include the above in your HEAD tag ---------->
 <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css" integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
	<!------ Include the above in your HEAD tag ----------

	<!-- //css files -->
	
	<!-- google fonts -->
	<link href="//fonts.googleapis.com/css?family=Raleway:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
	<!-- //google fonts -->
	
</head>
<script>
var poll_count=0;
var setinterval;
var request;
var auth_reponse;
var token_request;
var token_response;
var user;

function base64url(source) {
  // Encode in classical base64
  encodedSource = CryptoJS.enc.Base64.stringify(source);
  
  // Remove padding equal characters
  encodedSource = encodedSource.replace(/=+$/, '');
  
  // Replace characters according to base64url specifications
  encodedSource = encodedSource.replace(/\+/g, '-');
  encodedSource = encodedSource.replace(/\//g, '_');
  
  return encodedSource;
}


function sendRequest() {
	console.log("Identity Received");
	var data = null;
        var xmlhttp = new XMLHttpRequest();

const urlParams = new URLSearchParams(window.location.search);
 user = urlParams.get('user'); 	
document.getElementById("user").innerHTML=("User :  "+ user);

var header = {
  "alg": "HS256",
  "typ": "JWT"
};

var data = {

 "iss": "s6BhdRkqt3",
 "aud": "https://server.example.com",
 "exp": 1568031148,
 "iat": 1537819486,
 "nbf": 1537818886,
 "jti": "4LTCqACC2ESC5BWCnN3j58EnA",
 "scope": "openid email example-scope",
 "client_notification_token": "8d67dc78-7faa-4d41-aabd-67707b374255",
 "binding_message": "W4SCT",
 "login_hint": user
};

var secret = "My very confidential secret!!!";

var stringifiedHeader = CryptoJS.enc.Utf8.parse(JSON.stringify(header));
var encodedHeader = base64url(stringifiedHeader);


var stringifiedData = CryptoJS.enc.Utf8.parse(JSON.stringify(data));
var encodedData = base64url(stringifiedData);


var signature = encodedHeader + "." + encodedData;
signature = CryptoJS.HmacSHA256(signature, secret);
signature = base64url(signature);

  request = encodedHeader+"."+encodedData+"."+signature ;

	xmlhttp.open("POST", "http://localhost:8080/CIBAEndPoint?request="+request);
	
document.getElementById("CIBAauthRequest").innerHTML=("Authentication Request sent. "+" <br />" + " <br />" +"Authentication Request : "+"http://localhost:8080/CIBAEndPoint?request="+request);


	xmlhttp.setRequestHeader("Content-Type", "text/plain;charset=UTF-8");
    	xmlhttp.onreadystatechange = function() {
		
        if (this.readyState === 4 && this.status === 200) {
			console.log("Authentication Response Received")
			var response = this.responseText;
			var json = JSON.parse(response);
			var auth_req_id = json.auth_req_id;
			var interval = json.interval;


			console.log("Auth_req ID",auth_req_id)
                        //document.getElementById("CIBAauthRequest").innerHTML=("");	
			document.getElementById("CIBAauthResponse").innerHTML=("Authentication Response received. "+" <br />"  + " <br />" +"Authentication Response : "+response);	
			 auth_reponse = response;
			setinterval= setInterval(function(){tokenRequest(auth_req_id)},5000);

		} else{
			document.getElementById("CIBAauthResponse").innerHTML=("Error  : "+response);

			console.log(this.responseText);
        }
};
console.log("Authentication Request Sent");
   
    xmlhttp.send(data);

};
    
	function tokenRequest(auth_req_id){
token_request = " http://localhost:8080/TokenEndPoint?auth_req_id="+auth_req_id+"&grant_type=urn:openid:params:grant-type:ciba" ;
		document.getElementById("polling_status").innerHTML=("");
		document.getElementById("tokenRequest").innerHTML=("");
		document.getElementById("tokenResponse").innerHTML=("");
		poll_count++;
		console.log("Token Request posted");
		document.getElementById("polling_status").innerHTML=("Initiated Polling. "+" <br />" + " <br />" +"Polling Status : ("+poll_count +"). Polling for Token . . .");
		document.getElementById("tokenRequest").innerHTML=("Token Request Sent."+" <br />" + " <br />" +" Token Request : http://localhost:8080/TokenEndPoint?auth_req_id="+auth_req_id+"&grant_type=urn:openid:params:grant-type:ciba");

		var data1=null
		
		var xmlhttp = new XMLHttpRequest();
		xmlhttp.open("POST", "http://localhost:8080/TokenEndPoint?auth_req_id="+auth_req_id+"&grant_type=urn:openid:params:grant-type:ciba", true);
		xmlhttp.onreadystatechange = function() {
        		if (this.readyState == 4 && this.status == 200) {
			console.log(this.responseText)
			var response = this.responseText;
			var json = JSON.parse(response);
			var id_token = json.ID_token;

			if (response.includes("id_token")){
				clearInterval(setinterval);
				console.log("Token received");
				document.getElementById("tokenResponse").innerHTML=("Token Response recieved."+" <br />" + " <br />" +"Token Response : " +response);
				token_response = response;
                               window.location.replace("http://localhost/CIBA/resultpage.php?CIBAauthRequest="+request+"&CIBAauthResponse="+auth_reponse+"&tokenRequest="+token_request+"&tokenResponse="+token_response+"&user="+user);

			}else{
				document.getElementById("tokenResponse").innerHTML=("Pending Authentication . . .");
				};

			
			}else{
				//window.open("ErrorPage.php);
				console.log(this.responseText);
				document.getElementById("tokenResponse").innerHTML=(response);
			}	
        	};
		xmlhttp.send(data1);
	};

    </script>
<body onload="sendRequest()">
<section class="container">
  <div class="left-half">
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
  <a class="navbar-brand" href="#">VShop Console</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor02" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarColor01">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">Features</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">Pricing</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">Sales</a>
      </li>
    </ul>
    <form class="form-inline my-2 my-lg-0">
      <input class="form-control mr-sm-2" type="text" placeholder="Search">
      <button class="btn btn-secondary my-2 my-sm-0" type="submit">Search</button>
    </form>
  </div>
</nav>

                    <div>
                         
        			<img src="/CIBA/Images" alt="Logo" height=100px; width=100px>
                     </div>       
   
 
            <div style = "position:absolute; left:20px; top:200px;  color=#FFA500 height=400px; width=400px;">
                    <h3 class="card-title" id = "user"> </h3> 
                   <p>Transaction ID : 1908</p>
	<p>Waiting for Authentication......</p>

                </div>
       
      

       <div id="circle">

<img src="/CIBA/home.png" alt="PayHere" height=400px; width=400px ; style = "position:absolute; left:10px; top:20px;" >
  <div class="loader">
				    <div class="loader">
					<div class="loader">
					   <div class="loader">

					   </div>
					</div>
				    </div>
				  </div>
			   </div> 
                    


  </div>
  <div class="right-half">
	<div class="window">
	  <div class="terminal">
	<h1>Developer Mode </h1>
	    <p class="log">
	<span>
	<p> Location: PayHere Console</br>
	    State: Waiting for User authentication...</p>
	<p class="log"id="CIBAauthRequest"></p>
	<p class="log" id="CIBAauthResponse"></p>
	<p class="log" id="polling_status"></p>
	<p class="log" id="tokenRequest"></p>
	<p class="log" id="tokenResponse"></p>
      </span>
	    </p>
	  </div>
	</div>
 </div>
</section>
 
</body>
</html>
