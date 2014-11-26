<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form"
	prefix="springForm"%>
<!DOCTYPE html>
<html>
<head>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Bookstacks</title>

<!-- Bootstrap Core CSS -->
<link href="<c:url value="/resources/css/bootstrap.min.css" />"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="<c:url value="/resources/css/business-casual.css" />"
	rel="stylesheet">

<!-- Fonts -->
<link
	href="http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800"
	rel="stylesheet" type="text/css">
<link
	href="http://fonts.googleapis.com/css?family=Josefin+Slab:100,300,400,600,700,100italic,300italic,400italic,600italic,700italic"
	rel="stylesheet" type="text/css">

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.1/jquery-ui.js"></script>


<title>BookStacks</title>
<style>
.error {
	color: #ff0000;
}
</style>
</head>
<body>

	<div class="brand">Bookstacks</div>
	<div class="address-bar">Search | Order | Get the book at your
		doorstep</div>

	<!-- Navigation -->
	<nav class="navbar navbar-default" role="navigation">
		<div class="container">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<!-- navbar-brand is hidden on larger screens, but visible when the menu is collapsed -->
				<a class="navbar-brand" href="home">Bookstacks</a>
			</div>
			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li><a href="home"><small>Home</small></a></li>
					<li><a href="#"><small>About us</small></a></li>
					<li><a href="#"><small>Contact Us</small></a></li>
					<li><a href="homeToLogin"><small>Sign In</small></a></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container -->
	</nav>

	<div class="container" style="max-width: 30%;">

		<div class="address-bar">
			<div class="row">
				<h2 class="brand-before">
					<small style="color: yellow">${message }</small>
				</h2>
				<br>
			</div>
		</div>


		<div class="box">
			<div class="col-lg-12">
				<hr>
				<h2 class="intro-text text-center">
					<strong>Register</strong>
				</h2>
				<hr>
				<springForm:form commandName="ud" class="form-horizontal"
					role="form" method="post" action="register" name="form"
					onsubmit="validateForm()">
					<div class="form-group">
						<label class="control-label col-sm-3" for="name">Name</label>
						<div class="col-sm-9">
							<springForm:input type="text" class="form-control" path="name"
								placeholder="Enter name" required="true" />
							<springForm:errors path="name" cssClass="error" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-3" for="username">Username</label>
						<div class="col-sm-9">
							<springForm:input type="text" class="form-control"
								path="username" placeholder="Enter username" required="true" />
							<springForm:errors path="username" cssClass="error" />
							<h2 class="brand-before">
								<small style="color: red">${exists }</small>
							</h2>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-3" for="password">Password</label>
						<div class="col-sm-9">
							<springForm:input type="password" class="form-control"
								path="password" placeholder="Enter password" required="true" />
							<springForm:errors path="password" cssClass="error" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-3" for="confirmpswd">Confirm
							Password</label>
						<div class="col-sm-9">
							<input type="password" class="form-control"
								name="confirmPassword" placeholder="Confirm password"
								required="true" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-3" for="eid">Email-ID</label>
						<div class="col-sm-9">
							<springForm:input type="email" class="form-control" path="eid"
								placeholder="Enter email ID" required="true" />
							<springForm:errors path="eid" cssClass="error" />
							<h2 class="brand-before">
								<small style="color: red">${email }</small>
							</h2>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-3" for="address">Address</label>
						<div class="col-sm-9">
							<springForm:textarea class="form-control" rows="3" path="address"
								required="true" />
							<springForm:errors path="address" cssClass="error" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-3" for="contact">Contact</label>
						<div class="col-sm-9">
							<springForm:input type="tel" class="form-control" path="contact"
								placeholder="Enter contact" required="true" />
							<springForm:errors path="contact" cssClass="error" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-3" for="language">Preferred
							Languages</label>
						<div class="col-sm-9">
							<select name="language" multiple="yes" required="true">
								<option value="english">English</option>
								<option value="hindi">Hindi</option>
								<option value="sanskrit">Sanskrit</option>
								<option value="urdu">Urdu</option>
								<option value="other">Other</option>
							</select>
							<springForm:errors path="language" cssClass="error" />
						</div>
					</div>

					<div class="form-group">
						<label class="control-label col-sm-3" for="subscription">Subscription
							Plan</label>
						<div class="col-sm-9">
							Select from the below plans<br> <label
								style="color: #ff0000;">${emptySub }</label>
						</div>
					</div>
					<div id="accordion">
						<c:forEach var="s" items="${subs }">
							<div class="radio">
								<label class="control-label"><input type="radio"
									name="subscription" value="${s.subName }">${s.subName }</label>
							</div>
							<div>
								<p style="font-size: 15px">
									<small>Duration : ${s.subDuration }<br> Max books
										: ${s.maxBooks }<br> Amount : ${s.subAmount }
									</small>
								</p>
							</div>
						</c:forEach>
					</div>

					<div class="form-group">
						<div class="col-sm-offset-3 col-sm-9">
							<button type="submit" class="btn btn-default">Submit</button>
						</div>
					</div>
				</springForm:form>
			</div>
		</div>
	</div>

	<script>
		function validateForm() {
		    var x = document.forms["form"]["name"].value;
		    if (x.length<3) {
		        alert("Name cannot be shorter than 3 characters");
		        return false;
			}
		    if (x.length>20) {
		        alert("Name cannot be greater than 20 characters");
		        return false;
		    }
		    
		    x = document.forms["form"]["username"].value;
		    if (x.length<3) {
		        alert("Username cannot be shorter than 3 characters");
		        return false;
			}
		    if (x.length>10) {
		        alert("Username cannot be greater than 10 characters");
		        return false;
		    }
		    
		    x = document.forms["form"]["password"].value;
		    if (x.length<3) {
		        alert("Password cannot be shorter than 3 characters");
		        return false;
			}
		    if (x.length>15) {
		        alert("Password cannot be greater than 15 characters");
		        return false;
		    }
		    
		    x = document.forms["form"]["password"].value;
		    y = document.forms["form"]["confirmpswd"].value;
		    if (x != y) {
		    	document.forms["form"]["password"].style.borderColor = "#E34234";
		        document.forms["form"]["confirmpswd"].style.borderColor = "#E34234";
		        alert("Passwords do not match");
		        return false;
		    }
		    
		    x = document.forms["form"]["eid"].value;
		    var atpos = x.indexOf("@");
		    var dotpos = x.lastIndexOf(".");
			if (atpos< 1 || dotpos<atpos+2 || dotpos+2 >= x.length) {
	        alert("Not a valid e-mail address");
	        return false;
			}
			
			x = document.forms["form"]["address"].value;
			if (x.length<5) {
		        alert("Address too short");
		        return false;
			}
		    if (x.length>200) {
		        alert("Address too long");
		        return false;
		    }
		    
		    x = document.forms["form"]["contact"].value;
		    if (x.length<10 || x.length>10) {
		        alert("Contact no. must be of 10 characters");
		        return false;
		    }
		}
		
		  $(function() {
		    $( "#accordion" ).accordion({ event: "mouseup" });
		  });


	</script>
</body>
</html>