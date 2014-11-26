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
				<li><c:url value="/j_spring_security_logout" var="logoutUrl" />
					<a href="${logoutUrl}"><small>Logout</small></a></li>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container --> </nav>

	<h2 class="brand-before">
		<small style="color: yellow">${message }</small>
	</h2>

	<div class="container">
		<div class="box">
			<c:forEach var="s" items="${subs }">
				<div class="col-lg-6" style="height: 250px">
					<hr>
					<h2 class="intro-text text-center">
						<strong>${s.subName }</strong>
					</h2>
					<hr>
					<div style="height: 70px">
						<div class="col-md-6 text-right">Duration</div>
						<div class="col-md-6">: ${s.subDuration } month(s)</div>
						<div class="col-md-6 text-right">Maximum Books</div>
						<div class="col-md-6">: ${s.maxBooks }</div>
						<div class="col-md-6 text-right">Amount</div>
						<div class="col-md-6">: Rs. ${s.subAmount }</div>
					</div>
					<div class="col-md-12 text-center">
						<button onclick="subscribe(${s.subid});">Subscribe</button>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>

	<script>
    	function subscribe(subid) {
    		
    		var data = 'subid=' + encodeURIComponent(subid);
			    $.ajax({
			              url : "subscribe",
			              data : data,
			              type : "GET",
			              success : function(response) {
			            	  alert("Subscription plan updated");
			              			},
			              error : function(xhr, status, error) {
			                     		alert("error");
			              			}
			       });
			       return false;
			};
    </script>
    
    <!-- jQuery Version 1.11.0 -->
	<script src="<c:url value="/resources/js/custom.js"/>"></script>
	<script src="<c:url value="/resources/js/jquery-1.11.0.js"/>"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>

</body>
</html>