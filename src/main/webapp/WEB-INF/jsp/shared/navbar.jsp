<li class="nav-item"><a href="<spring:url value="/index" htmlEscape="true"/>"><i class="fa fa-home"></i> Úvod</a></li>
<li class="nav-item"><a href="<spring:url value="/lessons/index" htmlEscape="true"/>"><i class="fa fa-calendar"></i> Lekce</a></li>

<c:choose>
	<c:when test="${empty loggedInUser}">
		<li class="nav-item"><a href="<spring:url value="/activityTypes/index" htmlEscape="true"/>"><i class="fa fa-child"></i> Aktivity</a></li>
		<li class="nav-item"><a href="<spring:url value="/rooms/index" htmlEscape="true"/>"><i class="fa fa-map-marker"></i> Místnosti</a></li>
		<li class="nav-item"><a href="<spring:url value="/gallery/index" htmlEscape="true"/>"><i class="fa fa-photo"></i> Galerie</a></li>
		<li class="nav-item"><a href="<spring:url value="/instructors/index" htmlEscape="true"/>"><i class="fa fa-users"></i> Instruktoři</a></li>		
	</c:when>
	<c:when test="${loggedInUser.userRole.id == 3}">
		<li class="nav-item"><a href="<spring:url value="/reservations/index" htmlEscape="true"/>"><i class="fa fa-check-square-o"></i> Rezervace</a></li>
		<li class="nav-item"><a href="<spring:url value="/activityTypes/index" htmlEscape="true"/>"><i class="fa fa-child"></i> Aktivity</a></li>
		<li class="nav-item"><a href="<spring:url value="/rooms/index" htmlEscape="true"/>"><i class="fa fa-map-marker"></i> Místnosti</a></li>
		<li class="nav-item"><a href="<spring:url value="/gallery/index" htmlEscape="true"/>"><i class="fa fa-photo"></i> Galerie</a></li>
		<li class="nav-item"><a href="<spring:url value="/instructors/index" htmlEscape="true"/>"><i class="fa fa-users"></i> Instruktoři</a></li>		
	</c:when>
	<c:when test="${loggedInUser.userRole.id == 2}">
		<li class="nav-item"><a href="<spring:url value="/activityTypes/index" htmlEscape="true"/>"><i class="fa fa-child"></i> Aktivity</a></li>
		<li class="nav-item"><a href="<spring:url value="/rooms/index" htmlEscape="true"/>"><i class="fa fa-map-marker"></i> Místnosti</a></li>
		<li class="nav-item"><a href="<spring:url value="/gallery/index" htmlEscape="true"/>"><i class="fa fa-photo"></i> Galerie</a></li>
		<li class="nav-item"><a href="<spring:url value="/instructors/index" htmlEscape="true"/>"><i class="fa fa-users"></i> Instruktoři</a></li>		
	</c:when>
	<c:when test="${loggedInUser.userRole.id == 1}">
		<li class="nav-item"><a href="<spring:url value="/activityTypes/index" htmlEscape="true"/>"><i class="fa fa-child"></i> Aktivity</a></li>
		<li class="nav-item"><a href="<spring:url value="/rooms/index" htmlEscape="true"/>"><i class="fa fa-map-marker"></i> Místnosti</a></li>
		<li class="nav-item"><a href="<spring:url value="/gallery/index" htmlEscape="true"/>"><i class="fa fa-photo"></i> Galerie</a></li>
		<li class="nav-item dropdown">
		    <a class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-delay="0" data-close-others="false" href="#"><i class="fa fa-users"></i> Uživatelé <i class="fa fa-angle-down"></i></a>
		    <ul class="dropdown-menu">
		        <li><a href="<spring:url value="/clients/index" htmlEscape="true"/>"><i class="fa fa-user"></i> Klienti</a></li>
		        <li><a href="<spring:url value="/instructors/index" htmlEscape="true"/>"><i class="fa fa-users"></i> Instruktoři</a></li>
		        <li><a href="<spring:url value="/staffs/index" htmlEscape="true"/>"><i class="fa fa-female"></i> Obsluha</a></li>
		    </ul>
		</li>		
	</c:when>
</c:choose>