<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ taglib uri="http://www.springframework.org/tags/form"
	prefix="springForm"%>

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
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container --> </nav>

	<div class="container">

		<div class="address-bar">
			<div class="row">
				<form action="displayBooks" method="get">
					<br><input type="search" name="book"
						placeholder="Search by Title, Author and Category"
						style="width: 27%; color: black" required>
						<button type="submit" style="color:black">Search</button>
				</form>
				<br>
				<%
				    out.print("Welcome  " + session.getAttribute("usersession") + "!");
				%><br>
				<security:authorize access="hasRole('ROLE_ADMIN')">
					<label>Admin</label>
				</security:authorize>
				<br>
				<h2 class="brand-before">
					<small style="color: yellow">${add }</small>
				</h2>
				<br>
			</div>
		</div>


		<!-- 	        ----------------------------------------------------Bookshelf--------------------------------------------------------------- -->


		<div class="box" id="bookshelf">

			<div class="col-lg-12">
				<hr>
				<h2 class="intro-text text-center">
					<strong>Bookshelf</strong>
				</h2>
				<hr>
				<h2 class="brand-before">
					<small style="color: red">${message }</small>
				</h2>
				<br>
				<c:forEach var="b" items="${mylist }">
					<div class="col-md-4 text-center" style="height: 240px">
						<img class="img-responsive img-border "
							src="<c:url value="${b.image }"/>" alt=${b.name } width=80px
							height=120px onclick="myFunction(${b.bookid})"> <small>${b.name }</small><br>
						<br>
						<button onclick="myFunction1(${b.bookid});">Place Request</button>
						<br>
						<button onclick="myFunction2(${b.bookid});">Remove</button>
					</div>
				</c:forEach>
			</div>
		</div>


		<!-- 	----------------------------------------------------------Currently Reading------------------------------------------------------------------	         -->

		<div class="box">

			<div class="col-lg-12">
				<hr>
				<h2 class="intro-text text-center">
					<strong>Currently Reading</strong>
				</h2>
				<hr>
				<br>
				<h2 class="brand-before">
					<small style="color: red">${current }</small>
				</h2>
				<br>

				<c:forEach var="r" items="${userReq1 }" varStatus="i">
					<c:forEach var="h" items="${holding }" begin="${i.index}"
						end="${i.index}">
						<div class="col-md-2 text-center" style="height: 200px">
							<img class="img-responsive img-border text-center"
								src="<c:url value="${h.image }"/>" alt=${h.name } width=80px
								height=120px onclick="myFunction(${h.bookid})"> <strong><small>${h.name }</small></strong><br>
						</div>
						<div class="col-md-4" style="height: 200px">
							<p style="font-size: 15px">
								<small>By</small> <strong><small><a
										href="http://en.wikipedia.org/wiki/${h.author }">${h.author }</a></small></strong><br>
								<small>Request placed on <strong><fmt:formatDate
											value="${r.startDate }" pattern="dd-MM-yyyy" /></strong></small><br>
							</p>
							<br>
							<button onclick="returnRequest(${h.bookid})">Return</button>
						</div>
					</c:forEach>
				</c:forEach>
			</div>
		</div>

		<!-- 	----------------------------------------------------------To Be Returned------------------------------------------------------------------	         -->


		<div class="box">
			<div class="col-lg-12">
				<hr>
				<h2 class="intro-text text-center">
					<strong>To be Returned</strong>
				</h2>
				<hr>
				<br>
				<h2 class="brand-before">
					<small style="color: red">${toreturn }</small>
				</h2>
				<br>

				<c:forEach var="r" items="${userReq2 }" varStatus="i">
					<c:forEach var="h" items="${toReturn }" begin="${i.index}"
						end="${i.index}">
						<div class="col-md-2 text-center" style="height: 200px">
							<img class="img-responsive img-border text-center"
								src="<c:url value="${h.image }"/>" alt=${h.name } width=80px
								height=120px onclick="myFunction(${h.bookid})"> <strong><small>${h.name }</small></strong><br>
						</div>
						<div class="col-md-4" style="height: 200px">
							<p style="font-size: 15px">
								<small>By</small> <strong><small><a
										href="http://en.wikipedia.org/wiki/${h.author }">${h.author }</a></small></strong><br>
								<small>Request placed on <strong><fmt:formatDate
											value="${r.startDate }" pattern="dd-MM-yyyy" /></strong></small><br>
							</p>
							<button onclick="cancelRequest(${h.bookid})">Cancel</button>
						</div>
					</c:forEach>
				</c:forEach>
			</div>
		</div>

		<!-- 	----------------------------------------------------------Will Read------------------------------------------------------------------	         -->


		<div class="box">
			<div class="col-lg-12">
				<hr>
				<h2 class="intro-text text-center">
					<strong>Will Read</strong>
				</h2>
				<hr>
				<br>
				<h2 class="brand-before">
					<small style="color: red">${willread }</small>
				</h2>
				<br>

				<c:forEach var="r" items="${userReq3 }" varStatus="i">
					<c:forEach var="td" items="${toDeliver }" begin="${i.index}"
						end="${i.index}">
						<div class="col-md-2 text-center" style="height: 200px">
							<img class="img-responsive img-border text-center"
								src="<c:url value="${td.image }"/>" alt=${td.name } width=80px
								height=120px onclick="myFunction(${td.bookid})"> <strong><small>${td.name }</small></strong><br>
						</div>
						<div class="col-md-4" style="height: 200px">
							<p style="font-size: 15px">
								<small>By</small> <strong><small><a
										href="http://en.wikipedia.org/wiki/${td.author }">${td.author }</a></small></strong><br>
								<small>Published by <strong>${td.publisher }</strong></small><br>
								<small>Request placed on <strong><fmt:formatDate
											value="${r.startDate }" pattern="dd-MM-yyyy" /></strong></small><br>
							</p>
							<button onclick="cancelDelivery(${td.bookid})">Cancel</button>
						</div>
					</c:forEach>
				</c:forEach>
			</div>
		</div>

		<!-- 	----------------------------------------------------------Already Read------------------------------------------------------------------	         -->


		<div class="box">
			<div class="col-lg-12">
				<hr>
				<h2 class="intro-text text-center">
					<strong>Already Read</strong>
				</h2>
				<hr>
				<br>
				<h2 class="brand-before">
					<small style="color: red">${done }</small>
				</h2>
				<br>

				<c:forEach var="r" items="${userReq4 }" varStatus="i">
					<c:forEach var="b" items="${returned }" begin="${i.index}"
						end="${i.index}">

						<div class="col-md-3 text-center" style="height: 200px">
							<img class="img-responsive img-border text-center"
								src="<c:url value="${b.image }"/>" alt=${b.name } width=80px
								height=120px onclick="myFunction(${b.bookid})"> <strong><small>${b.name }</small></strong><br>
							<p style="font-size: 15px">
								<small>By</small> <strong><small><a
										href="http://en.wikipedia.org/wiki/${b.author }">${b.author }</a></small></strong>
						</div>
						<div class="col-md-3 text-center" style="height: 200px">
							<p style="font-size: 15px">
								<small>Delivery Request<br> <strong><fmt:formatDate
											value="${r.startDate }" pattern="dd-MM-yyyy" /></strong></small><br>
						</div>
						<div class="col-md-3 text-center" style="height: 200px">
							<p style="font-size: 15px">
								<small>Return Request<br> <strong><fmt:formatDate
											value="${r.endDate }" pattern="dd-MM-yyyy" /></strong></small>
						</div>
						<div class="col-md-3 text-center" style="height: 200px">
							<button onclick="myFunction1(${b.bookid});">Place Again</button>
						</div>
					</c:forEach>
				</c:forEach>
			</div>
		</div>
	</div>

	<script>
	
		$(document).ready(function(){
			$('#active').DataTable();
		});
		
		function myFunction(bookid) {
			window.location = "requestedBook?bookid=".concat(bookid);
		};

		function myFunction1(bookid) {
			
			var data = 'bookid=' + encodeURIComponent(bookid);
		       
		       $.ajax({
		    	   
		              url : "placeRequest",
		              data : data,
		              type : "GET",
		              success : function(ajaxresponse) {
		                    		if(ajaxresponse=="log in")
		                    			window.location = "homeToLogin?";
		                    		else if(ajaxresponse=="notavailable") {
		                    			$('#testing').load(location.href);
		                    			alert("Sorry no books available right now !");
		                    		}
		                    		else if(ajaxresponse=="bookexist") {
		                    			$('#testing').load(location.href);
		                    			alert("The book already exists !");
		                    		}
		                    		else if(ajaxresponse=="nobooksleft") {
		                    			$('#testing').load(location.href);
			              				alert("You cannot place request for more books.");
		                    		}
		                    		else if(ajaxresponse=="subinvalid") {
		                    			window.location = "subscription";
		                    			alert("Your need to update your subscription plan.");
		                    		}
		                    		else {
		                    			window.location = "confirm";
		                    		}
		              },
		              error : function(xhr, status, error) {
		                     //alert(xhr.responseText);
		                     alert("error");
		              }
		       });
		       return false;
		};
					
					function myFunction2(bookid) {
				    	var data = 'bookid=' + encodeURIComponent(bookid);
					    $.ajax({
					              url : "removeBookshelf",
					              data : data,
					              type : "GET",
					              success : function(response) {
					                    		$('#testing').load(location.href);
					                    		alert(response);
					              			},
					              error : function(xhr, status, error) {
					                     		alert("error");
					              			}
					       });
					       return false;
					};
					

					function returnRequest(bookid) {
						var data = 'bookid=' + encodeURIComponent(bookid);
						alert("Please wait a while..")
					    $.ajax({
					              url : "returnRequest",
					              data : data,
					              type : "GET",
					              success : function(response) {
					                    		$('#testing').load(location.href);
					                    		alert(response);
					              			},
					              error : function(xhr, status, error) {
					                     		alert("error");
					              			}
					       });
					       return false;
					};
					
					function cancelRequest(bookid) {
						var data = 'bookid=' + encodeURIComponent(bookid);
						alert("Please wait a while..")
					    $.ajax({
					              url : "cancelRequest",
					              data : data,
					              type : "GET",
					              success : function(response) {
					                    		$('#testing').load(location.href);
					                    		alert(response);
					              			},
					              error : function(xhr, status, error) {
					                     		alert("error");
					              			}
					       });
					       return false;
					};
					
					function cancelDelivery(bookid) {
				    	var data = 'bookid=' + encodeURIComponent(bookid);
				    	alert("Please wait a while..")
					    $.ajax({
					              url : "cancelDelivery",
					              data : data,
					              type : "GET",
					              success : function(response) {
					                    		$('#testing').load(location.href);
					                    		alert(response);
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