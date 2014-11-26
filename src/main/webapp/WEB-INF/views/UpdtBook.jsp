<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
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

	<div class="container" style="max-width: 40%;">

		<div class="address-bar">
			<div class="row">
				<%
           	out.print("Welcome  " +session.getAttribute("usersession") +"!");
            %>
				<br> <label>Admin</label>
			</div>
		</div>

		<div class="box">
			<div class="col-lg-12">
				<hr>
				<h2 class="intro-text text-center">
					<strong>Update Book</strong>
				</h2>
				<hr>
				<c:forEach var="b" items="${books }">
					<springForm:form commandName="bd" class="form-horizontal"
						role="form" method="post" action="update?bookid=${b.bookid }"
						name="form" enctype="multipart/form-data"
						onsubmit="return validateForm()">
						<div class="form-group">
							<label class="control-label col-sm-3" for="id">Book-ID</label>
							<div class="col-sm-9">
								<springForm:input type="text" class="form-control" path="bookid"
									value="${b.bookid }" readonly="true" />
								<springForm:errors path="bookid" cssClass="error" />
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-3" for="name">Name</label>
							<div class="col-sm-9">
								<springForm:input type="text" class="form-control" path="name"
									value="${b.name }" required="true" />
								<springForm:errors path="name" cssClass="error" />
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-3" for="author">Author</label>
							<div class="col-sm-9">
								<springForm:input type="text" class="form-control" path="author"
									value="${b.author }" required="true" />
								<springForm:errors path="author" cssClass="error" />
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-3" for="publisher">Publisher</label>
							<div class="col-sm-9">
								<springForm:input type="text" class="form-control"
									path="publisher" value="${b.publisher }" required="true" />
								<springForm:errors path="publisher" cssClass="error" />
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-3" for="category">Category</label>
							<div class="col-sm-9">
								<springForm:input type="text" class="form-control"
									path="category" value="${b.category }" required="true" />
								<springForm:errors path="category" cssClass="error" />
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-3" for="language">Language</label>
							<div class="col-sm-9">
								<springForm:input type="text" class="form-control"
									path="language" value="${b.language }" required="true" />
								<springForm:errors path="language" cssClass="error" />
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-3" for="description">Description</label>
							<div class="col-sm-9">
								<springForm:textarea class="form-control" rows="3"
									path="description" value="${b.description }" required="true" />
								<springForm:errors path="description" cssClass="error" />
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-3" for="avail">Available
								Copies</label>
							<div class="col-sm-9">
								<springForm:input type="number" class="form-control"
									path="avail" value="${b.avail }" required="true" />
								<springForm:errors path="avail" cssClass="error" />
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-3" for="exist">Exist</label>
							<div class="col-sm-9">
								<springForm:input type="text" class="form-control" path="exist"
									value="${b.exist }" required="true" />
								<springForm:errors path="exist" cssClass="error" />
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-3" for="file">Image</label>
							<div class="col-sm-9">
								<springForm:input type="text" class="form-control" path="image"
									value="${b.image }" required="true" />
								<springForm:errors path="image" cssClass="error" />
							</div>
							<br> <br> <br>
							<div class="col-sm-offset-5 col-sm-7">
								<button type="submit">Save</button>
							</div>
						</div>

					</springForm:form>
				</c:forEach>

			</div>
		</div>

	</div>

	<script>
		function validateForm() {
		    var x = document.forms["form"]["name"].value;
		    if (x.length<3) {
		        alert("Book name cannot be that short");
		        return false;
			}
		    if (x.length>300) {
		        alert("Book name cannot be that long");
		        return false;
		    }
		    
		    x = document.forms["form"]["author"].value;
		    if (x.length<3) {
		        alert("Author name cannot be that short");
		        return false;
			}
		    if (x.length>100) {
		        alert("Author name cannot be that long");
		        return false;
		    }
		    
		    x = document.forms["form"]["publisher"].value;
		    if (x.length<4) {
		        alert("Publisher name cannot be that short");
		        return false;
			}
		    if (x.length>200) {
		        alert("Publisher name cannot be that long");
		        return false;
		    }
		    
		    x = document.forms["form"]["category"].value;
		    if (x.length<3) {
		        alert("Category name cannot be that short");
		        return false;
			}
		    if (x.length>200) {
		        alert("Category name cannot be that long");
		        return false;
			}
		    
			x = document.forms["form"]["language"].value;
		    if (x.length<4) {
		        alert("Language cannot be that short");
		        return false;
		    }
		    
		    x = document.forms["form"]["description"].value;
		    if (x.length<100) {
		        alert("Description cannot be that short");
		        return false;
		    }
		    if (x.length>900) {
		        alert("Description cannot be that long");
		        return false;
		    }
		}
	</script>

</body>
</html>