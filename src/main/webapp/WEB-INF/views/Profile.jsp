<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
				<security:authorize access="hasRole('ROLE_USER')">
					<%
           	out.print("Welcome  " +session.getAttribute("usersession") +"!");
            %>
				</security:authorize>
				<br>

				<security:authorize access="hasRole('ROLE_ADMIN')">
					<label>Admin</label>
				</security:authorize>

			</div>
		</div>


		<div class="box">
			<c:forEach var="u" items="${user }">
				<div class="col-lg-12">
					<hr>
					<h2 class="intro-text text-center">
						<strong>Profile</strong>
					</h2>
					<hr>
					<div class="col-md-6 text-right"
						style="margin-top: 5px; margin-bottom: 5px">Name</div>
					<div class="col-md-6" style="margin-top: 5px; margin-bottom: 5px">:
						${u.name }</div>

					<div class="col-md-6 text-right"
						style="margin-top: 5px; margin-bottom: 5px">Email ID</div>
					<div id="eid" class="col-md-6"
						style="margin-top: 5px; margin-bottom: 5px">: ${u.eid }</div>
					<!-- 					<div id="neweid" class="col-md-4" style="margin-top:5px;margin-bottom:5px"><input type="text" name="eid" id="neweid"></div> -->
					<!-- 					<div class="col-md-2 text-left"><button onclick="editemail()">Edit</button></div> -->

					<div class="col-md-6 text-right"
						style="margin-top: 5px; margin-bottom: 5px">Contact</div>
					<div id="contact" class="col-md-6"
						style="margin-top: 5px; margin-bottom: 5px">: ${u.contact }</div>

					<div class="col-md-6 text-right"
						style="margin-top: 5px; margin-bottom: 5px">Address</div>
					<div id="address" class="col-md-6"
						style="margin-top: 5px; margin-bottom: 5px">: ${u.address }</div>

					<div class="col-lg-12">
						<hr>
						<h2 class="intro-text text-center">
							<strong>Subscription Enrolled</strong>
						</h2>
						<hr>
						<c:forEach var="us" items="${usersub }" varStatus="i">
							<c:forEach var="sd" items="${subDetails }" begin="${i.index}"
								end="${i.index}">

								<div class="col-md-6 text-right"
									style="margin-top: 5px; margin-bottom: 5px">Name</div>
								<div class="col-md-6"
									style="margin-top: 5px; margin-bottom: 5px">:
									${sd.subName }</div>

								<div class="col-md-6 text-right"
									style="margin-top: 5px; margin-bottom: 5px">Start Date</div>
								<div class="col-md-6"
									style="margin-top: 5px; margin-bottom: 5px">
									:
									<fmt:formatDate value="${us.startDate }" pattern="dd-MM-yyyy" />
								</div>

								<div class="col-md-6 text-right"
									style="margin-top: 5px; margin-bottom: 5px">End Date</div>
								<div class="col-md-6"
									style="margin-top: 5px; margin-bottom: 5px">
									:
									<fmt:formatDate value="${us.endDate }" pattern="dd-MM-yyyy" />
								</div>

								<div class="col-md-6 text-right"
									style="margin-top: 5px; margin-bottom: 5px">Books Left</div>
								<div class="col-md-6"
									style="margin-top: 5px; margin-bottom: 5px">:
									${us.booksLeft }</div>
								<div class="col-md-12">
									<hr>
								</div>
							</c:forEach>
						</c:forEach>
						<div class="col-md-12 text-center"
							style="margin-top: 5px; margin-bottom: 5px">
							<a href="subscription"><button>Change Subscription</button></a>

						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>

	<script>
    
//     document.getElementById("books_left").style.display = "none";
//     document.getElementById("eid").style.display = "block";
//     function editemail(){
//     	document.getElementById("neweid").style.display = "block";
//     	document.getElementById("eid").style.display = "none";
//     }
    
    
    </script>

	<!-- jQuery Version 1.11.0 -->
	<script src="<c:url value="/resources/js/custom.js"/>"></script>
	<script src="<c:url value="/resources/js/jquery-1.11.0.js"/>"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>

</body>
</html>