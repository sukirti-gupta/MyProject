<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form"
	prefix="springForm"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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

<title>BookStacks</title>





</head>
<body id="testing">

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
		<div id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="userhome"><small>Home</small></a></li>
				<security:authorize access="hasRole('ROLE_USER')">
					<li><a href="showBookshelf"><small>Bookshelf</small></a></li>
					<li><a href="profile"><small>Profile</small></a></li>
				</security:authorize>
				<%--                     <security:authorize access="hasRole('ROLE_ADMIN')"> --%>
				<!--  					<li> -->
				<!--                         <a href="#"><small>Books</small></a> -->
				<!--                     </li> -->
				<!--                     <li> -->
				<!--                         <a href="#"><small>User</small></a> -->
				<!--                     </li> -->
				<!--                     <li> -->
				<!--                         <a href="#"><small>Subscriptions</small></a> -->
				<!--                     </li> -->
				<%--  					</security:authorize>  --%>
				<li><c:url value="/j_spring_security_logout" var="logoutUrl" />
					<a href="${logoutUrl}"><small>Logout</small></a></li>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container --> </nav>

	<div class="container">
		<div class="box">
			<hr>
			<h2 class="intro-text text-center">
				<strong>Confirm Delivery Address</strong>
			</h2>
			<hr>
			<div class="col-md-6 text-right" style="height: 100px">Delivery
				Address</div>
			<div class="col-md-6" style="height: 100px">: ${address }</div>
			<div class="text-center">
				<button onclick="previous();">Proceed</button>
				<br> <br> <br>
			</div>
			<div class="col-md-5">
				<hr>
			</div>
			<div class="col-md-2 text-center">
				<strong>OR</strong>
			</div>
			<div class="col-md-5">
				<hr>
			</div>
			<form action="placenew" method="post" name="form"
				onsubmit="return validateForm()">
				<div class="col-md-6 text-right" style="height: 100px">New
					Address</div>
				<div class="col-md-6" style="height: 100px">
					<textarea rows="3" columns="2" name="address"></textarea>
				</div>
				<div class="text-center">
					<button>Proceed</button>
					<br> <br> <br>
				</div>
			</form>
		</div>
	</div>

	<script>
    	function previous() {
    		window.location = "placeprev";
	    };
    	
	    function validateForm() {
		    var x = document.forms["form"]["address"].value;
		    if (x.length<5 || x.length>100) {
		        alert("Address can only be between 5 to 100 characters");
		        return false;
		    }
		}
    </script>

	<!-- jQuery Version 1.11.0 -->
	<script src="<c:url value="/resources/js/custom.js"/>"></script>
	<script src="<c:url value="/resources/js/jquery-1.11.0.js"/>"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>

</body>
</html>