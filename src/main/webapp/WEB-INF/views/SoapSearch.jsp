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

				<%  
                    if(session.getAttribute("usersession")!=null) {
                    	out.print("<li><a href=\"userhome\"><small>Home</small></a>");
                    }
                    %>
				<security:authorize access="hasRole('ROLE_USER')">
					<%
		            if(session.getAttribute("usersession")!=null) {
		            	out.print("<li><a href=\"showBookshelf\"><small>Bookshelf</small></a>");
			            out.print("<li><a href=\"#\"><small>Profile</small></a>");
			        }
		            %>
				</security:authorize>
				<%-- <%--                     <security:authorize access="hasRole('ROLE_ADMIN')"> --%>
				<%-- <%--                     <% --%>
				<!-- // 		            if(session.getAttribute("usersession")!=null) { -->
				<!-- // 			            out.print("<li><a href=\"#\"><small>Books</small></a>"); -->
				<!-- // 			            out.print("<li><a href=\"#\"><small>Users</small></a>"); -->
				<!-- // 			            out.print("<li><a href=\"#\"><small>Subscriptions</small></a>"); -->
				<!-- // 			        } -->
				<%-- <%--                     %> --%>
				<%-- <%--                     </security:authorize> --%>
				<%  
                    if(session.getAttribute("usersession")!=null) {
                    	out.print("<li><a href=\"logout\"><small>Logout</small></a>");
                    }
                    %>
				<%
                    if(session.getAttribute("usersession")==null) {
                    	out.print("<li><a href=\"home\"><small>Home</small></a>");
		            	out.print("<li><a href=\"#\"><small>About</small></a>");
		            	out.print("<li><a href=\"#\"><small>Contact</small></a>");
		            }
		            %>
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container --> </nav>

	<div class="container">

		<div class="address-bar">
			<div class="row">
				<!-- 				<label>Search </label> <input id="book" type="search" name="book" -->
				<!-- 						placeholder="Search by Title, Author and Category" -->
				<!-- 						style="width: 27%; color: black" required> -->
				<!-- 						<button onclick="search()" style="color:black">Go !</button> -->
				<form action="searchBook" method="get">
					<br> <label>Search </label> <input type="search" name="book"
						placeholder="Search by Title, Author and Category"
						style="width: 27%; color: black" required>
				</form>

				<br>
				<%
            if(session.getAttribute("usersession")==null) {
	            out.print("<a href=\"homeToLogin\" style=\"color:white\">Sign In |</a>");
	            out.print("<a href=\"homeToRegister\" style=\"color:white\"> Sign Up</a>");
            }
            else
            	out.print("Welcome  " +session.getAttribute("usersession") +"!");
            %>
			</div>
		</div>

		<div class="box" id="searchContent">
			<div class="col-lg-12">
				<hr>
				<h2 class="intro-text text-center">
					<strong>Books relating to ${category }</strong>
				</h2>
				<hr>
				<br>
				<h2 class="brand-before">
					<small style="color: red">${error }${nobooksleft }${alreadyplaced }${notavailable }</small>
				</h2>
				<br>

				<c:forEach var="b" items="${books }">

					<div class="col-md-2 text-center" style="height: 200px">
						<img class="img-responsive img-border text-center"
							src="<c:url value="${b.image }"/>" alt=${b.name } width=70px
							height=120px onclick="myFunction(${b.bookid})">

					</div>

					<div class="col-md-6" style="height: 200px">
						<p style="font-size: 15px">
							<strong style="color: green">${b.name }</strong><br> <small>By</small>
							<strong><small><a
									href="http://en.wikipedia.org/wiki/${b.author }">${b.author }</a></small></strong><br>
							<small>Published by <strong>${b.publisher }</strong></small><br>
							<small>Category <strong>${b.category }</strong></small><br>
							<small>Available copies <strong>${b.avail }</strong></small><br>
							<br>
						</p>
					</div>

					<div class="col-md-4" style="height: 200px">
						<br> <br>
						<button onclick="myFunction1(${b.bookid})">Place Request</button>
						<button onclick="myFunction2(${b.bookid})">Add to
							bookshelf</button>
					</div>

				</c:forEach>
			</div>
		</div>
	</div>

	<script>
		//document.getElementById("searchContent").style.display = "none";
		
    	function search(){
    		//document.getElementById("searchContent").style.display = "block";
    		var book = $("#book").val();
    		alert(book);
    		window.location = "/books/get?book=".concat(book);
    	};
    	
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
		                    			window.location = "login2";
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
		              url : "addToBookshelf",
		              data : data,
		              type : "GET",
		              success : function(ajaxresponse) {
		            	  			if(ajaxresponse=="log in")
                  						window.location = "login2";
		            	  			else if(ajaxresponse=="bookexist")
		                    			alert("The book already exists !");
                  					else
                  						alert("Book added to Bookshelf !");
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
