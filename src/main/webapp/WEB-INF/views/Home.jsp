<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form"
	prefix="springForm"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<!-- Bootstrap Core CSS -->
<link href="<c:url value="/resources/css/bootstrap.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/bootstrap.min.css" />"
	rel="stylesheet">
<%--     <link href="<c:url value="/resources/css/bootstrap2.css" />" rel="stylesheet"> --%>
<%--     <link href="<c:url value="/resources/css/custom.css" />" rel="stylesheet"> --%>

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

<title>BookStacks</title>
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
				<li><a href="home">Home</a></li>
				<li><a href="#">About</a></li>
				<li><a href="#">Contact</a></li>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container --> </nav>

	<div class="container">

		<div class="address-bar">
			<div class="row">
				<!--             	<div class="col-md-8"> -->
				<form action="displayBooks" method="get">
					<br><input type="search" name="book"
						placeholder="Search by Title, Author and Category"
						style="width: 27%; color: black" required>
						<button type="submit" style="color:black">Search</button>
				</form>
				<h2 class="brand-before">
					<small style="color: yellow">${message }</small>
				</h2>
				<br> <a href="login2" style="color: white" id="login">Sign
					In |</a> <a href="homeToRegister" style="color: white"> Sign Up</a>
			</div>
		</div>

		<div class="row">
			<div class="box">
				<div class="col-lg-12 text-center">

					<img
						src="<c:url value="http://tmhome.com/wp-content/uploads/2013/08/tumblr_m6c3ipVPWl1romywno1_500.gif" />"
						alt="">


					<h2 class="brand-before">
						<small>Welcome to</small>
					</h2>
					<h1 class="brand-name" style="color: black;">BookStacks</h1>
					<hr class="tagline-divider">
					<h2>
						<small>By <strong>Impetus</strong>
						</small>
					</h2>
				</div>
			</div>
		</div>

		<div class="box" style="max-width: 200%">
			<div class="col-lg-6">
				<h2 class="intro-text text-center">
					<strong>New</strong>
				</h2>

				<c:forEach var="n" items="${newbooks }">
					<div class="col-sm-4 text-center" style="height: 200px">
						<img class="img-responsive img-border"
							src="<c:url value="${n.image }"/>" width=80px height=120px
							onclick="myFunction(${n.bookid})"> <small>${n.name }</small>
					</div>
				</c:forEach>
			</div>

			<div class="col-lg-6">
				<h2 class="intro-text text-center">
					<strong>Popular</strong>
				</h2>

				<c:forEach var="b" items="${popular }">
					<div class="col-sm-4 text-center" style="height: 200px">
						<img class="img-responsive img-border text-center"
							src="<c:url value="${b.image }"/>" width=80px height=120px
							onclick="myFunction(${b.bookid})"> <small>${b.name }</small>
					</div>
				</c:forEach>
			</div>
		</div>

	</div>

	<script>
			        function myFunction(bookid) {
				    	window.location = "requestedBook?bookid=".concat(bookid);
				    	
					};
	</script>

	<!-- jQuery Version 1.11.0 -->
	<script src="<c:url value="/resources/js/custom.js"/>"></script>
	<script src="<c:url value="/resources/js/jquery-1.11.0.js"/>"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>

</body>
</html>


