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
			<br> <br>
			<springForm:form commandName="bd" class="form-horizontal" role="form"
				method="post" action="add" name="form" enctype="multipart/form-data">
				<div class="form-group">
					<label class="control-label col-sm-3" for="name">Name</label>
					<div class="col-sm-9">
						<springForm:input type="text" class="form-control" path="name"
							placeholder="Book name" />
						<springForm:errors path="name" cssClass="error" />
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-3" for="auth">Author</label>
					<div class="col-sm-9">
						<springForm:input type="text" class="form-control" path="author"
							placeholder="Author" />
						<springForm:errors path="author" cssClass="error" />
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-3" for="publ">Publisher</label>
					<div class="col-sm-9">
						<springForm:input type="text" class="form-control"
							path="publisher" placeholder="Publisher" />
						<springForm:errors path="publisher" cssClass="error" />
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-3" for="cat">Category</label>
					<div class="col-sm-9">
						<springForm:input type="text" class="form-control" path="category"
							placeholder="Category" />
						<springForm:errors path="category" cssClass="error" />
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-3" for="lang">Language</label>
					<div class="col-sm-9">
						<springForm:input type="text" class="form-control" path="language"
							placeholder="Language" />
						<springForm:errors path="language" cssClass="error" />
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-3" for="desc">Description</label>
					<div class="col-sm-9">
						<springForm:textarea class="form-control" rows="3"
							path="description" />
						<springForm:errors path="description" cssClass="error" />
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-3" for="number">Available
						Copies</label>
					<div class="col-sm-9">
						<springForm:input type="text" class="form-control" path="avail"
							placeholder="Available copies" />
						<springForm:errors path="avail" cssClass="error" />
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-3" for="exist">Exist</label>
					<div class="col-sm-9">
						<springForm:input type="text" class="form-control" path="exist"
							placeholder="true or false" />
						<springForm:errors path="exist" cssClass="error" />
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-3" for="image">Image</label>
					<div class="col-sm-9">
						<springForm:input type="text" class="form-control" path="image"
							placeholder="Give image link" />
						<springForm:errors path="image" cssClass="error" />
					</div>
					<br> <br> <br>
					<div class="col-sm-offset-5 col-sm-7">
						<button type="submit">Save</button>
					</div>
				</div>
			</springForm:form>
		</div>
	</div>

	<!-- 	<script> -->
	<!-- // 	function validateForm() { -->
	<!-- // 	    var x = document.forms["form"]["name"].value; -->
	<!-- // 	    if (x.length<3) { -->
	<!-- // 	        alert("Name cannot be shorter than 3 characters"); -->
	<!-- // 	        return false; -->
	<!-- // 		} -->
	<!-- // 	    if (x.length>20) { -->
	<!-- // 	        alert("Name cannot be greater than 20 characters"); -->
	<!-- // 	        return false; -->
	<!-- // 	    } -->

	<!-- // 	    x = document.forms["form"]["username"].value; -->
	<!-- // 	    if (x.length<3) { -->
	<!-- // 	        alert("Username cannot be shorter than 3 characters"); -->
	<!-- // 	        return false; -->
	<!-- // 		} -->
	<!-- // 	    if (x.length>10) { -->
	<!-- // 	        alert("Username cannot be greater than 10 characters"); -->
	<!-- // 	        return false; -->
	<!-- // 	    } -->

	<!-- // 	    x = document.forms["form"]["password"].value; -->
	<!-- // 	    if (x.length<3) { -->
	<!-- // 	        alert("Password cannot be shorter than 3 characters"); -->
	<!-- // 	        return false; -->
	<!-- // 		} -->
	<!-- // 	    if (x.length>15) { -->
	<!-- // 	        alert("Password cannot be greater than 15 characters"); -->
	<!-- // 	        return false; -->
	<!-- // 	    } -->

	<!-- // 	    x = document.forms["form"]["password"].value; -->
	<!-- // 	    y = document.forms["form"]["confirmpswd"].value; -->
	<!-- // 	    if (x != y) { -->
	<!-- // 	    	document.forms["form"]["password"].style.borderColor = "#E34234"; -->
	<!-- // 	        document.forms["form"]["confirmpswd"].style.borderColor = "#E34234"; -->
	<!-- // 	        alert("Passwords do not match"); -->
	<!-- // 	        return false; -->
	<!-- // 	    } -->

	<!-- // 	    x = document.forms["form"]["eid"].value; -->
	<!-- // 	    var atpos = x.indexOf("@"); -->
	<!-- // 	    var dotpos = x.lastIndexOf("."); -->
	<!-- // 		if (atpos< 1 || dotpos<atpos+2 || dotpos+2 >= x.length) { -->
	<!-- //         alert("Not a valid e-mail address"); -->
	<!-- //         return false; -->
	<!-- // 		} -->

	<!-- // 		x = document.forms["form"]["address"].value; -->
	<!-- // 		if (x.length<5) { -->
	<!-- // 	        alert("Address too short"); -->
	<!-- // 	        return false; -->
	<!-- // 		} -->
	<!-- // 	    if (x.length>200) { -->
	<!-- // 	        alert("Address too long"); -->
	<!-- // 	        return false; -->
	<!-- // 	    } -->

	<!-- // 	    x = document.forms["form"]["contact"].value; -->
	<!-- // 	    if (x.length<10 || x.length>10) { -->
	<!-- // 	        alert("Contact no. must be of 10 characters"); -->
	<!-- // 	        return false; -->
	<!-- // 	    } -->
	<!-- // 	} -->

	<!-- // 		$(function() { -->
	<!-- // 		    $( "#accordion" ).accordion({ event: "mouseup" }); -->
	<!-- // 		  }); -->
	<!-- 	</script> -->
</body>
</html>