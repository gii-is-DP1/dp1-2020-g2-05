<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, owners, vets or error"%>

<nav class="navbar navbar-default" role="navigation">
	<div class="container">
		<div  class="navbar-header"> 
<!-- 		class="navbar-brand" -->
			<a 	href="<spring:url value="/" htmlEscape="true" />">
			<span><img alt="Home" src="/resources/images/motogpLogo.png"></span>
			</a>
<!-- 			<button type="button" class="navbar-toggle" data-toggle="collapse" -->
<!-- 				data-target="#main-navbar"> -->
<!-- 				<span class="sr-only"><os-p>Toggle navigation</os-p></span> <span -->
<!-- 					class="icon-bar"></span> <span class="icon-bar"></span> <span -->
<!-- 					class="icon-bar"></span> -->
<!-- 			</button> -->
		</div>
		<div class="navbar-collapse collapse" id="main-navbar">
			<ul class="nav navbar-nav">

				
				<petclinic:menuItem active="${name eq 'pilots'}" url="/pilotsPaged"
					title="leads to /pilots">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Pilots</span>
				</petclinic:menuItem>
			
				

				
				<petclinic:menuItem active="${name eq 'leagues'}" url="/leagues"
					title="leads to /leagues">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Leagues</span>
				</petclinic:menuItem>
				
				<petclinic:menuItem active="${name eq 'pilots'}" url="/granPremios"
					title="leads to /granPremios">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>GPs</span>
				</petclinic:menuItem>
				
<%-- 				<sec:authorize access="hasAuthority('admin')"> --%>
<%-- 				<petclinic:menuItem active="${name eq 'BD'}" url="/BD" --%>
<%-- 					title="leads to /BD"> --%>
<!-- 					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span> -->
<!-- 					<span>Settle DB</span> -->
<%-- 				</petclinic:menuItem> --%>
<%-- 				</sec:authorize>		 --%>
				
			<sec:authorize access="hasAuthority('admin')">
				<petclinic:menuItem active="${name eq 'CP'}" url="/controlPanelSP"
					title="leads to /controlPanelSP">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Control Panel<span>
				</petclinic:menuItem>
			</sec:authorize>
			</ul>


			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />">Login</a></li>
					<li><a href="<c:url value="/users/new" />">Register</a></li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span> 
							<strong><sec:authentication property="name" /></strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row">
										<div class="col-lg-4">
											<p class="text-center">
												<span class="glyphicon glyphicon-user icon-size"></span>
											</p>
										</div>
										<div class="col-lg-8">
											<p class="text-left">
												<a href="/myTeams"><strong><sec:authentication property="name" /></strong></a>
											</p>
											<p class="text-left">
												<a href="<c:url value="/logout" />"
													class="btn btn-primary btn-block btn-sm">Logout</a>
											</p>
										</div>
									</div>
								</div>
							</li>
							<li class="divider"></li>
<!-- 							
                            <li> 
								<div class="navbar-login navbar-login-session">
									<div class="row">
										<div class="col-lg-12">
											<p>
												<a href="#" class="btn btn-primary btn-block">My Profile</a>
												<a href="#" class="btn btn-danger btn-block">Change
													Password</a>
											</p>
										</div>
									</div>
								</div>
							</li>
							
-->
		
                            <li> 
								<div class="navbar-login navbar-login-session">
									<div class="row">
										<div class="col-lg-12">
											<p>
												<a href="/messages/" class="btn btn-primary btn-block">My messages</a>
												<a href="/friends/" class="btn btn-primary btn-block">My friends</a>
												<a href="/leagues/myLeagues" class="btn btn-primary btn-block">My leagues</a>
<!-- 												<a href="#" class="btn btn-danger btn-block">Change -->
<!-- 													Password</a> -->
											</p>
										</div>
									</div>
								</div>
							</li>
						</ul></li>
				</sec:authorize>
			</ul>
		</div>



	</div>
</nav>
