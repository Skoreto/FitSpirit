<div class="section-content">
	<c:forEach var="lesson" begin="0" end="5" items="${lessonsForWidget.lessonList}">
      		<div class="event-item">
               <p class="date-label">
                   <span class="month"><fmt:formatDate value="${lesson.startTime}" pattern="MMMM" /></span>
                   <span class="date-number"><fmt:formatDate value="${lesson.startTime}" pattern="E d." /></span>
               </p>
               <div class="details ">
                   <h4 class="title">${lesson.activityType.name}</h4>
                   <p class="time">
                       <i class="fa fa-clock-o"></i><fmt:formatDate value="${lesson.startTime}" pattern="H:mm" /> - <fmt:formatDate value="${lesson.endTime}" pattern="H:mm" />
                   </p>
                   <p class="location">
                       <i class="fa fa-map-marker"></i>${lesson.room.name}
                   </p>
               </div>
           </div>
	</c:forEach>
	<a class="read-more" href="<spring:url value="/lessons/index" htmlEscape="true"/>">Všechny lekce<i class="fa fa-chevron-right"></i></a>
</div>