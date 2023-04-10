<%--
- form.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<h2>
	<acme:message code="lecturer.lecturerDashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="lecturer.lecturerDashboard.form.label.average-lecture-learning-time"/>
		</th>
		<td>
			<acme:print value="${lecturesStats.getAverage()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="lecturer.lecturerDashboard.form.label.min-lecture-learning-time"/>
		</th>
		<td>
			<acme:print value="${lecturesStats.getMin()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="lecturer.lecturerDashboard.form.label.max-lecture-learning-time"/>
		</th>
		<td>
			<acme:print value="${lecturesStats.getMax()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="lecturer.lecturerDashboard.form.label.lin-dev-lecture-learning-time"/>
		</th>
		<td>
			<acme:print value="${lecturesStats.getLinDev()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="lecturer.lecturerDashboard.form.label.average-course-learning-time"/>
		</th>
		<td>
			<acme:print value="${coursesStats.getAverage()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="lecturer.lecturerDashboard.form.label.max-course-learning-time"/>
		</th>
		<td>
			<acme:print value="${coursesStats.getMax()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="lecturer.lecturerDashboard.form.label.min-course-learning-time"/>
		</th>
		<td>
			<acme:print value="${coursesStats.getMin()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="lecturer.lecturerDashboard.form.label.lin-dev-course-learning-time"/>
		</th>
		<td>
			<acme:print value="${coursesStats.getLinDev()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="lecturer.lecturerDashboard.form.label.theoretical-lectures"/>
		</th>
		<td>
			<acme:print value="${numOfLecturesByType.get('THEORETICAL')}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="lecturer.lecturerDashboard.form.label.hands-on-lectures"/>
		</th>
		<td>
			<acme:print value="${numOfLecturesByType.get('HANDS_ON')}"/>
		</td>
	</tr>	
</table>

<jstl:choose>
<jstl:when test="${lecturesStats.getMax()>0.0}">
						
		
	<h3><acme:message code="lecturer.lecturerDashboard.form.label.lectures.information"/></h3>
	<div>
		<canvas id="canvas"></canvas>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			var data = {
				labels : [
						"AVERAGE", "MAX", "MIN","LINEAL DEVIATION"
				],
				datasets : [
					{
						data : [
							<jstl:out value="${lecturesStats.getAverage()}"/>, 
							<jstl:out value="${lecturesStats.getMax()}"/>, 
							<jstl:out value="${lecturesStats.getMin()}"/>,
							<jstl:out value="${lecturesStats.getLinDev()}"/>
						],
						backgroundColor: [
						      'rgb(40, 180, 99)',
					    	  'rgb(54, 162, 235)',
					    	  'rgb(255, 205, 86)',
					      	  'rgb(230, 170, 243)'
					    ]
					}
				]
			};
			var options = {
				scales : {
					yAxes : [
						{
							ticks : {
								suggestedMin : 0.0,
								suggestedMax : 100.0
							}
						}
					]
				},
				legend : {
					display : false
				}
			};
	
			var canvas, context;
	
			canvas = document.getElementById("canvas");
			context = canvas.getContext("2d");
			new Chart(context, {
				type : "bar",
				data : data,
				options : options
			});
		});
	</script>

</jstl:when>
</jstl:choose>

<jstl:choose>
<jstl:when test="${coursesStats.getMax()>0.0}">

	<h3><acme:message code="lecturer.lecturerDashboard.form.label.courses.information"/></h3>
	<div>
		<canvas id="canvas2"></canvas>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			var data = {
				labels : [
						"AVERAGE", "MAX", "MIN","LINEAL DEVIATION"
				],
				datasets : [
					{
						data : [
							<jstl:out value="${coursesStats.getAverage()}"/>, 
							<jstl:out value="${coursesStats.getMax()}"/>, 
							<jstl:out value="${coursesStats.getMin()}"/>,
							<jstl:out value="${coursesStats.getLinDev()}"/>
						],
						backgroundColor: [
						      'rgb(40, 180, 99)',
						      'rgb(54, 162, 235)',
						      'rgb(255, 205, 86)',
						      'rgb(230, 170, 243)'
					    ]
					}
				]
			};
			var canvas, context;
			canvas = document.getElementById("canvas2");
			context = canvas.getContext("2d");
			new Chart(context, {
				type : "bar",
				data : data,
			});
		});
	</script>

</jstl:when>
</jstl:choose>

<jstl:choose>
<jstl:when test="${numOfLecturesByType.get('THEORETICAL') != 0 || numOfLecturesByType.get('HANDS_ON') != 0}">

	<h3><acme:message code="lecturer.lecturerDashboard.form.label.lectures.type.information"/></h3>
	<div>
		<canvas id="canvas3"></canvas>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			var data = {
				labels : [
						"THEORETICAL", "HANDS-ON"
				],
				datasets : [
					{
						data : [
							<jstl:out value="${numOfLecturesByType.get('THEORETICAL')}"/>, 
							<jstl:out value="${numOfLecturesByType.get('HANDS_ON')}"/>, 
						],
						backgroundColor: [
					      'rgb(40, 180, 99)',
					      'rgb(54, 162, 235)'
					    ]
					}
				]
			};

	
			var canvas, context;
	
			canvas = document.getElementById("canvas3");
			context = canvas.getContext("2d");
			new Chart(context, {
				type : "doughnut",
				data : data,
			});
		});
	</script>
</jstl:when>
</jstl:choose>


<acme:return/>

