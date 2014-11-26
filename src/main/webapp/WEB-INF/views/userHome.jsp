<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ taglib uri="http://www.springframework.org/tags/form"
	prefix="springForm"%>
<!DOCTYPE html >
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
<script src="<c:url value="/resources/js/jquery-1.11.1.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.js"/>"></script>
<script src="//code.jquery.com/ui/1.11.1/jquery-ui.js"></script>



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
		<!-- /.container -->
	</nav>
	<div class="container">

		<div class="address-bar">
			<div class="row">

				<br> <label>Search </label> <input id="book" type="search"
					name="book" placeholder="Search by Title, Author and Category"
					style="width: 40%; color: black" required>
				<button style="color: black" onclick="search()">Search</button>
				<br>
				<h2 class="brand-before">
					<small style="color: yellow">${message }</small>
				</h2>
				<br>
				<%
				    out.print("Welcome  " + session.getAttribute("usersession") + "!");
				%>
				<br>

				<security:authorize access="hasRole('ROLE_ADMIN')">
					<label>Admin</label>
					<br>

					<h2 class="brand-before">
						<small style="color: yellow">${status }</small>
					</h2>
				</security:authorize>
			</div>
		</div>

		<div id="list" class="box"></div>

		<security:authorize access="hasRole('ROLE_ADMIN')">

			<div class="box">

				<ul class="nav nav-tabs" role="tablist">
					<li class="active"><a href="#summary" role="tab"
						data-toggle="tab">Book Summary</a></li>
					<li><a href="#addb" role="tab" data-toggle="tab">Add Book</a></li>
					<li><a href="#updtb" role="tab" data-toggle="tab">Update
							Book</a></li>
					<li><a href="#users" role="tab" data-toggle="tab">Users</a></li>
					<li><a href="#subplan" role="tab" data-toggle="tab">Subscription</a></li>
					<li><a href="#csv" role="tab" data-toggle="tab">Upload csv</a></li>
					<li><a href="#registerAmdin" role="tab" data-toggle="tab">Add
							admin</a></li>

				</ul>

				<!-- Tab panes -->
				<div class="tab-content">
					<div class="tab-pane active" id="summary">
						<br> <br>

						<div id="accordion">
							<label class="control-label">Book rented for a certain
								period</label>
							<div>
								<p style="font-size: 15px">
								<form action="generatepdf" method="post">
									<center>
										Books rented from <input type="text" id="from1" name="from"
											required> to <input type="text" id="to1" name="to"
											required> <br> <br>
										<button type="submit">Get Summary</button>
								</form>
							</div>

							<label class="control-label">Book by an author rented for
								a period</label>
							<div>
								<p style="font-size: 15px">
								<form action="generatepdf" method="post">
									<center>
										Books rented from <input type="text" id="from2" name="from"
											required> to <input type="text" id="to2" name="to"
											required>
										<fieldset align="center">
											<p style="font-size: 15px" for="author">Written by</p>
											<select name="author" id="author">
												<option default>Select Author</option>
												<c:forEach var="a" items="${auth }">
													<option>${a}</option>
												</c:forEach>
											</select>
										</fieldset>
										<br> <br>
										<button type="submit">Get Summary</button>
								</form>
							</div>

							<label class="control-label">Book of a category rented
								for a period</label>
							<div>
								<p style="font-size: 15px">
								<form action="generatepdf" method="post">
									<center>
										Books rented from <input type="text" id="from3" name="from"
											required> to <input type="text" id="to3" name="to"
											required>
										<fieldset align="center">
											<p style="font-size: 15px" for="author">Category</p>
											<select name="category" id="category">
												<option default>Select Category</option>
												<%-- 											<c:forEach var="c" items="${cat }"> --%>
												<%-- 												<option>${c}</option> --%>
												<%-- 
																							</c:forEach> --%>
												<option>Adventurey</option>
												<option>Animals</option>
												<option>Autobiography</option>
												<option>Biograpgy</option>
												<option>Classics</option>
												<option>Crime fiction</option>
												<option>Drama</option>
												<option>Fantasy</option>
												<option>Fiction</option>
												<option>General</option>
												<option>History</option>
												<option>Horror</option>
												<option>Literature</option>
												<option>Mythology</option>
												<option>Non-fiction</option>
												<option>Novel</option>
												<option>Plays</option>
												<option>Political Science</option>
												<option>Political Security</option>
												<option>Sci-fi</option>
												<option>Self-help</option>
												<option>Social Criticism</option>
												<option>Suspense</option>
												<option>Women</option>
												<option>World</option>

											</select>
										</fieldset>
										<br> <br>
										<button type="submit">Get Summary</button>
								</form>
							</div>

							<label class="control-label">Books and their request
								types</label>
							<div>
								<p style="font-size: 15px">
								<form action="pdfreport" method="post">
									<center>
										Books rented from <input type="text" id="from4" name="from"
											required> to <input type="text" id="to4" name="to"
											required> <br> <br>
										<button type="submit">Get Summary</button>
								</form>
							</div>
						</div>
					</div>


					<div class="tab-pane" id="addb">
						<br> <br>
						<springForm:form commandName="bd" class="form-horizontal"
							role="form" method="post" name="form"
							enctype="multipart/form-data" onsubmit="return validateForm()">
							<div class="form-group">
								<label class="control-label col-sm-3" for="name">Name</label>
								<div class="col-sm-9">
									<springForm:input type="text" class="form-control" path="name"
										placeholder="Book name" required="true" />
									<springForm:errors path="name" cssClass="error" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-3" for="auth">Author</label>
								<div class="col-sm-9">
									<springForm:input type="text" class="form-control"
										path="author" placeholder="Author" required="true" />
									<springForm:errors path="author" cssClass="error" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-3" for="publ">Publisher</label>
								<div class="col-sm-9">
									<springForm:input type="text" class="form-control"
										path="publisher" placeholder="Publisher" required="true" />
									<springForm:errors path="publisher" cssClass="error" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-3" for="cat">Category</label>
								<div class="col-sm-9">
									<springForm:input type="text" class="form-control"
										path="category" placeholder="Category" required="true" />
									<springForm:errors path="category" cssClass="error" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-3" for="lang">Language</label>
								<div class="col-sm-9">
									<springForm:input type="text" class="form-control"
										path="language" placeholder="Language" required="true" />
									<springForm:errors path="language" cssClass="error" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-3" for="desc">Description</label>
								<div class="col-sm-9">
									<springForm:textarea class="form-control" rows="3"
										path="description" required="true" />
									<springForm:errors path="description" cssClass="error" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-3" for="number">Available
									Copies</label>
								<div class="col-sm-9">
									<springForm:input type="number" class="form-control"
										path="avail" placeholder="Available copies" required="true" />
									<springForm:errors path="avail" cssClass="error" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-3" for="exist">Exist</label>
								<div class="col-sm-9">
									<springForm:input type="text" class="form-control" path="exist"
										placeholder="true or false" required="true" />
									<springForm:errors path="exist" cssClass="error" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-3" for="image">Image</label>
								<div class="col-sm-9">
									<springForm:input type="text" class="form-control" path="image"
										placeholder="Give image link" required="true" />
									<springForm:errors path="image" cssClass="error" />
								</div>
								<br> <br> <br>
								<div class="col-sm-offset-5 col-sm-7">
									<button type="submit">Save</button>
								</div>
							</div>
						</springForm:form>
					</div>


					<div class="tab-pane" id="updtb">
						<br> <br>
						<form action="find" method="get">
							<center>
								<input type="search" name="book"
									style="width: 27%; color: black" required>
								<button type="submit" style="color: black">Find</button>
						</form>
					</div>

					<div class="tab-pane" id="subplan">
						<br> <br>
						<form method="POST" action="updsub" enctype="multipart/form-data">
							<br>
							<br>
							<center>
								Chose file to upload:
								<center><input type="file" name="file">
								<input type="submit" value="Upload">
						</form>
					</div>

					<div class="tab-pane" id="users">
						<br> <br>
						<center>
							<form action="userInfo">
								Users with active subscriptions<br>
								<button type="submit">View</button>
								<br> <br>
							</form>
							<form action="userReq">
								Users requests and their status<br>
								<button type="submit">View</button>
							</form>
					</div>

					<div class="tab-pane" id="csv">
						<br> <br>
						<center>
							<br> <br>
							<form method="POST" action="uploadcsv"
								enctype="multipart/form-data">
								<div class="form-group col-lg-6 text-right">File to
									upload:</div>
								<div class="form-group col-lg-6 text-left">
									<input type="file" name="file"><br> <input
										type="submit" value="Upload">
								</div>
							</form>
					</div>

					<div class="tab-pane" id="registerAmdin">
						<br> <br>
						<springForm:form commandName="ud" class="form-horizontal"
							role="form" method="post" action="registerAdmin" name="form2"
							onsubmit="return validate()">
							<div class="form-group">
								<label class="control-label col-sm-3" for="name">Name</label>
								<div class="col-sm-9">
									<springForm:input type="text" class="form-control" path="name"
										placeholder="Enter name" required="true" id="name" />
									<springForm:errors path="name" cssClass="error" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-3" for="username">Username</label>
								<div class="col-sm-9">
									<springForm:input type="text" class="form-control"
										path="username" placeholder="Enter username" required="true"
										id="username" />
									<springForm:errors path="username" cssClass="error" />
									<h2 class="brand-before">
										<small style="color: red">${exists }</small>
									</h2>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-3" for="password">Password</label>
								<div class="col-sm-9">
									<springForm:input type="password" class="form-control"
										path="password" placeholder="Enter password" required="true"
										id="password" />
									<springForm:errors path="password" cssClass="error" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-3" for="confirmpswd">Confirm
									Password</label>
								<div class="col-sm-9">
									<input type="password" class="form-control"
										name="confirmPassword" placeholder="Confirm password" required
										id="confirmPassword" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-3" for="eid">Email-ID</label>
								<div class="col-sm-9">
									<springForm:input type="email" class="form-control" path="eid"
										placeholder="Enter email ID" required="true" id="eid" />
									<springForm:errors path="eid" cssClass="error" />
									<h2 class="brand-before">
										<small style="color: red">${email }</small>
									</h2>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-3" for="contact">Contact</label>
								<div class="col-sm-9">
									<springForm:input type="tel" class="form-control"
										path="contact" placeholder="Enter contact" required="true"
										id="contact" />
									<springForm:errors path="contact" cssClass="error" />
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-3 col-sm-9">
									<button type="submit" class="btn btn-default">Submit</button>
								</div>
							</div>
						</springForm:form>

					</div>
				</div>
			</div>
		</security:authorize>
	</div>
	<security:authorize access="hasRole('ROLE_USER')">
		<div class="container">
			<div class="box">
				<div class="col-lg-4">
					<h2 class="intro-text text-center">
						<strong>New</strong>
					</h2>

					<c:forEach var="n" items="${newbooks }">
						<div class="col-sm-4 text-center" style="height: 230px">
							<img class="img-responsive img-border"
								src="<c:url value="${n.image }"/>" width=80px height=120px
								onclick="requested(${n.bookid})"> <small>${n.name }</small>
						</div>
					</c:forEach>
				</div>

				<div class="col-lg-4">
					<h2 class="intro-text text-center">
						<strong>Recommended</strong>
					</h2>

					<c:forEach var="b" items="${recom }">
						<div class="col-sm-4 text-center" style="height: 230px">
							<img class="img-responsive img-border text-center"
								src="<c:url value="${b.image }"/>" width=80px height=120px
								onclick="requested(${b.bookid})"> <small>${b.name }</small>
						</div>
					</c:forEach>
				</div>

				<div class="col-lg-4">
					<h2 class="intro-text text-center">
						<strong>Popular</strong>
					</h2>

					<c:forEach var="n" items="${popular }">
						<div class="col-sm-4 text-center" style="height: 230px">
							<img class="img-responsive img-border"
								src="<c:url value="${n.image }"/>" width=80px height=120px
								onclick="requested(${n.bookid})"> <small>${n.name }</small>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</security:authorize>
	<div></div>

	<script>
		document.getElementById("list").style.display = "none";

		function search() {

			document.getElementById("list").style.display = "block";

			$.ajax({
						type : "post",
						url : "book/get",
						cache : false,
						data : 'book=' + $("#book").val(),
						success : function(response) {
							$("#list").html('');
							console.log(response);
							$
									.each(
											response,
											function(index, element) {
												var itemHTML = [

														"<div class=col-md-2>",
														"<img onclick=",
														"requested('"
														+ element.bookid
														+ "') src=",element.image," width=","70"," height=","120","><br><br><br>",
														"</div>",

														"<div class=col-md-6>",
														"<p style=","font-size:13px",">",
														"<strong style=","color:green",">",
														element.name,
														"</strong><br><br>",
														"By ",
														"<a href=","http://en.wikipedia.org/wiki/",element.author,"><b>",
														element.author,
														"</b></a><br>",
														"Publisher <b>",
														element.publisher,
														"</b><br>",
														"Category <b>",
														element.category,
														"</b><br>",
														"Available copies <b>",
														element.avail,
														"</b>",
														"</div>",

														"<div class=col-md-4>",
														"<button onclick=",
														"myFunction1('"
																+ element.bookid
																+ "')",
														">Place Request</button>",
														"<button onclick=",
														"myFunction2('"
																+ element.bookid
																+ "')",
														">Add to Bookshelf</button><br><br><br><br><br><br><br><br>",
														"</div>"].join('\n');
												$("#list").append(itemHTML);
											});
						},
						error : function() {
							alert('Error while request..');
						}
					});
		};
	</script>
	<script>
		function myFunction1(bookid) {
			var data = 'bookid=' + encodeURIComponent(bookid);

			$.ajax({

				url : "placeRequest",
				data : data,
				type : "GET",
				success : function(ajaxresponse) {
					if (ajaxresponse == "log in")
						window.location = "login2";
					else if (ajaxresponse == "notavailable") {
						$('#testing').load(location.href);
						alert("Sorry no books available right now !");
					} else if (ajaxresponse == "bookexist") {
						$('#testing').load(location.href);
						alert("The book already exists !");
					} else if (ajaxresponse == "nobooksleft") {
						$('#testing').load(location.href);
						alert("You cannot place request for more books.");
					} else if (ajaxresponse == "subinvalid") {
						window.location = "subscription";
						alert("Your need to update your subscription plan.");
					} else {
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
					if (ajaxresponse == "log in")
						window.location = "login2";
					else if (ajaxresponse == "bookexist")
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
		
		function requested(bookid) {
			window.location = "requestedBook?bookid=".concat(bookid);
		};

		

		$(function() {
			$("#accordion").accordion();
		});
	</script>

	<script>
	$(function() {
		$('[id^="from"]').datepicker("option", "maxDate", '+0m +0w');
		$('[id^="to"]').datepicker("option", "maxDate", '+0m +0w');

		$('[id^="from"]').datepicker(
				{
					defaultDate : "-2m",
					changeMonth : true,
					numberOfMonths : 3,
					maxDate : '+0m +0w',
					onClose : function(selectedDate) {
						$('[id^="to"]').datepicker("option", "minDate",
								selectedDate);
						$('[id^="to"]').datepicker("option", "maxDate",
								'+0m +0w');
					}
				});
		$('[id^="to"]').datepicker(
				{
					defaultDate : "+1w",
					changeMonth : true,
					numberOfMonths : 3,
					onClose : function(selectedDate) {
						$('[id^="from"]').datepicker("option", "maxDate",
								selectedDate);
					}
				});

	});
	</script>
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
	};
	
	function validate() {
	    var x = document.forms["form2"]["name"].value;
	    if (x.length<3) {
	        alert("Name cannot be shorter than 3 characters");
	        return false;
		}
	    if (x.length>20) {
	        alert("Name cannot be greater than 20 characters");
	        return false;
	    }
	    
	    x = document.forms["form2"]["username"].value;
	    if (x.length<3) {
	        alert("Username cannot be shorter than 3 characters");
	        return false;
		}
	    if (x.length>10) {
	        alert("Username cannot be greater than 10 characters");
	        return false;
	    }
	    
	    x = document.forms["form2"]["password"].value;
	    if (x.length<3) {
	        alert("Password cannot be shorter than 3 characters");
	        return false;
		}
	    if (x.length>15) {
	        alert("Password cannot be greater than 15 characters");
	        return false;
	    }
	    
	    x = document.forms["form2"]["password"].value;
	    y = document.forms["form2"]["confirmpswd"].value;
	    if (x != y) {
	    	document.forms["form2"]["password"].style.borderColor = "#E34234";
	        document.forms["form2"]["confirmpswd"].style.borderColor = "#E34234";
	        alert("Passwords do not match");
	        return false;
	    }
	    
	    x = document.forms["form2"]["eid"].value;
	    var atpos = x.indexOf("@");
	    var dotpos = x.lastIndexOf(".");
		if (atpos< 1 || dotpos<atpos+2 || dotpos+2 >= x.length) {
        alert("Not a valid e-mail address");
        return false;
		}
		
		x = document.forms["form2"]["contact"].value;
	    if (x.length<10 || x.length>10) {
	        alert("Contact no. must be of 10 characters");
	        return false;
	    }
	};
</script>


</body>
</html>
