<!DOCTYPE html>
<%@include file="taglib.jsp" %>
<html>
<head>
<title>Login</title>
<link href="${rootURL}resources/bootstrap/css/bootstrap.css" media="screen" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${rootURL}resources/jquery/jquery-1.10.2.js"></script>
<script type="text/javascript" src="${rootURL}resources/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="${rootURL}resources/js/app.js"></script>
</head>
<body>
            
     <div class="row">
		  <button type="button" onclick="ac();">ssss</button>
	</div>
</div>

	<script>
		function ac(){
			$.ajax({
				url : "./rest/users",
				type : "GET",
				success : function(dt){
					console.log(dt);
//					setTimeout(function(){
//
//					},100000);


				},
				error : function(){
					alert("---==---");
				}
			})
		}

	</script>
</body>
</html>