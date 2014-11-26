<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ taglib uri="http://www.springframework.org/tags/form"
	prefix="springForm"%>
<!DOCTYPE html>
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
	href="//cdn.datatables.net/1.10.3/css/jquery.dataTables.min.css">

<script src="//code.jquery.com/jquery-1.11.1.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="<c:url value="/resources/js/bootstrap.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<script src="//cdn.datatables.net/1.10.3/js/jquery.dataTables.min.js"></script>
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
			<div id="bs-example-navbar-collapse-1" class="dropdown">
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
		<!-- /.container -->
	</nav>

	<div class="container">

		<div class="address-bar">
			<div class="row">
				<!--             	<div class="col-md-8"> -->
				<form action="displayBooks" method="get">
					<br> <label>Search </label> <input type="search" name="book"
						placeholder="Search by Title, Author and Category"
						style="width: 27%; color: black" required>
				</form>
				<br>
				<h2 class="brand-before">
					<small style="color: yellow">${message }</small>
				</h2>
				<br>

				<security:authorize access="hasRole('ROLE_USER')">
					<%
           	out.print("Welcome  " +session.getAttribute("usersession") +"!");
            %>
				</security:authorize>
			</div>
		</div>

		<div class="container">
			<div class="box">
				<div class="col-lg-12">
					<hr>
					<h2 class="intro-text text-center">
						<strong>User Information</strong>
					</h2>
					<hr>
					<br>
					<table id="active">
						<thead>
							<tr>
								<td>REQUEST ID</td>
								<td>NAME</td>
								<td>REQUEST TYPE</td>
								<td>REQUEST STATUS</td>
								<td>DATE PLACED</td>
								<td></td>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="n" items="${name}" varStatus="i">
								<c:forEach var="r" items="${req }" begin="${i.index}"
									end="${i.index}">
									<tr>
										<td align="center">${r.reqid }</td>
										<td align="center">${n.name}</td>
										<td align="center">${r.reqType}</td>
										<td align="center" id="status">${r.reqStatus }</td>
										<td align="center"><fmt:formatDate
												value="${r.startDate }" pattern="dd-MM-yyyy" /></td>
										<td align="center"><c:if
												test="${r.reqStatus.equals('pending') }">
												<button onclick="closereq(${r.reqid})">Close
													request</button>
											</c:if></td>
									</tr>
								</c:forEach>
							</c:forEach>
						</tbody>
					</table>

				</div>

			</div>
		</div>

	</div>

	<script>
		$(document).ready(function(){
	    		$('#active').DataTable();
	    });
	
	function closereq(reqid) {
		window.location = "reqStat?reqid=".concat(reqid);
	};
	</script>


</body>
</html>