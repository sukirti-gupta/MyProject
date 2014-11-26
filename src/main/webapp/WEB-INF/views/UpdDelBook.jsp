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
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container --> </nav>

	<div class="container">

		<div class="address-bar">
			<div class="row">
				<!--             	<div class="col-md-8"> -->
				<form action="find" method="get">
					<center>
						<input type="search" name="book"
							style="width: 27%; color: black" required>
							<button type="submit" style="color:black">Search</button>
					</center>
				</form>
				<br>
			</div>
		</div>

		<div class="box">
			<div class="col-lg-12">
				<hr>
				<h2 class="intro-text text-center">
					<strong>Books relating to ${category }</strong>
				</h2>
				<hr>
				<br>
				<h2 class="brand-before">
					<small style="color: red">${error }</small>
				</h2>
				<br>

				<c:forEach var="b" items="${books }">

					<div class="col-md-3 text-center" style="height: 300px">
						<img class="img-responsive img-border text-center"
							src="<c:url value="${b.image }"/>" alt=${b.name } width=80px
							height=120px onclick="myFunction(${b.bookid})"> <strong><small>${b.name }</small></strong><br>
						<button onclick="update(${b.bookid});">Update</button>
						<c:set var="status" value="${b.exist }"/>
						<c:set var="a" value="true"/>
						<c:if test="${status.equals(a) }">
						<button onclick="dltbook(${b.bookid})">Delete</button>
						</c:if>
					</div>
					<div class="col-md-9" style="height: 300px">
						<p style="font-size: 15px">
							<small>By</small> <strong><small><a
									href="http://en.wikipedia.org/wiki/${b.author }">${b.author }</a></small></strong><br>
							<small>Published by <strong>${b.publisher }</strong></small><br>
							<br> <small>Category <strong>${b.category }</strong></small><br>
							<small>Available copies <strong>${b.avail }</strong></small><br>
							<small>Exist <strong>${b.exist }</strong></small><br> <br>
							<small>${b.description }</small>
						</p>
					</div>

				</c:forEach>
			</div>
		</div>
	</div>

	<script>
    	function update(bookid) {
			
			window.location = "toupdate?bookid=".concat(bookid);
    	}
		
		function dltbook(bookid) {
	    	var data = 'bookid=' + encodeURIComponent(bookid);
		       
		       $.ajax({
		              url : "dltbook",
		              data : data,
		              type : "GET",
		              success : function(ajaxresponse) {
		            	  $('#testing').load(location.href);
		            	  			alert("Book deleted !");
		              },
		              error : function(xhr, status, error) {
		                     //alert(xhr.responseText);
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
