<!DOCTYPE html>
<html lang="en">
<head>
<title>Pay Here</title>
 <!-- Meta-Tags -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all"/>

<link href="css/cibahome.css" rel="stylesheet" type="text/css" media="all"/>
<link href="css/split.css" rel="stylesheet" type="text/css" media="all"/>
<link href="css/payhere.css" rel="stylesheet" type="text/css" media="all"/>
<link href="css/rotate.css" rel="stylesheet" type="text/css" media="all"/>
<link href="scss/_variables.scss" rel="stylesheet" type="text/css" media="all"/>
<link href="scss/_bootswatch.scss" rel="stylesheet" type="text/css" media="all"/>
<link href="css/nowterminal.css" rel="stylesheet" type="text/css" media="all"/>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!------ Include the above in your HEAD tag ---------->
<!------ Include the above in your HEAD tag ---------->
 <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css" integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">

</head>
<body>
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
<div class="container h-100" style = "position:absolute; left:100px; top:200px;>
		<div class="d-flex justify-content-center h-100" >
			<div class="user_card1" >
				<div class="d-flex justify-content-center">
					
				<div class= "brand_logo"> 
						<img src="/CIBA/Images" alt="Logo"  height=150px; width=150px>
					</div>
				</div>
		
					<div  style = "position:relative; left:10px; top:20px;>
						
							<span class="input-group-text"><i class="fas fa-user">Transaction details</i></span>
							<p> Transaction Id : 1908</p>
							<p> Transaction Amount : Rs XXXX.XX</p>
						
						
						</div>
	<div class="d-flex justify-content-center form_container">
	                                      <form action=authenticationpage.php  method="get">
						<div>
						
							<span class="input-group-text"><i class="fas fa-user">User Identity</i></span>
							<input type="text" name="user" id="user" class="form-control input_user" value="" placeholder="">
						
						</div>

					<button type="submit" value="Submit" class="btn login_btn">PayHere</button>
				
					 </form>
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
	    State: Waiting for User identity...</p>
	      </span>
	    </p>
	  </div>
	</div>
	  </div>
</section>
 
</body>
</html>
